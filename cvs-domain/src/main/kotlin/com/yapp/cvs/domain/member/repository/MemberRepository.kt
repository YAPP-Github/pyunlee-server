package com.yapp.cvs.domain.member.repository

import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmailAndLoginType(email: String, loginType: OAuthLoginType): Member?
}