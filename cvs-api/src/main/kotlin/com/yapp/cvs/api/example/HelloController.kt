package com.yapp.cvs.api.example

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(authentication: Authentication): String {
        authentication.principal
        return "hello"
    }

    @GetMapping("/")
    fun hi(): String {
        return "login"
    }
}
