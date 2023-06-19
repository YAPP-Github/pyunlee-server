package com.yapp.cvs.api.product

import com.yapp.cvs.api.product.dto.ProductDetailDTO
import com.yapp.cvs.domain.product.application.ProductProcessor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productProcessor: ProductProcessor
) {
    @GetMapping("/{productId}/detail")
    fun getProductDetail(@PathVariable productId: Long): ProductDetailDTO {
        return ProductDetailDTO.from(productProcessor.getProductDetail(productId))
    }
}
