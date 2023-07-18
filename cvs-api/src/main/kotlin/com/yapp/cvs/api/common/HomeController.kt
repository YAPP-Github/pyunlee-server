package com.yapp.cvs.api.common

import com.yapp.cvs.api.common.dto.HomeInfoDTO
import com.yapp.cvs.domain.home.application.HomeProcessor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class HomeController(
    private val homeProcessor: HomeProcessor
) {

    @GetMapping("/home")
    fun home(): HomeInfoDTO {
        return HomeInfoDTO.from(homeProcessor.getHomeInfo())
    }
}