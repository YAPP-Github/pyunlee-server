package com.yapp.cvs.domains.product

import com.yapp.cvs.domains.product.entity.Product
import com.yapp.cvs.domains.product.model.vo.ProductCollectionVo
import com.yapp.cvs.domains.product.repository.ProductRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(
    val productRepository: ProductRepository,
) {
    fun save(productCollectionVo: ProductCollectionVo): Product {
        val existingProduct = productRepository.findByCodeOrName(productCollectionVo.code, productCollectionVo.name)
        return existingProduct?.apply {
            name = productCollectionVo.name
            price = productCollectionVo.price
            imageUrl = productCollectionVo.imageUrl
            productEventType = productCollectionVo.productEventType
            isNew = productCollectionVo.isNew
            category = productCollectionVo.category
        }
            ?: // 새로운 엔티티 생성
            return productRepository.save(
                Product(
                    name = productCollectionVo.name,
                    price = productCollectionVo.price,
                    imageUrl = productCollectionVo.imageUrl,
                    productEventType = productCollectionVo.productEventType,
                    isNew = productCollectionVo.isNew,
                    category = productCollectionVo.category,
                    code = productCollectionVo.code,
                ),
            )
    }
}
