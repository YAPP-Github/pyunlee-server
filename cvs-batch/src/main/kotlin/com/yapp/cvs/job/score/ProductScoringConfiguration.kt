package com.yapp.cvs.job.score

import com.yapp.cvs.domain.product.repository.ProductRepository
import com.yapp.cvs.domain.product.repository.ProductScoreRepository
import com.yapp.cvs.job.config.BatchConfig
import com.yapp.cvs.job.score.ProductScoringConfiguration.Companion.J0B_NAME
import com.yapp.cvs.support.RunUniqueIdIncrementer
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(value = [BatchConfig.SPRING_BATCH_JOB_NAMES], havingValue = J0B_NAME)
class ProductScoringConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val productRepository: ProductRepository,
    private val productScoreRepository: ProductScoreRepository
) {
    companion object {
        const val J0B_NAME = "product-scoring-job"
    }


    @Bean
    fun productScoringJob(): Job {
        return jobBuilderFactory[J0B_NAME]
            .start(productScoringStep())
            .incrementer(RunUniqueIdIncrementer())
            .build()
    }

    @Bean
    @JobScope
    fun productScoringStep(): Step {
        return stepBuilderFactory["collect"]
            .tasklet(productScoringTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @StepScope
    fun productScoringTasklet(): Tasklet = ProductScoringTasklet(productRepository, productScoreRepository)
}