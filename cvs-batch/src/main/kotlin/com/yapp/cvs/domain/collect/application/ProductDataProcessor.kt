package com.yapp.cvs.domain.collect.application

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.product.entity.PbProductMapping
import com.yapp.cvs.domains.product.entity.ProductPromotionType
import com.yapp.cvs.domains.product.entity.ProductRetailerMapping
import com.yapp.cvs.domains.product.entity.PromotionProduct
import com.yapp.cvs.domains.product.repository.PbProductMappingRepository
import com.yapp.cvs.domains.product.repository.ProductEntityRepository
import com.yapp.cvs.domains.product.repository.ProductRetailerMappingRepository
import com.yapp.cvs.domains.product.repository.PromotionProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductDataProcessor(
    private val productEntityRepository: ProductEntityRepository,
    private val productRetailerMappingRepository: ProductRetailerMappingRepository,
    private val pbProductMappingRepository: PbProductMappingRepository,
    private val promotionProductRepository: PromotionProductRepository
) {
    @Transactional
    fun saveProduct(productRawDataVO: ProductRawDataVO) {
        if (productEntityRepository.findByBarcode(productRawDataVO.barcode) == null) {
            val productEntity = productEntityRepository.save(productRawDataVO.to())
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
        val product = productEntityRepository.findByBarcode(productRawDataVO.barcode)
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
