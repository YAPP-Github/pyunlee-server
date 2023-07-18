package com.yapp.cvs.domain.comment.view

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.product.entity.Product

class ProductCommentDetailView(
    val productComment: ProductComment,
    val likeCount: Long?,
    val product: Product,
    val productLikeType: ProductLikeType?
) {
}