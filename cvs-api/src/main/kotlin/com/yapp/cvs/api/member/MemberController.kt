package com.yapp.cvs.api.member

import com.yapp.cvs.api.member.dto.MemberAccessDTO
import com.yapp.cvs.api.member.dto.MemberAccessRequestDTO
import com.yapp.cvs.domain.member.application.MemberProcessor
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원")
@RestController
@RequestMapping("/api/v1/member")
@SecurityRequirement(name = "X-ACCESS-TOKEN")
class MemberController(
    private val memberProcessor: MemberProcessor
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody accessRequestDTO: MemberAccessRequestDTO): MemberAccessDTO {
        return MemberAccessDTO(memberProcessor.signUp(accessRequestDTO.toVO()))
    }

    @PostMapping("/login")
    fun login(@RequestBody accessRequestDTO: MemberAccessRequestDTO): MemberAccessDTO {
        return MemberAccessDTO(memberProcessor.login(accessRequestDTO.toVO()))
    }
}