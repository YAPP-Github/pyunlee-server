package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO


data class ProductCommentSearchDTO(
        val pageSize: Long = 10,
        val offsetCommentMappingId: Long? = null,
) {
    fun toVO(): ProductCommentSearchVO {
        return ProductCommentSearchVO(
                pageSize = pageSize,
                offsetProductCommentId = offsetCommentMappingId
        )
    }
}