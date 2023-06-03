package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.ProductService
import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.model.vo.ProductCollectionVo
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.ProductCollectorService.Companion.log
import com.yapp.cvs.job.crawl.WebdriverHandler

class CUCollectorService(
    private val productService: ProductService,
    private val webdriverInstruction: WebdriverHandler,
) : ProductCollectorService {

    override fun <T : Enum<T>> getCollection(category: T): List<ProductCollectorDto> {
        val cuCategory = category as ProductCategory
        log.info("Target Category: ${(cuCategory).kr}")
        val driver = webdriverInstruction.initializeWebdriver()
        try {
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")
            webdriverInstruction.setCategoryTo(category = cuCategory, driver = driver)
            webdriverInstruction.expandAllProductPage(driver = driver)
            return webdriverInstruction.collect(category = cuCategory, driver = driver)
        } finally {
            driver.quit()
        }
    }

    override fun saveAll(productCollections: List<ProductCollectorDto>) {
        productCollections.forEach {
            productService.save(
                ProductCollectionVo(
                    name = it.name ?: "",
                    price = it.price ?: 0,
                    imageUrl = it.imageUrl,
                    productEventType = it.productEventType,
                    isNew = it.isNew ?: false,
                    code = it.code ?: "",
                    category = it.category,
                ),
            )
        }
    }
}