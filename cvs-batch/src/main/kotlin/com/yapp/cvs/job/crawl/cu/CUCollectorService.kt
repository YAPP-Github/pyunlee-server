package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.product.ProductService
import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.ProductCollectorService.Companion.log
import com.yapp.cvs.job.crawl.WebdriverHandler

class CUCollectorService(
    private val productService: ProductService,
    private val webdriverInstruction: WebdriverHandler,
) : ProductCollectorService {

    override fun <T : Enum<T>> getCollection(category: T): List<ProductRawDataVO> {
        val cuCategory = category as ProductCategory
        log.info("Target Category: ${(cuCategory).kr}")
        val driver = webdriverInstruction.initializeWebdriver()
        try {
            webdriverInstruction.setCategoryTo(category = cuCategory, driver = driver)
            webdriverInstruction.expandAllProductPage(driver = driver)
            return webdriverInstruction.collect(category = cuCategory, driver = driver)
        } finally {
            driver.quit()
        }
    }

    override fun saveAll(productCollections: List<ProductRawDataVO>) {
    }
}
