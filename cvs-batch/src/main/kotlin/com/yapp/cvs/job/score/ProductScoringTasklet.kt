package com.yapp.cvs.job.score

import com.yapp.cvs.domain.product.entity.ProductScore
import com.yapp.cvs.domain.product.repository.ProductRepository
import com.yapp.cvs.domain.product.repository.ProductScoreRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class ProductScoringTasklet(
    private val productRepository: ProductRepository,
    private val productScoreRepository: ProductScoreRepository
): Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val list = productRepository.findAll().
            map { ProductScore(
                productId = it.productId!!,
                score = 0L
            ) }

        productScoreRepository.saveAll(list)

        return RepeatStatus.FINISHED
    }
}