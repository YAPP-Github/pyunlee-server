package com.yapp.cvs.job.crawl

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ProductCollectorService {
    fun <T : Enum<T>> getCollection(category: T): List<ProductCollectorDto>
    fun saveAll(productCollections: List<ProductCollectorDto>)
    fun validateAll(productCollections: List<ProductCollectorDto>) {
        val invalidItems = productCollections.filter {
            try {
                it.validate()
                false
            } catch (e: Exception) {
                log.error("Invalid item", e)
                true
            }
        }
        log.info("Number of invalid items: ${invalidItems.size}")
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ProductCollectorService::class.java)
    }
}