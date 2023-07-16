package com.yapp.cvs.domain.home.vo

import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.product.vo.ProductVO

class HomeInfoVO(
    val newProductVOList: List<ProductVO>,
    val popularProductVOList: List<ProductVO>,
    val promotionProductVOMap: Map<RetailerType, List<ProductVO>>,
    val recentProductCommentVOList: List<ProductCommentVO>
) {
}