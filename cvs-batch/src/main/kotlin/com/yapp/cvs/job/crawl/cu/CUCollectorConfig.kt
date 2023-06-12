package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.job.config.BatchConfig
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

@ConditionalOnProperty(
    value = [BatchConfig.SPRING_BATCH_JOB_NAMES],
    havingValue = CUCollectorConfig.JOB_NAME,
)
@Configuration
class CUCollectorConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val productDataProcessor: ProductDataProcessor
) {
    companion object {
        const val JOB_NAME = "cu-collect-job"
        const val STEP_NAME = "cu-collect-job-step"
    }

    @Bean
    fun cuCollectorJob(): Job {
        return jobBuilderFactory[JOB_NAME]
            .start(cuProductCollectorStep())
            .next(cuPromotionStep())
            .incrementer(RunUniqueIdIncrementer())
            .build()
    }

    @Bean
    @JobScope
    fun cuProductCollectorStep(): Step {
        return stepBuilderFactory[STEP_NAME]
            .tasklet(cuProductCollectorTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @JobScope
    fun cuPromotionStep(): Step {
        return stepBuilderFactory[STEP_NAME]
            .tasklet(cuPromotionCollectorTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @StepScope
    fun cuProductCollectorTasklet(): Tasklet = CUProductCollectorTasklet(productDataProcessor)

    @Bean
    @StepScope
    fun cuPromotionCollectorTasklet(): Tasklet = CUPromotionCollectorTasklet(productDataProcessor)
}
