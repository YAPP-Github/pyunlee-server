package com.yapp.cvs.domain.member.repository

import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmailAndLoginTypeAndMemberStatus(email: String, loginType: OAuthLoginType, memberStatus: MemberStatus): Member?
    fun countByNickName(nickName: String): Long
}