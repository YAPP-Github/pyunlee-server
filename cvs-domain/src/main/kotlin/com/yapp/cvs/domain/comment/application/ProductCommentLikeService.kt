package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentLike
import com.yapp.cvs.domain.comment.repository.ProductCommentLikeRepository
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class ProductCommentLikeService(
        val productCommentLikeRepository: ProductCommentLikeRepository,
) {
    fun like(memberProductMappingKey: MemberProductMappingKey, memberId: Long): ProductCommentLike {
        validateCommentLikeDuplication(memberProductMappingKey, memberId)
        val commentLike = ProductCommentLike.like(memberProductMappingKey, memberId)
        return productCommentLikeRepository.save(commentLike)
    }

    fun cancel(memberProductMappingKey: MemberProductMappingKey, memberId: Long): ProductCommentLike {
        return productCommentLikeRepository
                .findLatestLike(memberProductMappingKey.productId, memberProductMappingKey.memberId, memberId)
                ?.apply { valid = false }
                ?: throw BadRequestException("이미 좋아요를 취소한 코멘트입니다.")
    }

    private fun validateCommentLikeDuplication(memberProductMappingKey: MemberProductMappingKey, memberId: Long) {
        if (productCommentLikeRepository
                        .findLatestLike(memberProductMappingKey.productId, memberProductMappingKey.memberId, memberId) != null) {
            throw BadRequestException("이미 좋아요 요청한 코멘트입니다.")
        }
    }
}
