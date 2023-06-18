package com.yapp.cvs.api.product.dto

data class ProductThumbnailResponse(
    val name: String,
    val price: Long,
    val totalRecommendScore: Long = 0L,
    val totalReview: Long = 0L,
    val thumbnailImageURL: String = "",
    val hasEvent: Boolean = false
)
