package com.yapp.cvs.api.member.dto

import com.yapp.cvs.domain.member.vo.MemberUpdateVO

class MemberUpdateDTO(
    val nickname: String
) {
    fun toVO(): MemberUpdateVO {
        return MemberUpdateVO(nickname = nickname)
    }
}