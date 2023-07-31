package com.yapp.cvs.domain.comment.view

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.enums.ProductLikeType

class ProductCommentView(
    val productComment: ProductComment,
    val likeCount: Long?,
    val productLikeType: ProductLikeType?
) {
}