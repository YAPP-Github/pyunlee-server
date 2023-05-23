package com.yapp.cvs.domains.user

import com.yapp.cvs.common.entity.BaseTimeEntity
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @NotNull
        @Column
        val name: String,

        @NotNull
        @Column
        val phone: String,
) : BaseTimeEntity()
