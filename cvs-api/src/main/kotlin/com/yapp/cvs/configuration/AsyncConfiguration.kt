package com.yapp.cvs.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class AsyncConfiguration {
    @Bean(name = ["productLikeSummaryTaskExecutor"])
    fun productLikeSummaryTaskExecutor(): Executor{
        return createTaskExecutor("Product-Like-Summary-Task")
    }

    @Bean(name = ["productCommentLikeSummaryTaskExecutor"])
    fun productCommentLikeSummaryTaskExecutor(): Executor{
        return createTaskExecutor("Product-Comment-Like-Summary-Task")
    }

    private fun createTaskExecutor(name: String): Executor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = CORE_POOL_SIZE
        taskExecutor.maxPoolSize = MAX_POOL_SIZE
        taskExecutor.queueCapacity = QUEUE_CAPACITY
        taskExecutor.setThreadNamePrefix(name)
        taskExecutor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        taskExecutor.initialize()
        return taskExecutor
    }

    companion object {
        private const val CORE_POOL_SIZE = 1
        private const val MAX_POOL_SIZE = 20
        private const val QUEUE_CAPACITY = 500
    }
}