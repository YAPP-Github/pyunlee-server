package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentRatingSummary
import com.yapp.cvs.domain.comment.repository.ProductCommentRatingSummaryRepository
import com.yapp.cvs.exception.NotFoundSourceException
import org.springframework.stereotype.Service

@Service
class ProductCommentRatingSummaryService(
    val productCommentRatingSummaryRepository: ProductCommentRatingSummaryRepository
) {
    fun save(productCommentRatingSummary: ProductCommentRatingSummary) {
        productCommentRatingSummaryRepository.save(productCommentRatingSummary)
    }

    fun findByProductCommentId(productCommentId: Long): ProductCommentRatingSummary {
        return productCommentRatingSummaryRepository.findByProductCommentId(productCommentId)
            ?: throw NotFoundSourceException("not found productCommentRatingSummary ")
    }

    fun findByProductCommentIdOrDefault(productCommentId: Long): ProductCommentRatingSummary {
        return productCommentRatingSummaryRepository.findByProductCommentId(productCommentId)
            ?: ProductCommentRatingSummary.empty(productCommentId = productCommentId)
    }

    fun like(productCommentId: Long) {
        findByProductCommentIdOrDefault(productCommentId)
            .let {
                it.like()
                productCommentRatingSummaryRepository.save(it)
            }
    }

    fun cancel(productCommentId: Long) {
        productCommentRatingSummaryRepository.findByProductCommentId(productCommentId)
            ?.let {
                it.cancelLike()
                productCommentRatingSummaryRepository.save(it)
            }
    }
}
