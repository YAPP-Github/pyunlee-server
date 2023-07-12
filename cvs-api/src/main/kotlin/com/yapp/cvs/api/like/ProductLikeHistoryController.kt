package com.yapp.cvs.api.like

import com.yapp.cvs.api.like.dto.ProductLikeHistoryDTO
import com.yapp.cvs.api.like.dto.ProductLikeSummaryDTO
import com.yapp.cvs.api.product.dto.ProductScoreDTO
import com.yapp.cvs.domain.like.application.ProductLikeProcessor
import com.yapp.cvs.domain.like.entity.ProductLikeHistory
import com.yapp.cvs.domain.like.vo.ProductLikeRequestVO
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import com.yapp.cvs.domain.product.vo.ProductScoreVO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductLikeHistoryController(
        val productLikeProcessor: ProductLikeProcessor
) {
    //TODO: security 에서 memberId 추출 기능 확정
    @GetMapping("/{productId}/rate/history")
    @Operation(summary = "하나의 상품에 대한 내 최근 평가 내역을 가져옵니다.")
    fun getProductLike(@PathVariable productId: Long): ProductLikeHistoryDTO {
        return ProductLikeHistoryDTO.from(productLikeProcessor.findLatestRate(productId, 1L))
    }

    @GetMapping("/{productId}/rate")
    @Operation(summary = "하나의 상품의 평가 정보를 가져옵니다")
    fun getProductLikeSummary(@PathVariable productId: Long): ProductLikeSummaryDTO {
        return ProductLikeSummaryDTO.from(productLikeProcessor.findProductLikeSummary(productId))
    }

    @PostMapping("/{productId}/rate/like")
    @Operation(summary = "\"또 먹을거에요\" 평가를 남깁니다.")
    fun postProductLike(@PathVariable productId: Long): ProductScoreDTO {
        return ProductScoreDTO.from(
            productLikeProcessor.likeProduct(ProductLikeRequestVO(productId = productId, 1L))
        )
    }

    @PostMapping("/{productId}/rate/dislike")
    @Operation(summary = "\"또 안 사먹을래요\" 평가를 남깁니다.")
    fun postProductDislike(@PathVariable productId: Long): ProductScoreDTO {
        return ProductScoreDTO.from(
            productLikeProcessor.dislikeProduct(ProductLikeRequestVO(productId = productId, 1L))
        )
    }

    @PostMapping("/{productId}/rate/cancel")
    @Operation(summary = "평가를 취소합니다.")
    fun postProductLikeCancel(@PathVariable productId: Long): ProductScoreDTO {
        return ProductScoreDTO.from(
            productLikeProcessor.cancelEvaluation(ProductLikeRequestVO(productId = productId, 1L))
        )
    }
}
