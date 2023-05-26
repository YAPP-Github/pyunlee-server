package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.common.entity.BaseTimeEntity
import javax.persistence.Entity
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
    var id: Long = 0,
    var name: String,
    var price: Int,

    // todo : 작명 다시 필요
    var onePlusOne: Boolean,
    var twoPlusOne: Boolean,
    var newProduct: Boolean,
) : BaseTimeEntity()
