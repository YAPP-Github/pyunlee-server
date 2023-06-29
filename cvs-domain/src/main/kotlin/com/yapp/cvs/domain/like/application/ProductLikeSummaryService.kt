package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.repository.MemberProductLikeMappingRepository
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import org.springframework.stereotype.Service


@Service
class ProductLikeSummaryService(
        private val memberProductLikeMappingRepository: MemberProductLikeMappingRepository
) {
    fun upsertProductLikeMapping(productId: Long, memberId: Long, likeType: ProductLikeType) {
        val memberProductLikeMapping = memberProductLikeMappingRepository.findByProductIdAndMemberId(productId, memberId)
                ?.apply { this.likeType = likeType }
                ?: MemberProductLikeMapping(productId = productId, memberId = memberId, likeType = likeType)
        memberProductLikeMappingRepository.save(memberProductLikeMapping)
    }

    fun getProductLikeSummary(productId: Long): ProductLikeSummaryVO {
        val mappings = memberProductLikeMappingRepository.findAllByProductId(productId)
        return ProductLikeSummaryVO(
                productId = productId,
                likeCount = mappings.count { it.likeType == ProductLikeType.LIKE }.toLong(),
                dislikeCount = mappings.count { it.likeType == ProductLikeType.DISLIKE }.toLong(),
                totalCount = mappings.size.toLong()
        )
    }
}
