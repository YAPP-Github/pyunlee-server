package com.yapp.cvs.domains.enums

import com.yapp.cvs.domains.extension.containsKeywords

enum class ProductCategoryType(
    val displayName: String
) {
    DOSIRAK("도시락"),
    SANDWICH("샌드위치/햄버거"),
    GIMBAP("주먹밥/김밥"),
    FRIES("튀김류"),
    BAKERY("베이커리"),
    INSTANT_COFFEE("즉석커피"),
    BISCUIT("스낵/비스켓"),
    DESSERT("빵/디저트"),
    CANDY("껌/초콜릿/캔디"),
    ICE_CREAM("아이스크림"),
    INSTANT_MEAL("가공식사"),
    MUNCHIES("안주류"),
    INGREDIENT("식재료"),
    DRINK("음료"),
    ICED_DRINK("아이스드링크"),
    DIARY("유제품"),
    UNKNOWN("알수없음")
    ;

    companion object {
        fun parse(name: String): ProductCategoryType {
            val ruleList = ProductCategoryRule.values()
            ruleList.forEach { rule ->
                if (name.containsKeywords(rule.keywords)) {
                    return rule.productCategoryType
                }
            }
            return UNKNOWN
        }
    }

    enum class ProductCategoryRule(
        val keywords: List<String>,
        val productCategoryType: ProductCategoryType,
    ) {
        DRINK(listOf("아메리카노", "라떼", "주스", "라떼", "카페", "녹차", "콜드브루"), ProductCategoryType.DRINK),
        INSTANT_MEAL(listOf("라면", "큰컵"), ProductCategoryType.INSTANT_MEAL),
        DIARY_KEYWORD(listOf("우유"), ProductCategoryType.DIARY)
    }
}
