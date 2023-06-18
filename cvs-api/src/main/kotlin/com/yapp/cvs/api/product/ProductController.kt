package com.yapp.cvs.api.product

import com.yapp.cvs.api.product.dto.ProductThumbnailResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getProducts(): ProductThumbnailResponse {
        return productService.getDto()
    }

    @GetMapping("/error")
    fun throwsError(): String {
        throw Exception("내부 서버 에러")
    }
}
