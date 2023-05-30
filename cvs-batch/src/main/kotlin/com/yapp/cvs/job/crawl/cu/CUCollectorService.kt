package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.ProductService
import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductCategory.UNKNOWN
import com.yapp.cvs.domains.product.model.vo.ProductCollectionVo
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.ProductCollectorService.Companion.log
import com.yapp.cvs.job.crawl.WebdriverHandler

class CUCollectorService(
    private val productService: ProductService,
    private val webdriverInstruction: WebdriverHandler,
) : ProductCollectorService {

    override fun getCollection(category: ProductCategory?): List<ProductCollectorDto> {
        val driver = webdriverInstruction.initializeWebdriver()
        try {
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")
            log.info("Target Category: ${(category ?: UNKNOWN).kr}")
            webdriverInstruction.setCategoryTo(category = category, driver = driver)
            webdriverInstruction.expandAllProductPage(driver = driver)
            return webdriverInstruction.collect(category = category, driver = driver)
        } finally {
            driver.quit()
        }
    }

    override fun saveAll(productCollections: List<ProductCollectorDto>) {
        productCollections.forEach {
            productService.save(
                ProductCollectionVo(
                    name = it.name ?: "",
                    price = it.price?.toInt() ?: 0,
                    imageUrl = it.imageUrl,
                    productEventType = it.productEventType,
                    isNew = it.isNew ?: false,
                    code = it.code ?: "",
                    category = it.category,
                ),
            )
            println(it.toString())
        }
    }
}
