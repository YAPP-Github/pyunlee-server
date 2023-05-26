package com.yapp.cvs.job.example

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

open class HelloListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        println("beforeJob")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("afterJob")
    }
}
