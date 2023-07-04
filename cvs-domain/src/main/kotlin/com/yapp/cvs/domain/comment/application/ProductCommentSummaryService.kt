package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.repository.ProductCommentRepository
import org.springframework.stereotype.Service

@Service
class ProductCommentSummaryService(
        val productCommentRepository: ProductCommentRepository
) {
    fun upsertProductCommentMapping(productId: Long, memberId: Long, commentId: Long) {
        val mapping = productCommentRepository.findByProductIdAndMemberId(productId, memberId)
                ?.apply { productCommentHistoryId = commentId; valid = true }
                ?: ProductComment(productId = productId, memberId = memberId, productCommentHistoryId = commentId)
        productCommentRepository.save(mapping)
    }

    fun activateCommentMapping(productId: Long, memberId: Long) {
        productCommentRepository.findByProductIdAndMemberId(productId, memberId)?.apply { valid = true }
    }

    fun inactivateCommentMapping(productId: Long, memberId: Long) {
        productCommentRepository.findByProductIdAndMemberId(productId, memberId)?.apply { valid = false }
    }

    fun deleteCommentMapping(productId: Long, memberId: Long) {
        productCommentRepository.deleteByProductIdAndMemberId(productId, memberId)
    }
}
