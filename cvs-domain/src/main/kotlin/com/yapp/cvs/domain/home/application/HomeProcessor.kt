package com.yapp.cvs.domain.home.application

import com.yapp.cvs.domain.base.vo.OffsetSearchVO
import com.yapp.cvs.domain.comment.application.ProductCommentService
import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.domain.home.vo.HomeInfoVO
import com.yapp.cvs.domain.product.application.ProductService
import com.yapp.cvs.domain.product.entity.ProductOrderType
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import com.yapp.cvs.domain.product.vo.ProductVO
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class HomeProcessor(
    private val productService: ProductService,
    private val productCommentService: ProductCommentService
) {
    fun getHomeInfo(): HomeInfoVO {
        return HomeInfoVO(
            newProductVOList = productService.searchProductList(homeOffsetSearchVO(), newProductSearchVO()).map { ProductVO.from(it) },
            popularProductVOList = productService.searchProductList(homeOffsetSearchVO(), popularProductSearchVO()).map { ProductVO.from(it) },
            promotionProductVOMap = RetailerType.values().associateWith { retailerType ->
                productService.searchProductList(homeOffsetSearchVO(), promotionProductSearchVO(retailerType)).map { ProductVO.from(it) } },
            recentProductCommentVOList = productCommentService.findRecentCommentList(10).map { it }
        )
    }

    private fun homeOffsetSearchVO(): OffsetSearchVO {
        return OffsetSearchVO(pageSize = 10, offsetId = null)
    }

    private fun newProductSearchVO(): ProductSearchVO {
        return ProductSearchVO(
            minPrice = null,
            maxPrice = null,
            productCategoryTypeList = emptyList(),
            pbOnly = false,
            promotionTypeList = emptyList(),
            promotionRetailerList = emptyList(),
            appliedDateTime = LocalDateTime.now(),
            keyWord = null,
            orderBy = ProductOrderType.RECENT
        )
    }

    private fun popularProductSearchVO(): ProductSearchVO {
        return ProductSearchVO(
            minPrice = null,
            maxPrice = null,
            productCategoryTypeList = emptyList(),
            pbOnly = false,
            promotionTypeList = emptyList(),
            promotionRetailerList = emptyList(),
            appliedDateTime = LocalDateTime.now(),
            keyWord = null,
            orderBy = ProductOrderType.POPULAR
        )
    }

    private fun promotionProductSearchVO(retailerType: RetailerType): ProductSearchVO {
        return ProductSearchVO(
            minPrice = null,
            maxPrice = null,
            productCategoryTypeList = emptyList(),
            pbOnly = false,
            promotionTypeList = ProductPromotionType.values().toList(),
            promotionRetailerList = listOf(retailerType),
            appliedDateTime = LocalDateTime.now(),
            keyWord = null,
            orderBy = ProductOrderType.RECENT
        )
    }
}