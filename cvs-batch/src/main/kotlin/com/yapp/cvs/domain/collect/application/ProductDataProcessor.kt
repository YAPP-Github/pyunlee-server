package com.yapp.cvs.domain.collect.application

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.product.entity.PbProductMapping
import com.yapp.cvs.domains.product.entity.ProductRetailerMapping
import com.yapp.cvs.domains.product.repository.PbProductMappingRepository
import com.yapp.cvs.domains.product.repository.ProductEntityRepository
import com.yapp.cvs.domains.product.repository.ProductRetailerMappingRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductDataProcessor(
    private val productEntityRepository: ProductEntityRepository,
    private val productRetailerMappingRepository: ProductRetailerMappingRepository,
    private val pbProductMappingRepository: PbProductMappingRepository
) {
    @Transactional
    fun saveProduct(productRawDataVO: ProductRawDataVO) {
        if (productEntityRepository.findByProductNameAndBrandName(productRawDataVO.name, productRawDataVO.brandName) == null) {
            val productEntity = productEntityRepository.save(productRawDataVO.to())
            productRetailerMappingRepository.save(ProductRetailerMapping.of(productEntity, productRawDataVO.retailerType))

            if (productRawDataVO.isPbProduct) {
                pbProductMappingRepository.save(PbProductMapping.of(productEntity))
            }
        }
    }
}
