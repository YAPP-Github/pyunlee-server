package com.yapp.cvs.api.example

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check")
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
