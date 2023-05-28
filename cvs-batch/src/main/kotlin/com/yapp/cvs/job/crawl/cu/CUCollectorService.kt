package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.ProductService
import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.model.vo.ProductCollectionVo
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.ProductCollectorService.Companion.log
import com.yapp.cvs.job.crawl.instruction.WebdriverInstruction

class CUCollectorService(
    private val productService: ProductService,
    private val webdriverInstruction: WebdriverInstruction,
) : ProductCollectorService {

    override fun getCollection(): List<ProductCollectorDto> {
        val driver = webdriverInstruction.initializeWebdriver()
        try {
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")

            val productItems = mutableListOf<ProductCollectorDto>()
            ProductCategory.values().forEach {
                log.info("Target Category: ${it.kr}")
                webdriverInstruction.setCategoryTo(category = it, driver = driver)
                webdriverInstruction.expandAllProductPage(driver = driver)
                val collection = webdriverInstruction.collect(category = it, driver = driver)
                productItems.addAll(collection)
            }
            return productItems
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
