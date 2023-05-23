package com.yapp.cvs.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
open class BaseTimeEntity(
        @CreatedDate
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @LastModifiedDate
        val updatedAt: LocalDateTime = LocalDateTime.now(),
)
