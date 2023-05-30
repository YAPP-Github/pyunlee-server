package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.job.crawl.ProductCollectorService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class CUCollectorTasklet(
    private val cuCollectorService: ProductCollectorService,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        ProductCategory.values().forEach {
            val products = cuCollectorService.getCollection(it)
            cuCollectorService.validateAll(products)
            cuCollectorService.saveAll(products)
        }
        return RepeatStatus.FINISHED
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(CUCollectorTasklet::class.java)
    }
}
