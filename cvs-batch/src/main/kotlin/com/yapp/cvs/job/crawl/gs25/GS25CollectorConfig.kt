package com.yapp.cvs.job.crawl.gs25

import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.job.config.BatchConfig
import com.yapp.cvs.job.crawl.gs25.handler.GS25WebdriverHandler
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
    havingValue = GS25CollectorConfig.JOB_NAME,
)
@Configuration
class GS25CollectorConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val productDataProcessor: ProductDataProcessor,
    private val webdriverHandler: GS25WebdriverHandler,
) {
    companion object {
        const val JOB_NAME = "gs25-collect-job"
        const val STEP_NAME = "gs25-collect-job-step"
    }

    @Bean
    fun gs25CollectorJob(): Job {
        return jobBuilderFactory[JOB_NAME]
            .start(gs25CollectorStep())
            .incrementer(RunUniqueIdIncrementer())
            .build()
    }

    @Bean
    @JobScope
    fun gs25CollectorStep(): Step {
        return stepBuilderFactory[STEP_NAME]
            .tasklet(gs25CollectorTasklet())
            .allowStartIfComplete(true)
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @StepScope
    fun gs25CollectorTasklet(): Tasklet = GS25CollectorTasklet(
        webdriverHandler = webdriverHandler,
        productDataProcessor = productDataProcessor,
    )
}
