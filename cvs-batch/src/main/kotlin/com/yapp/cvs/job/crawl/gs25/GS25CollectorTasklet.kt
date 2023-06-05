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
        val driver = webdriverHandler.initializeWebdriver()
        try {
            gs25ProductCollection.forEach {
                webdriverHandler.setCategoryTo(it, driver)
                Thread.sleep(4000)
                saveProductData(webdriverHandler.collect(it, driver))
            }
        } finally {
            driver.quit()
        }
        return RepeatStatus.FINISHED
    }

    private fun saveProductData(productRawDataVOList: List<ProductRawDataVO>) {
        productRawDataVOList.forEach {
            productDataProcessor.saveProduct(it)
        }
    }
}
