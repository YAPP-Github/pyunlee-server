package com.yapp.cvs.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
@EnableBatchProcessing
class CvsBatchApplication

fun main(args: Array<String>) {
    val context = runApplication<CvsBatchApplication>(*args)
    exitProcess(SpringApplication.exit(context))
}
