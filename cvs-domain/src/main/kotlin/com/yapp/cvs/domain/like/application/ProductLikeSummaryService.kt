package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.ProductLikeSummary
import com.yapp.cvs.domain.like.repository.ProductLikeSummaryRepository
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service


@Service
class ProductLikeSummaryService(
    private val productLikeSummaryRepository: ProductLikeSummaryRepository
) {
    fun getProductLikeSummary(productId: Long): ProductLikeSummaryVO {
        return ProductLikeSummaryVO.from(productLikeSummaryRepository.findByProductId(productId))
    }

    fun findProductLikeSummary(productId: Long): ProductLikeSummary {
        return productLikeSummaryRepository.findByProductId(productId)
    }

    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun likeProductRatingSummary(productId: Long, lastRatingType: ProductLikeType?){
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)

        if(lastRatingType != null && lastRatingType.isDislike()) {
            productLikeSummary.cancelDislike()
        }

        productLikeSummary.like()
        productLikeSummaryRepository.save(productLikeSummary)
    }

    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun dislikeProductRatingSummary(productId: Long, lastRatingType: ProductLikeType?){
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)

        if(lastRatingType != null && lastRatingType.isLike()) {
            productLikeSummary.cancelLike()
        }

        productLikeSummary.dislike()
        productLikeSummaryRepository.save(productLikeSummary)
    }

    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun cancelProductRatingSummary(productId: Long, lastRatingType: ProductLikeType){
        if (lastRatingType.isLike()) {
            cancelLikeProductRating(productId)
        } else if(lastRatingType.isDislike()) {
            cancelDislikeProductRating(productId)
        }
    }

    private fun cancelLikeProductRating(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.cancelLike()
        productLikeSummaryRepository.save(productLikeSummary)
    }

    private fun cancelDislikeProductRating(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.cancelDislike()
        productLikeSummaryRepository.save(productLikeSummary)
    }
}
