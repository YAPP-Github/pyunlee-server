package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.extension.containsKeywords

enum class ProductCategoryRule(
    val keywords: List<String>,
    val productCategoryType: ProductCategoryType,
) {
    DRINK(listOf("아메리카노", "라떼", "주스", "라떼", "카페", "녹차", "콜드브루"), ProductCategoryType.DRINK),
    INSTANT_MEAL(listOf("라면", "큰컵"), ProductCategoryType.INSTANT_MEAL),
    DIARY_KEYWORD(listOf("우유"), ProductCategoryType.DIARY)
    ;

    companion object {
        fun parse(name: String): ProductCategoryType {
            val ruleList = ProductCategoryRule.values()
            ruleList.forEach { rule ->
                if (name.containsKeywords(rule.keywords)) {
                    return rule.productCategoryType
                }
            }
            return ProductCategoryType.UNKNOWN
        }
    }
}
