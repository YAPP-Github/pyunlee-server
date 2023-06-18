package com.yapp.cvs.api.product

import com.yapp.cvs.api.product.dto.ProductThumbnailResponse
import com.yapp.cvs.domain.product.repository.ProductRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getDto(): ProductThumbnailResponse {
        return ProductThumbnailResponse("편의점", 2000)
    }
}
