package com.yapp.cvs.domain.comment.view

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.member.entity.Member

class ProductCommentView(
    val productComment: ProductComment,
    val member: Member,
    val likeCount: Long?,
    val productLikeType: ProductLikeType?
) {
}