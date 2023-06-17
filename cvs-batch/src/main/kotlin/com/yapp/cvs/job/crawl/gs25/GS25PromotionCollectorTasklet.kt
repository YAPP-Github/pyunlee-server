package com.yapp.cvs.job.crawl.gs25

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.support.GS25ProductCollectSupport
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus

class GS25PromotionCollectorTasklet(
    private val productDataProcessor: ProductDataProcessor,
) : GS25ProductCollectorTasklet(productDataProcessor) {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val gs25ProductCollection = GS25ProductCollectSupport.values().filter { it.promotionType != null }
        gs25ProductCollection.forEach {
            savePromotionData(collectRawData(it), it.promotionType!!)
        }
        return RepeatStatus.FINISHED
    }

    private fun savePromotionData(
        productRawDataVOList: List<ProductRawDataVO>,
        productPromotionType: ProductPromotionType
    ) {
        log.info("${productRawDataVOList.size}건 저장")
        productRawDataVOList.forEach {
            productDataProcessor.savePromotion(it, productPromotionType)
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
