package com.yapp.cvs.job.example

import com.yapp.cvs.job.config.BatchConfig
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnProperty(
    value = [BatchConfig.SPRING_BATCH_JOB_NAMES],
    havingValue = HelloConfig.JOB_NAME,
)
@Configuration
class HelloConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val jobRepository: JobRepository,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    companion object {
        const val JOB_NAME = "hello-job"
        const val STEP_NAME = "hello-step"
    }

    @Bean
    fun helloJob(): Job {
        return jobBuilderFactory[JOB_NAME]
            .repository(jobRepository)
            .start(helloStep())
            .listener(helloListener())
            .incrementer(RunIdIncrementer())
            .build()
    }

    /** 단순 조회만 수행하기 때문에 ResourcelessTransactionManager 로 내부적으로 관리 */
    @Bean
    @JobScope
    fun helloStep(): Step {
        return stepBuilderFactory[STEP_NAME]
            .tasklet(helloTasklet())
            .transactionManager(ResourcelessTransactionManager())
            .build()
    }

    @Bean
    @StepScope
    fun helloTasklet() = HelloTasklet()

    @Bean
    @JobScope
    fun helloListener() = HelloListener()
}
