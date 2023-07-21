package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.base.vo.OffsetPageVO
import com.yapp.cvs.domain.comment.vo.ProductCommentRequestVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.comment.vo.ProductCommentVO
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommentProcessor(
    private val productCommentService: ProductCommentService
) {

    fun getCommentDetailList(productId: Long, member: Member, productCommentSearchVO: ProductCommentSearchVO): OffsetPageVO<ProductCommentVO> {
        val result = productCommentService.getProductCommentList(productId, member, productCommentSearchVO)
        return OffsetPageVO(result.lastOrNull()?.productCommentId, result)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productCommentRequestVO"])
    fun createComment(productCommentRequestVO: ProductCommentRequestVO, content: String) {
        productCommentService.write(productCommentRequestVO.memberProductMappingKey, content)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productCommentRequestVO"])
    fun updateComment(productCommentRequestVO: ProductCommentRequestVO, content: String) {
        productCommentService.update(productCommentRequestVO.memberProductMappingKey, content)
    }

    @Async(value = "productCommentLikeSummaryTaskExecutor")
    fun deleteAllCommentByMember(memberId: Long) {
        val commentList = productCommentService.findAllByMember(memberId)
        commentList.forEach {
            productCommentService.inactivate(MemberProductMappingKey(it.productId, it.memberId))
        }
    }
}
