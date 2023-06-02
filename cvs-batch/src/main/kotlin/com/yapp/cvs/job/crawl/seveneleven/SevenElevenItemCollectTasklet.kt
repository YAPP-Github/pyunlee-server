package com.yapp.cvs.job.crawl.seveneleven

import com.yapp.cvs.domains.enums.SellerType
import com.yapp.cvs.domains.extension.commaStringToLong
import com.yapp.cvs.domains.product.entity.ProductEntity
import com.yapp.cvs.support.JsoupHandler
import com.yapp.cvs.support.ProductCollectInfo
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class SevenElevenItemCollectTasklet: Tasklet {
    companion object {
    }
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val productCollectInfoList = ProductCollectInfo.getAllBySellerType(SellerType.SEVEN_ELEVEN)

        productCollectInfoList.forEach {
            collectData(it)
        }

        return RepeatStatus.FINISHED
    }

    private fun collectData(productCollectInfo: ProductCollectInfo) {
        val productList = mutableListOf<ProductEntity>()
        var preSize: Int
        var pageSize = productCollectInfo.pageSize

        do {
            preSize = productList.size
            val document = JsoupHandler.doFormPost(productCollectInfo.url, mapOf(("intPageSize" to pageSize.toString())))
            val body = document.body()

            val productElementList = body.getElementsByClass("dosirak_list").first()!!
                .getElementsByTag("ul").first()!!
                .getElementsByTag("li")

            productElementList.forEach { productRawElement ->
                // 신상품, 1+1 같은 쓸모 없는 element 제외
                val productElement = productRawElement.getElementsByClass("pic_product").first() ?: return@forEach

                //	ex) https://www.7-eleven.co.kr/upload/product/8809838/034001.1.jpg
                val imgUrl = productElement.getElementsByTag("img").first()!!.absUrl("src")

                val urlSplitStr = imgUrl.split("/product")[1]
                    .split("/")
                val barcode = urlSplitStr[1] + urlSplitStr[2].split(".")[0]

                val infoElement = productElement.getElementsByClass("infowrap").first()!!

                val nameSplit = infoElement.getElementsByClass("name").first()!!.text().split(")")
                val name = nameSplit[1]
                val brandName = nameSplit[0]

                val priceStr = infoElement.getElementsByClass("price").first()!!.text()

                // 세븐일레븐은 매 요청마다 전체 리스트가 반환됌, 이미 추가된 상품 제외
                if(!productList.any { it.name == name && it.brandName == brandName }){
                    //TODO: dup check name with db
                    val productEntity = ProductEntity(
                        name = nameSplit[1],
                        brandName = nameSplit[0],
                        price = priceStr.commaStringToLong(),
                        productCategoryType = productCollectInfo.productCategoryType,
                        barcode = barcode,
                        imageUrl = imgUrl
                    )
                    productList.add(productEntity)
                    println(productEntity.toString())
                }
            }
            pageSize += productCollectInfo.pageSize
        } while (preSize < productList.size)
    }
}