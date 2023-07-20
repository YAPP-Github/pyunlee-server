package com.yapp.cvs.api.comment

import com.yapp.cvs.api.comment.dto.ProductCommentContentDTO
import com.yapp.cvs.api.comment.dto.ProductCommentDTO
import com.yapp.cvs.api.comment.dto.ProductCommentSearchDTO
import com.yapp.cvs.api.common.dto.OffsetPageDTO
import com.yapp.cvs.domain.comment.application.ProductCommentProcessor
import com.yapp.cvs.domain.comment.application.ProductCommentRatingProcessor
import com.yapp.cvs.domain.comment.vo.ProductCommentRequestVO
import com.yapp.cvs.domain.like.application.ProductRatingProcessor
import com.yapp.cvs.domain.like.vo.ProductLikeRequestVO
import com.yapp.cvs.domain.member.entity.Member
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "상품 평가 (코멘트)")
@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = "X-ACCESS-TOKEN")
class ProductCommentController(
    private val productCommentProcessor: ProductCommentProcessor,
    private val productRatingProcessor: ProductRatingProcessor,
    private val productCommentRatingProcessor: ProductCommentRatingProcessor
) {
    @GetMapping("/{productId}/comment")
    @Operation(summary = "해당 상품에 작성된 평가 코멘트를 조건만큼 가져옵니다.")
    fun getProductCommentList(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long,
        @ParameterObject productCommentSearchDTO: ProductCommentSearchDTO
    ): OffsetPageDTO<ProductCommentDTO> {
        val result = productCommentProcessor.getCommentDetailList(productId, member, productCommentSearchDTO.toVO())
        return OffsetPageDTO(result.lastId, result.content.map { ProductCommentDTO.from(it) })
    }

    @PostMapping("/{productId}/comment/write")
    @Operation(summary = "상품에 대한 평가 코멘트를 작성합니다.")
    fun writeComment(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long,
        @RequestBody productCommentContentDTO: ProductCommentContentDTO
    ) {
        val requestVO = ProductCommentRequestVO(productId, member.memberId!!)
        productCommentProcessor.createComment(requestVO, productCommentContentDTO.content)
    }

    @PostMapping("/{productId}/comment/edit")
    @Operation(summary = "상품에 대한 평가 코멘트를 수정합니다.")
    fun updateComment(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long,
        @RequestBody productCommentContentDTO: ProductCommentContentDTO
    ) {
        val requestVO = ProductCommentRequestVO(productId, member.memberId!!)
        productCommentProcessor.updateComment(requestVO, productCommentContentDTO.content)
    }

    @PostMapping("/{productId}/comment/delete")
    @Operation(summary = "상품에 대한 평가 코멘트를 삭제합니다.")
    fun deleteComment(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ) {
        productRatingProcessor.cancelEvaluation(ProductLikeRequestVO(productId = productId, memberId = member.memberId!!))
    }

    @PostMapping("/comment/{commentId}/like")
    @Operation(summary = "해당 코멘트에 좋아요를 설정합니다.")
    fun likeComment(
        @Parameter(hidden = true) member: Member,
        @PathVariable commentId: Long
    ) {
        productCommentRatingProcessor.likeComment(member.memberId!!, commentId)
    }

    @PostMapping("/comment/{commentId}/cancel")
    @Operation(summary = "해당 코멘트 좋아요를 취소합니다.")
    fun cancelCommentLike(
        @Parameter(hidden = true) member: Member,
        @PathVariable commentId: Long
    ) {
        productCommentRatingProcessor.cancelLikeComment(member.memberId!!, commentId)
    }
}