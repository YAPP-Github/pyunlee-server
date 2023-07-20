package com.yapp.cvs.api.common

import com.yapp.cvs.api.common.dto.HomeInfoDTO
import com.yapp.cvs.domain.home.application.HomeProcessor
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "홈")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "X_ACCESS_TOKEN")
class HomeController(
    private val homeProcessor: HomeProcessor
) {

    @GetMapping("/home")
    fun home(): HomeInfoDTO {
        return HomeInfoDTO.from(homeProcessor.getHomeInfo())
    }
}