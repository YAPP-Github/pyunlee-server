package com.yapp.cvs.domains.user

import com.yapp.cvs.common.entity.BaseTimeEntity
import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column
        val name: String,

        @Column
        val phone: String,
) : BaseTimeEntity()
