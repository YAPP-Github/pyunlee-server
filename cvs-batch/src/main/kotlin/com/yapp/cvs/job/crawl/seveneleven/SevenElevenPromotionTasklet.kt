package com.yapp.cvs.job.crawl.seveneleven

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.support.SevenElevenProductCollectInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus

class SevenElevenPromotionTasklet(
    private val productDataProcessor: ProductDataProcessor
) : SevenElevenProductCollectTasklet(productDataProcessor) {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val promotionProductCollectInfo = SevenElevenProductCollectInfo.values()
            .filter { it.promotionType != null }

        promotionProductCollectInfo.forEach {
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
