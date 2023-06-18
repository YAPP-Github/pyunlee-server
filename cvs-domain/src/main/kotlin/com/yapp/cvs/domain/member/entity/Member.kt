package com.yapp.cvs.domain.member.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.MemberStatus
import com.yapp.cvs.domain.enums.OAuthLoginType
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

    val nickName: String,

    @Enumerated(EnumType.STRING)
    val memberStatus: MemberStatus,
) : BaseEntity() {

    companion object {
        fun google(attributes: Map<String, Any>, nickName: String): Member {
            return Member(
                email = attributes["email"].toString(),
                loginType = OAuthLoginType.GOOGLE,
                nickName = nickName,
                memberStatus = MemberStatus.ACTIVATED
            )
        }
    }
}
