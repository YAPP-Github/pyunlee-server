package com.yapp.cvs.job.crawl

interface ProductCollectorService : ProductCollectorValidator {
    fun getCollection(): List<ProductCollectorDto>

    fun saveAll(productCollections: List<ProductCollectorDto>)
}
