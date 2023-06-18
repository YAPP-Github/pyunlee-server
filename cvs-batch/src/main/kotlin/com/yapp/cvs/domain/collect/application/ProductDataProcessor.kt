package com.yapp.cvs.domain.collect.application

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.product.entity.PbProductMapping
import com.yapp.cvs.domain.product.entity.ProductPromotionType
import com.yapp.cvs.domain.product.entity.ProductRetailerMapping
import com.yapp.cvs.domain.product.entity.PromotionProduct
import com.yapp.cvs.domain.product.repository.PbProductMappingRepository
import com.yapp.cvs.domain.product.repository.ProductRepository
import com.yapp.cvs.domain.product.repository.ProductRetailerMappingRepository
import com.yapp.cvs.domain.product.repository.PromotionProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductDataProcessor(
    private val productRepository: ProductRepository,
    private val productRetailerMappingRepository: ProductRetailerMappingRepository,
    private val pbProductMappingRepository: PbProductMappingRepository,
    private val promotionProductRepository: PromotionProductRepository
) {
    @Transactional
    fun saveProduct(productRawDataVO: ProductRawDataVO) {
        if (productRepository.findByBarcode(productRawDataVO.barcode) == null) {
            val productEntity = productRepository.save(productRawDataVO.to())
            productRetailerMappingRepository.save(
                ProductRetailerMapping.of(
                    productEntity,
                    productRawDataVO.retailerType
                )
            )

            if (productRawDataVO.isPbProduct) {
                pbProductMappingRepository.save(PbProductMapping.of(productEntity))
            }
        }
    }

    @Transactional
    fun savePromotion(productRawDataVO: ProductRawDataVO, productPromotionType: ProductPromotionType) {
        val product = productRepository.findByBarcode(productRawDataVO.barcode)
        if (product == null) {
            log.info("해당 상품에 대한 정보가 없습니다. barcode: ${productRawDataVO.barcode} name: ${productRawDataVO.name}")
        } else {
            promotionProductRepository.save(
                PromotionProduct(
                    productId = product.productId!!,
                    promotionType = productPromotionType,
                    retailerType = productRawDataVO.retailerType,
                )
            )
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
