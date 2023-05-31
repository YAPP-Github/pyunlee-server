package com.yapp.cvs.job.config

import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfig {
    companion object {
        const val SPRING_BATCH_JOB_NAMES = "spring.batch.job.names"
    }
}
