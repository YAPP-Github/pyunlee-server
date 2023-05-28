package com.yapp.cvs.job.crawl

import com.yapp.cvs.domains.product.entity.ProductCategory

interface CollectorInstruction {
    fun collect(category: ProductCategory): List<ProductCollectorDto>

    fun expandAllProductPage()
}
