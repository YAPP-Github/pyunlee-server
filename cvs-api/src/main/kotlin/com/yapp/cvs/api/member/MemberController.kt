package com.yapp.cvs.api.member

import com.yapp.cvs.api.member.dto.MemberAccessDTO
import com.yapp.cvs.api.member.dto.MemberAccessRequestDTO
import com.yapp.cvs.api.member.dto.MemberUpdateDTO
import com.yapp.cvs.api.member.dto.MemberSummaryDTO
import com.yapp.cvs.configuration.swagger.SwaggerConfig.Companion.SWAGGER_AUTH_KEY
import com.yapp.cvs.domain.member.application.MemberDeletionProcessor
import com.yapp.cvs.domain.member.application.MemberProcessor
import com.yapp.cvs.domain.member.entity.Member
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원")
@RestController
@RequestMapping("/api/v1/member")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class MemberController(
    private val memberProcessor: MemberProcessor,
    private val memberDeletionProcessor: MemberDeletionProcessor
) {
    @GetMapping("/summary")
    @Operation(summary = "내 정보를 가져옵니다.")
    fun myPage(@Parameter(hidden = true) member: Member): MemberSummaryDTO {
        return MemberSummaryDTO.from(memberProcessor.getMemberSummary(member))
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입을 요청합니다.")
    fun signUp(@RequestBody accessRequestDTO: MemberAccessRequestDTO): MemberAccessDTO {
        return MemberAccessDTO(memberProcessor.signUp(accessRequestDTO.toVO()))
    }

    @PostMapping("/login")
    fun login(@RequestBody accessRequestDTO: MemberAccessRequestDTO): MemberAccessDTO {
        return MemberAccessDTO(memberProcessor.login(accessRequestDTO.toVO()))
    }

    @PostMapping("/nickname")
    @Operation(summary = "닉네임을 변경합니다.")
    fun updateNickname(
            @Parameter(hidden = true) member: Member,
            @RequestBody memberNicknameDTO: MemberUpdateDTO
    ) {
        memberProcessor.update(member, memberNicknameDTO.toVO())
    }

    @PostMapping("/delete")
    fun delete(member: Member) {
        return memberDeletionProcessor.delete(member)
    }
}