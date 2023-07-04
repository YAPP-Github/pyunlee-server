package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentHistory
import com.yapp.cvs.domain.comment.repository.ProductCommentHistoryRepository
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import org.springframework.stereotype.Service

@Service
class ProductCommentHistoryService(
    private val productCommentHistoryRepository: ProductCommentHistoryRepository
) {
    fun findProductCommentsPage(productId: Long, productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO> {
        return productCommentHistoryRepository.findAllByProductIdAndPageOffset(productId, productCommentSearchVO)
    }

    fun createCommentHistory(productId: Long, memberId: Long, content: String): ProductCommentHistory {
        val comment = ProductCommentHistory(productId = productId, memberId = memberId, content = content)
        return productCommentHistoryRepository.save(comment)
    }
}
