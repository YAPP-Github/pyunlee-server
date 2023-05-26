package com.yapp.cvs

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.system.exitProcess

@SpringBootApplication
@EnableBatchProcessing
class CvsBatchApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application,batch")
    val context = SpringApplication.run(CvsBatchApplication::class.java, *args)
    exitProcess(SpringApplication.exit(context))
}
