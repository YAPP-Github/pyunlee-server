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
    val id: Long = 0L,
    var name: String,
    var price: Int?,
    var imageUrl: String?,
    @Enumerated(EnumType.STRING)
    var productEventType: ProductEventType?,
    var isNew: Boolean?,
    @Enumerated(EnumType.STRING)
    var category: ProductCategory?,
    val code: String,
) : BaseTimeEntity()
