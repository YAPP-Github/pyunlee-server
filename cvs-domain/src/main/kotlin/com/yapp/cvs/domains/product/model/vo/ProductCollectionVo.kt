package com.yapp.cvs.domains.product.model.vo

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductEventType

data class ProductCollectionVo(
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val productEventType: ProductEventType,
    val isNew: Boolean,
    val category: ProductCategory,
    val code: String,
)
