package com.yapp.cvs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CvsAdminApplication

fun main(args: Array<String>) {
	runApplication<CvsAdminApplication>(*args)
}
