package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.domain.like.repository.MemberProductLikeMappingRepository
import org.springframework.stereotype.Service

@Service
class MemberProductLikeMappingService(
    private val memberProductLikeMappingRepository: MemberProductLikeMappingRepository
) {
    fun saveMemberProductLikeMapping(memberProductLikeMapping: MemberProductLikeMapping) {
        memberProductLikeMappingRepository.save(memberProductLikeMapping)
    }

    fun findByMemberProductLike(memberProductMappingKey: MemberProductMappingKey): MemberProductLikeMapping? {
        return memberProductLikeMappingRepository.findByProductIdAndMemberId(memberProductMappingKey.productId, memberProductMappingKey.memberId)
    }

    fun findAllByMember(memberId: Long): List<MemberProductLikeMapping> {
        return memberProductLikeMappingRepository.findAllByMemberId(memberId)
    }

    fun countByMemberId(memberId: Long): Long {
        return memberProductLikeMappingRepository.countByMemberId(memberId)
    }
}