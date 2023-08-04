package com.yapp.cvs.domain.product.entity

import javax.persistence.*

@Entity
@Table(name = "product_scores")
class ProductScore(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productScoreId: Long? = null,
    val productId: Long,
    var score: Long
) {
}