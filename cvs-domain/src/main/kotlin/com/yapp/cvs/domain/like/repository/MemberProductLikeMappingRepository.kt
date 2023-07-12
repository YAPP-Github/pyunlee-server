package com.yapp.cvs.domain.like.repository

import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import org.springframework.data.jpa.repository.JpaRepository

interface MemberProductLikeMappingRepository
    : JpaRepository<MemberProductLikeMapping, Long>, MemberProductLikeMappingCustom {
    fun findByProductIdAndMemberId(productId: Long, memberId: Long): MemberProductLikeMapping?
}

interface MemberProductLikeMappingCustom {
    fun findAllByProductId(productId: Long): List<MemberProductLikeMapping>
    fun findAllByMemberId(memberId: Long): List<MemberProductLikeMapping>
}
