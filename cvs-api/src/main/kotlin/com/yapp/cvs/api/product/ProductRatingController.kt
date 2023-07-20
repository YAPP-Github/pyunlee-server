package com.yapp.cvs.api.product

import com.yapp.cvs.api.product.dto.ProductLikeHistoryDTO
import com.yapp.cvs.api.product.dto.ProductLikeSummaryDTO
import com.yapp.cvs.api.product.dto.ProductScoreDTO
import com.yapp.cvs.configuration.swagger.SwaggerConfig
import com.yapp.cvs.configuration.swagger.SwaggerConfig.Companion.SWAGGER_AUTH_KEY
import com.yapp.cvs.domain.like.application.ProductRatingProcessor
import com.yapp.cvs.domain.like.vo.ProductLikeRequestVO
import com.yapp.cvs.domain.member.entity.Member
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "평가 (좋아요)")
@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class ProductRatingController(
        val productRatingProcessor: ProductRatingProcessor
) {
    @GetMapping("/{productId}/rate/history")
    @Operation(summary = "하나의 상품에 대한 내 최근 평가 내역을 가져옵니다.")
    fun getProductLike(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ): ProductLikeHistoryDTO {
        return ProductLikeHistoryDTO.from(productRatingProcessor.findLatestRate(productId, member.memberId!!))
    }

    @GetMapping("/{productId}/rate")
    @Operation(summary = "하나의 상품의 평가 정보를 가져옵니다")
    fun getProductLikeSummary(@PathVariable productId: Long): ProductLikeSummaryDTO {
        return ProductLikeSummaryDTO.from(productRatingProcessor.findProductLikeSummary(productId))
    }

    @PostMapping("/{productId}/rate/like")
    @Operation(summary = "\"또 먹을거에요\" 평가를 남깁니다.")
    fun postProductLike(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ): ProductScoreDTO {
        return ProductScoreDTO.from(
            productRatingProcessor.likeProduct(ProductLikeRequestVO(productId = productId, member.memberId!!))
        )
    }

    @PostMapping("/{productId}/rate/dislike")
    @Operation(summary = "\"또 안 사먹을래요\" 평가를 남깁니다.")
    fun postProductDislike(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ): ProductScoreDTO {
        return ProductScoreDTO.from(
            productRatingProcessor.dislikeProduct(ProductLikeRequestVO(productId = productId, member.memberId!!))
        )
    }

    @PostMapping("/{productId}/rate/cancel")
    @Operation(summary = "평가를 취소합니다.")
    fun postProductLikeCancel(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ): ProductScoreDTO {
        return ProductScoreDTO.from(
            productRatingProcessor.cancelEvaluation(ProductLikeRequestVO(productId = productId, member.memberId!!))
        )
    }
}
