package com.yapp.cvs.api.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }

    @GetMapping("/")
    fun hi(): String {
        return "login"
    }
}
