package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/***
 * 크롤링한 데이터를 DB에 담기 위한 Entity 입니다 (추후 수정 가능성 O)
 */
@Entity
@Table(name = "tbl_product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var name: String,
    var price: Int,
    val imageUrl: String,
    @Enumerated(EnumType.STRING)
    val productEventType: ProductEventType,
    val isNew: Boolean,
    @Enumerated(EnumType.STRING)
    val category: ProductCategory,
    val code: String,
    val referenceUrl: String? = null,
) : BaseTimeEntity()
