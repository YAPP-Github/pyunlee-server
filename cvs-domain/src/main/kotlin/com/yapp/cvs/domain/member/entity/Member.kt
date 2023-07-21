package com.yapp.cvs.domain.member.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.OAuthLoginType
import com.yapp.cvs.domain.enums.OAuthMember
import javax.persistence.*

@Entity
@Table(name = "members")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null,

    val email: String,

    @Enumerated(EnumType.STRING)
    val loginType: OAuthLoginType,

    var nickName: String,

    @Enumerated(EnumType.STRING)
    var memberStatus: MemberStatus,
) : BaseEntity() {
    fun deactivate() {
        memberStatus = MemberStatus.DEACTIVATED
    }

    companion object {
        fun create(oAuthMember: OAuthMember, nickName: String): Member{
            return Member(
                email = oAuthMember.email,
                loginType = oAuthMember.loginType,
                nickName = nickName,
                memberStatus = MemberStatus.ACTIVATED
            )
        }
    }
}