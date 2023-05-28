package com.yapp.cvs.job.crawl

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ProductCollectorValidator {
    fun validateAll(discountedItems: List<ProductCollectorDto>) {
        val invalidItems = discountedItems.filter {
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
        val log: Logger = LoggerFactory.getLogger(ProductCollectorValidator::class.java)
    }
}
