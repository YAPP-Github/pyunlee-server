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
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = 1
        taskExecutor.maxPoolSize = 20
        taskExecutor.queueCapacity = 500
        taskExecutor.setThreadNamePrefix("Product-Like-Summary-Task")
        taskExecutor.setRejectedExecutionHandler(ThreadPoolExecutor.DiscardPolicy())
        taskExecutor.initialize()
        return taskExecutor
    }
}