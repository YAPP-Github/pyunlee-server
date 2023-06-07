package com.yapp.cvs.job.crawl.gs25

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.job.crawl.gs25.handler.GS25WebdriverHandler
import com.yapp.cvs.support.GS25ProductCollectSupport
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class GS25CollectorTasklet(
    private val webdriverHandler: GS25WebdriverHandler,
    private val productDataProcessor: ProductDataProcessor,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val gs25ProductCollection = GS25ProductCollectSupport.values()
        gs25ProductCollection.forEach {
            webdriverHandler.setCategoryTo(it)
            saveProductData(webdriverHandler.collect(it))
        }
        return RepeatStatus.FINISHED
    }

    private fun saveProductData(productRawDataVOList: List<ProductRawDataVO>) {
        productRawDataVOList.forEach {
            productDataProcessor.saveProduct(it)
        }
    }
}
