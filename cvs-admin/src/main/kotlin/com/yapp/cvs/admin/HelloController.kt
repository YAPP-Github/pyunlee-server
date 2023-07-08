package com.yapp.cvs.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelloController {
    @GetMapping("/hello")
    fun hello(): String{
        return "index"
    }
}