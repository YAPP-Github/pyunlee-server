package com.yapp.cvs.job.crawl.seveneleven

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.enums.RetailerType
import com.yapp.cvs.domains.extension.commaStringToLong
import com.yapp.cvs.support.JsoupHandler
import com.yapp.cvs.support.SevenElevenProductCollectInfo
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class SevenElevenDataCollectTasklet: Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val sevenElevenProductCollectInfoList = SevenElevenProductCollectInfo.values()

        val productRawDataVOList = mutableListOf<ProductRawDataVO>()
        sevenElevenProductCollectInfoList.forEach {
            productRawDataVOList.addAll(collectRawData(it))
        }

        saveProductData(productRawDataVOList)

        return RepeatStatus.FINISHED
    }

    private fun collectRawData(sevenElevenProductCollectInfo: SevenElevenProductCollectInfo): List<ProductRawDataVO> {
        val productRawDataVOList = mutableListOf<ProductRawDataVO>()
        var preSize: Int
        var pageSize = sevenElevenProductCollectInfo.pageSize

        do {
            preSize = productRawDataVOList.size
            val document = JsoupHandler.doFormPost(sevenElevenProductCollectInfo.url,
                mapOf(
                    ("pTab" to sevenElevenProductCollectInfo.pTab),
                    ("intPageSize" to pageSize.toString())
                )
            )
            val body = document.body()

            val productElementList = body.getElementsByClass("pic_product")

            productElementList.forEach { productRawElement ->
                //ex) https://www.7-eleven.co.kr/upload/product/8809838/034001.1.jpg
                val imgUrl = productRawElement.getElementsByTag("img").first()!!.absUrl("src")

                val urlSplitStr = imgUrl.split("/product")[1]
                    .split("/")
                val barcode = urlSplitStr[1] + urlSplitStr[2].split(".")[0]

                val infoElement = productRawElement.getElementsByClass("infowrap").first()!!

                val nameSplit = infoElement.getElementsByClass("name").first()!!.text().split(")")
                val name = nameSplit[1]
                val brandName = nameSplit[0]

                val priceStr = infoElement.getElementsByClass("price").first()!!.text()

                // 세븐일레븐은 매 요청마다 전체 리스트가 반환됌, 이미 추가된 상품 제외
                if(!productRawDataVOList.any { it.name == name && it.brandName == brandName }){
                    val productRawDataVO = ProductRawDataVO(
                        name = name,
                        brandName = if(sevenElevenProductCollectInfo.isPbProduct) brandName else RetailerType.SEVEN_ELEVEN.retailerName,
                        price = priceStr.commaStringToLong(),
                        categoryType = sevenElevenProductCollectInfo.parseProductCategoryType(name),
                        barcode = barcode,
                        imageUrl = imgUrl,
                        retailerType = RetailerType.SEVEN_ELEVEN,
                        isPbProduct = sevenElevenProductCollectInfo.isPbProduct
                    )
                    productRawDataVOList.add(productRawDataVO)
                }
            }
            pageSize += sevenElevenProductCollectInfo.pageSize
        } while (preSize < productRawDataVOList.size)

        return productRawDataVOList
    }

    private fun saveProductData(productRawDataVOList: List<ProductRawDataVO>) {
        //TODO: dup check name with db
        productRawDataVOList.forEach {
            println(it.toString())
        }
    }
}