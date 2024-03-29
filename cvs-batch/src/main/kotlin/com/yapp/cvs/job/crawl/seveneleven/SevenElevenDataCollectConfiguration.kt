package com.yapp.cvs.job.crawl.seveneleven

import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.job.config.BatchConfig
import com.yapp.cvs.job.crawl.seveneleven.SevenElevenDataCollectConfiguration.Companion.J0B_NAME
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
class SevenElevenDataCollectConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val productDataProcessor: ProductDataProcessor,
) {
    companion object {
        const val J0B_NAME = "seven-eleven-collect-job"
    }

    @Bean
    fun sevenElevenDataCollectJob(): Job {
        return jobBuilderFactory[J0B_NAME]
            .start(sevenElevenPromotionCollectStep())
            //.next(sevenElevenPromotionCollectStep())
            .incrementer(RunUniqueIdIncrementer())
            .build()
    }

    @Bean
    @JobScope
    fun sevenElevenProductCollectStep(): Step {
        return stepBuilderFactory["collect"]
            .tasklet(sevenElevenProductCollectTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @JobScope
    fun sevenElevenPromotionCollectStep(): Step {
        return stepBuilderFactory["collect"]
            .tasklet(sevenElevenPromotionCollectTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @StepScope
    fun sevenElevenProductCollectTasklet(): Tasklet = SevenElevenProductCollectTasklet(productDataProcessor)

    @Bean
    @StepScope
    fun sevenElevenPromotionCollectTasklet(): Tasklet = SevenElevenPromotionTasklet(productDataProcessor)
}
