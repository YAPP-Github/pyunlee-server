package com.yapp.cvs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CvsWebApplication

fun main(args: Array<String>) {
    runApplication<CvsWebApplication>(*args)
}
