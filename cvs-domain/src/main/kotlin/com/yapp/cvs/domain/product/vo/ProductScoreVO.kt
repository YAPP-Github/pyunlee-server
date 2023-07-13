package com.yapp.cvs.domain.product.vo

import com.yapp.cvs.domain.like.entity.ProductLikeSummary

class ProductScoreVO(
    val totalCount: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val likeRatio: Int,
    val dislikeRatio: Int
) {
    companion object {
        fun like(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            productLikeSummary.like()
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }

        fun dislike(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            productLikeSummary.dislike()
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }

        fun cancelLike(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            productLikeSummary.cancelLike()
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }

        fun cancelDislike(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            productLikeSummary.cancelDislike()
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }

        fun from(productLikeSummary: ProductLikeSummary): ProductScoreVO {
            return ProductScoreVO(
                totalCount = productLikeSummary.totalCount,
                likeCount = productLikeSummary.likeCount,
                dislikeCount = productLikeSummary.getDislikeCount(),
                likeRatio = productLikeSummary.getLikeRatio(),
                dislikeRatio = productLikeSummary.getDislikeRatio()
            )
        }
    }
}