package com.yapp.cvs.domain.comment.view

import com.yapp.cvs.domain.comment.entity.ProductComment

class ProductCommentView(
    val productComment: ProductComment,
    val likeCount: Long?
) {
}