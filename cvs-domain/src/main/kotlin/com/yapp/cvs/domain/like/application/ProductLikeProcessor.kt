package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.comment.application.ProductCommentService
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.vo.ProductLikeHistoryVO
import com.yapp.cvs.domain.like.vo.ProductLikeRequestVO
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import com.yapp.cvs.domain.product.vo.ProductScoreVO
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.exception.NotFoundSourceException
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductLikeProcessor(
    private val productLikeHistoryService: ProductLikeHistoryService,
    private val memberProductLikeMappingService: MemberProductLikeMappingService,
    private val productLikeSummaryService: ProductLikeSummaryService,
    private val productCommentService: ProductCommentService
) {
    fun findLatestRate(productId: Long, memberId: Long): ProductLikeHistoryVO {
        val productLikeHistory = productLikeHistoryService.findLatest(productId, memberId)
            ?: ProductLikeHistory.none(MemberProductMappingKey(productId = productId, memberId = memberId))
        return ProductLikeHistoryVO.from(productLikeHistory)
    }

    fun findProductLikeSummary(productId: Long): ProductLikeSummaryVO {
        return productLikeSummaryService.getProductLikeSummary(productId)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productLikeRequestVO"])
    fun likeProduct(productLikeRequestVO: ProductLikeRequestVO): ProductScoreVO {
        val lastMapping = memberProductLikeMappingService
            .findByMemberProductLike(productLikeRequestVO.memberProductMappingKey)
            ?.also { if(it.likeType.isLike()) throw BadRequestException("이미 좋아요 한 상품입니다.") }

        val lastRatingType = lastMapping?.likeType

        val memberProductLikeMapping = lastMapping
            ?.apply { likeType = ProductLikeType.LIKE }
            ?: MemberProductLikeMapping.like(productLikeRequestVO.memberProductMappingKey)

        productLikeHistoryService.like(productLikeRequestVO.memberProductMappingKey)
        memberProductLikeMappingService.saveMemberProductLikeMapping(memberProductLikeMapping)
        productCommentService.activate(productLikeRequestVO.memberProductMappingKey)

        productLikeSummaryService.likeProductLikeSummary(productLikeRequestVO.productId, lastRatingType)

        return ProductScoreVO.like(productLikeSummaryService.findProductLikeSummary(productLikeRequestVO.productId))
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productLikeRequestVO"])
    fun dislikeProduct(productLikeRequestVO: ProductLikeRequestVO): ProductScoreVO {
        val lastMapping = memberProductLikeMappingService
            .findByMemberProductLike(productLikeRequestVO.memberProductMappingKey)
            ?.also { if(it.likeType.isDislike()) throw BadRequestException("이미 싫어요 한 상품입니다.") }

        val lastRatingType = lastMapping?.likeType

        val memberProductLikeMapping = lastMapping
            ?.apply { likeType = ProductLikeType.DISLIKE }
            ?: MemberProductLikeMapping.dislike(productLikeRequestVO.memberProductMappingKey)

        productLikeHistoryService.dislike(productLikeRequestVO.memberProductMappingKey)
        memberProductLikeMappingService.saveMemberProductLikeMapping(memberProductLikeMapping)
        productCommentService.activate(productLikeRequestVO.memberProductMappingKey)

        productLikeSummaryService.dislikeProductLikeSummary(productLikeRequestVO.productId, lastRatingType)

        return ProductScoreVO.dislike(productLikeSummaryService.findProductLikeSummary(productLikeRequestVO.productId))
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productLikeRequestVO"])
    fun cancelEvaluation(productLikeRequestVO: ProductLikeRequestVO): ProductScoreVO {
        val lastMapping = memberProductLikeMappingService
            .findByMemberProductLike(productLikeRequestVO.memberProductMappingKey)
            ?: throw NotFoundSourceException("평가 정보가 없습니다.")

        val lastLikeType = lastMapping.likeType

        productLikeHistoryService.cancel(productLikeRequestVO.memberProductMappingKey)

        memberProductLikeMappingService.saveMemberProductLikeMapping(lastMapping.apply { likeType = ProductLikeType.NONE })
        productCommentService.inactivateIfExist(productLikeRequestVO.memberProductMappingKey)

        val productLikeSummary = productLikeSummaryService.findProductLikeSummary(productLikeRequestVO.productId)

        return if (lastLikeType.isLike()) {
            productLikeSummaryService.cancelLikeProductRating(productLikeRequestVO.productId)
            ProductScoreVO.cancelLike(productLikeSummary)
        } else if(lastLikeType.isDislike()) {
            productLikeSummaryService.cancelDislikeProductLikeSummary(productLikeRequestVO.productId)
            ProductScoreVO.cancelDislike(productLikeSummary)
        } else {
            throw BadRequestException("잘못된 요청")
        }
    }
}
