package com.yapp.cvs.domain.like.vo

import com.yapp.cvs.domain.like.entity.MemberProductMappingKey

class ProductLikeRequestVO(
    val productId: Long,
    val memberId: Long
) {
    val memberProductMappingKey: MemberProductMappingKey = MemberProductMappingKey(
        productId = productId,
        memberId = memberId
    )
}