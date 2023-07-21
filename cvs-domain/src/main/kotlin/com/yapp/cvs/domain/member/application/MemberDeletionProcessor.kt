package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.comment.application.ProductCommentProcessor
import com.yapp.cvs.domain.comment.application.ProductCommentRatingProcessor
import com.yapp.cvs.domain.like.application.ProductRatingProcessor
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberDeletionProcessor(
    private val memberRepository: MemberRepository,
    private val productCommentProcessor: ProductCommentProcessor,
    private val productRatingProcessor: ProductRatingProcessor,
    private val productCommentRatingProcessor: ProductCommentRatingProcessor
) {
    fun delete(member: Member) {
        member.deactivate()
        memberRepository.save(member)
        productRatingProcessor.cancelAllRatingByMember(member.memberId!!)
        productCommentProcessor.deleteAllCommentByMember(member.memberId!!)
        productCommentRatingProcessor.cancelAllRatingByMember(member.memberId!!)
    }
}