package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.ProductLikeType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class ProductRatingSummaryAsyncService(
    private val productLikeSummaryService: ProductLikeSummaryService
) {
    @Async(value = "productLikeSummaryTaskExecutor")
    fun likeProductRatingSummary(productId: Long, lastRatingType: ProductLikeType?){
        productLikeSummaryService.likeProductRatingSummary(productId, lastRatingType)
    }

    @Async(value = "productLikeSummaryTaskExecutor")
    fun dislikeProductRatingSummary(productId: Long, lastRatingType: ProductLikeType?){
        productLikeSummaryService.dislikeProductRatingSummary(productId, lastRatingType)
    }
    @Async(value = "productLikeSummaryTaskExecutor")
    fun cancelProductRatingSummary(productId: Long, lastRatingType: ProductLikeType){
        productLikeSummaryService.cancelProductRatingSummary(productId, lastRatingType)
    }
}