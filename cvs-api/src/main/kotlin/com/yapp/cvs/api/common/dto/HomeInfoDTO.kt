package com.yapp.cvs.api.common.dto

import com.yapp.cvs.api.comment.dto.ProductCommentDetailDTO
import com.yapp.cvs.api.product.dto.ProductDTO
import com.yapp.cvs.domain.home.vo.HomeInfoVO

class HomeInfoDTO(
    val newProductList: List<ProductDTO>,
    val popularProductList: List<ProductDTO>,
    val promotionProductMap: Map<String, List<ProductDTO>>,
    val recentProductCommentList: List<ProductCommentDetailDTO>
) {
    companion object {
        fun from(homeInfoVO: HomeInfoVO): HomeInfoDTO {
            return HomeInfoDTO(
                newProductList = homeInfoVO.newProductVOList.map { ProductDTO.from(it) },
                popularProductList = homeInfoVO.popularProductVOList.map { ProductDTO.from(it) },
                promotionProductMap = homeInfoVO.promotionProductVOMap.entries.associate{promotions ->
                    promotions.key.name to promotions.value.map { ProductDTO.from(it) }
                },
                recentProductCommentList = homeInfoVO.recentProductCommentVOList.map { ProductCommentDetailDTO.from(it) }
            )
        }
    }
}