package com.yapp.cvs.domain.product.application

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.product.vo.ProductDetailVO
import com.yapp.cvs.domain.product.vo.ProductSearchVO
import com.yapp.cvs.domain.product.vo.ProductVO
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductProcessor(
    private val productService: ProductService,
    private val productPromotionService: ProductPromotionService,
) {
    fun getProductDetail(productId: Long): ProductDetailVO {
        val productPbVO = productService.findProductPbInfo(productId)
        val productPromotionList = productPromotionService.findProductPromotionList(productId)

        productService.increaseProductViewCount(productId)

        return ProductDetailVO.of(productPbVO, productPromotionList)
    }

    fun searchProductPageList(productSearchVO: ProductSearchVO): OffsetPageVO<ProductVO> {
        val productList = productService.searchProductList(productSearchVO)
        return OffsetPageVO(
            productList.lastOrNull()?.productId,
            productList.map { ProductVO.from(it) }
        )
    }
}