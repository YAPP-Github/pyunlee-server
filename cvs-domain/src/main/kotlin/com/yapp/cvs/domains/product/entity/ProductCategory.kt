package com.yapp.cvs.domains.product.entity

import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.BEVERAGE
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.COOK
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.FOOD
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.SIMPLE_MEAL
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.SNACK

@Suppress("SpellCheckingInspection")
enum class ProductCategory(
        val kr: String,
        val superCategory: ProductSuperCategory) {
    DOSIRAK("도시락", SIMPLE_MEAL),
    SANDWICH("샌드위치/햄버거", SIMPLE_MEAL),
    GIMBAP("주먹밥/김밥", SIMPLE_MEAL),

    FRIES("튀김류", COOK),
    BAKERY("베이커리", COOK),
    INSTANT_COFFEE("즉석커피", COOK),

    BISCUIT("스낵/비스켓", SNACK),
    DESSERT("빵/디저트", SNACK),
    CANDY("껌/초콜릿/캔디", SNACK),

    ICE_CREAM("아이스크림", ProductSuperCategory.ICE_CREAM),

    INSTANT_MEAL("가공식사", FOOD),
    MUNCHIES("안주류", FOOD),
    INGREDIENT("식재료", FOOD),

    DRINK("음료", BEVERAGE),
    ICED_DRINK("아이스드링크", BEVERAGE),
    DIARY("유제품", BEVERAGE),
    ;

    enum class ProductSuperCategory(val kr: String) {
        SIMPLE_MEAL("간편식사"),
        COOK("즉석조리"),
        SNACK("과자류"),
        ICE_CREAM("아이스크림"),
        FOOD("식품"),
        BEVERAGE("음료"),
    }
}
