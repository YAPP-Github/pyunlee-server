package com.yapp.cvs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CvsApiApplication

fun main(args: Array<String>) {
    runApplication<CvsApiApplication>(*args)
}
