package com.yapp.cvs.job.crawl.gs25

import com.fasterxml.jackson.databind.ObjectMapper
import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.domains.enums.RetailerType
import com.yapp.cvs.support.GS25ProductCollectSupport
import com.yapp.cvs.support.ProductCategoryRule
import com.yapp.cvs.support.ProductDataParser
import com.yapp.cvs.support.RestTemplateHandler
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

open class GS25ProductCollectorTasklet(
    private val productDataProcessor: ProductDataProcessor,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val gs25ProductCollection = GS25ProductCollectSupport.values().filter { it.promotionType == null }
        gs25ProductCollection.forEach {
            saveProductData(collectRawData(it))
        }
        return RepeatStatus.FINISHED
    }

    protected fun collectRawData(category: GS25ProductCollectSupport): List<ProductRawDataVO> {
        log.info("Target Category : ${category.productCategoryType?.displayName ?: category.name}")
        val productCollections = mutableListOf<ProductRawDataVO>()
        val productElements = this.collectProductElements(category)
        productElements.forEach {
            try {
                val jsonObject = it as JSONObject
                val title = jsonObject["goodsNm"].toString()
                val price = jsonObject["price"].toString()
                val imgURL = jsonObject["attFileNm"].toString()
                productCollections.add(
                    ProductRawDataVO(
                        name = ProductDataParser.parseProductName(title),
                        brandName = ProductDataParser.parseBrandName(title),
                        price = ProductDataParser.parsePrice(price),
                        categoryType = category.productCategoryType ?: ProductCategoryRule.parse(title),
                        barcode = ProductDataParser.parseProductCode(imgURL) ?: "",
                        imageUrl = imgURL,
                        retailerType = RetailerType.CU,
                        isPbProduct = category.isPbProduct
                    )
                )
            } catch (_: Exception) {
            }
        }
        return productCollections
    }

    private fun collectProductElements(category: GS25ProductCollectSupport): JSONArray {
        val jsonString = RestTemplateHandler.doGet(category.url, category.getQueryParams())
        val jsonObject = ObjectMapper().readValue(jsonString, String::class.java)
        return JSONObject(jsonObject).getJSONArray(category.jsonDataKey)
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
