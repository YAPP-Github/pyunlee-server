package com.yapp.cvs.job.crawl.gs25

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.job.crawl.gs25.handler.GS25ProductCollectSupport
import com.yapp.cvs.job.crawl.gs25.handler.GS25WebdriverHandler
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class GS25CollectorTasklet(
    private val webdriverHandler: GS25WebdriverHandler,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val driver = webdriverHandler.initializeWebdriver()
        val gs25ProductCollection = GS25ProductCollectSupport.values()
        val productRawDataVOList = mutableListOf<ProductRawDataVO>()

        gs25ProductCollection.forEach {
            webdriverHandler.setCategoryTo(it, driver)
            Thread.sleep(4000)
            productRawDataVOList.addAll(webdriverHandler.collect(it, driver))
        }
        // todo : save all items
        return RepeatStatus.FINISHED
    }
}
