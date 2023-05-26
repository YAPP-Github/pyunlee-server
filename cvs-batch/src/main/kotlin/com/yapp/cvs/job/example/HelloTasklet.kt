package com.yapp.cvs.job.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

open class HelloTasklet : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info("hello")
        return RepeatStatus.FINISHED
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(HelloTasklet::class.java)
    }
}
