package com.yapp.cvs.job.crawl.seveneleven

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.extension.commaStringToLong
import com.yapp.cvs.support.JsoupHandler
import com.yapp.cvs.support.ProductCategoryRule
import com.yapp.cvs.support.ProductDataParser
import com.yapp.cvs.support.SevenElevenProductCollectInfo
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

open class SevenElevenProductCollectTasklet(
    private val productDataProcessor: ProductDataProcessor
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val sevenElevenProductCollectInfoList = SevenElevenProductCollectInfo.values()
            .filter { it.promotionType == null }

        sevenElevenProductCollectInfoList.forEach {
            saveProductData(collectRawData(it))
        }

        return RepeatStatus.FINISHED
    }

    fun collectRawData(sevenElevenProductCollectInfo: SevenElevenProductCollectInfo): List<ProductRawDataVO> {
        val productRawDataVOList = mutableListOf<ProductRawDataVO>()
        var preSize: Int
        var pageSize = sevenElevenProductCollectInfo.pageSize
        var pageNum = 1
        log.info("${sevenElevenProductCollectInfo.name} 정보 조회 시작")
        do {
            preSize = productRawDataVOList.size
            val productElementList = collectProductElementList(sevenElevenProductCollectInfo, pageNum, pageSize)

            productElementList.forEach { productRawElement ->
                try {
                    val infoElement = productRawElement.getElementsByClass("infowrap").first()!!

                    val nameStr = infoElement.getElementsByClass("name").first()!!.text()
                    val name = ProductDataParser.parseProductName(nameStr)
                    val brandName = ProductDataParser.parseBrandName(nameStr)

                    val priceStr = infoElement.getElementsByClass("price").first()!!.text()

                    val imgUrl = productRawElement.getElementsByTag("img").first()!!.absUrl("src")
                    val barcode = parseBarcode(imgUrl)

                    // 세븐일레븐은 매 요청마다 전체 리스트가 반환됌, 이미 추가된 상품 제외
                    if (!productRawDataVOList.any { it.barcode == barcode }) {
                        val productRawDataVO = ProductRawDataVO(
                            name = name,
                            brandName = brandName,
                            price = priceStr.commaStringToLong(),
                            categoryType = sevenElevenProductCollectInfo.productCategoryType
                                ?: ProductCategoryRule.parse(name),
                            barcode = barcode,
                            imageUrl = imgUrl,
                            retailerType = RetailerType.SEVEN_ELEVEN,
                            isPbProduct = sevenElevenProductCollectInfo.isPbProduct,
                        )
                        productRawDataVOList.add(productRawDataVO)
                    }
                } catch (_: Exception) {
                }
            }
            pageSize += sevenElevenProductCollectInfo.pageSize
            pageNum += 1
        } while (preSize < productRawDataVOList.size)

        return productRawDataVOList
    }

    private fun collectProductElementList(
        sevenElevenProductCollectInfo: SevenElevenProductCollectInfo,
        pageNum: Int,
        pageSize: Int,
    ): Elements {
        val body = JsoupHandler.doFormPost(
            sevenElevenProductCollectInfo.url,
            mapOf(
                ("pTab" to sevenElevenProductCollectInfo.pTab),
                ("intCurrPage" to pageNum.toString()),
                ("intPageSize" to pageSize.toString()),
            ),
        ).body()

        return body.getElementsByClass("pic_product")
    }

    private fun parseBarcode(imgUrl: String): String {
        // ex) https://www.7-eleven.co.kr/upload/product/8809838/034001.1.jpg
        val urlSplitStr = imgUrl.split("/product")[1]
            .split("/")

        return urlSplitStr[1] + urlSplitStr[2].split(".")[0]
    }

    private fun saveProductData(productRawDataVOList: List<ProductRawDataVO>) {
        log.info("${productRawDataVOList.size}건 저장")

        productRawDataVOList.forEach {
            productDataProcessor.saveProduct(it)
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
