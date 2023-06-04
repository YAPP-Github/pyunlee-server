package com.yapp.cvs.job.crawl.gs25.handler

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.extension.containsKeywords
import com.yapp.cvs.job.crawl.gs25.handler.GS25ProductCollectSupport.CategoryURL.DISCOUNT
import com.yapp.cvs.job.crawl.gs25.handler.GS25ProductCollectSupport.CategoryURL.FRESH_FOOD
import com.yapp.cvs.job.crawl.gs25.handler.GS25ProductCollectSupport.CategoryURL.PB

enum class GS25ProductCollectSupport(
    val url: String,
    val productCategoryType: ProductCategoryType? = null,
    val tabId: String? = null,
    val isPbProduct: Boolean = false,
) {
    DOSIRAK_URL(
        FRESH_FOOD.URL,
        ProductCategoryType.DOSIRAK,
        "productLunch",
        true,
    ),
    GIMBAP_URL(
        FRESH_FOOD.URL,
        ProductCategoryType.GIMBAP,
        "productRice",
        true,
    ),
    SANDWICH_URL(
        FRESH_FOOD.URL,
        ProductCategoryType.SANDWICH,
        "productBurger",
        true,
    ),
    SIMPLE_MEAL_URL(
        FRESH_FOOD.URL,
        ProductCategoryType.INSTANT_MEAL,
        "productSnack",
        true,
    ),

    DRINK_URL(
        PB.URL,
        ProductCategoryType.DRINK,
        "productDrink",
        true,
    ),

    DIARY_URL(
        PB.URL,
        ProductCategoryType.DIARY,
        "productMilk",
        true,
    ),
    BISCUIT_URL(
        PB.URL,
        ProductCategoryType.BISCUIT,
        "productCookie",
        true,
    ),
    INSTANT_MEAL_URL(
        PB.URL,
        ProductCategoryType.INSTANT_MEAL,
        "productRamen",
        true,
    ),

    DISCOUNT_URL(
        DISCOUNT.URL,
        isPbProduct = false,
    ),

    ;

    fun parseProductCategoryType(name: String): ProductCategoryType {
        if (this.productCategoryType == null) {
            val ruleList = ProductCategoryRule.values()
            ruleList.forEach { rule ->
                if (name.containsKeywords(rule.keywords)) {
                    return rule.productCategoryType
                }
            }
            return ProductCategoryType.UNKNOWN
        }

        return this.productCategoryType
    }

    enum class ProductCategoryRule(
        val keywords: List<String>,
        val productCategoryType: ProductCategoryType,
    ) {
        DRINK(listOf("아메리카노", "라떼", "주스", "라떼", "카페", "녹차", "콜드브루"), ProductCategoryType.DRINK),
        INSTANT_MEAL(listOf("라면", "큰컵"), ProductCategoryType.INSTANT_MEAL),
        DIARY_KEYWORD(listOf("우유"), ProductCategoryType.DIARY),
    }

    enum class CategoryURL(val URL: String) {
        FRESH_FOOD("https://gs25.gsretail.com/gscvs/ko/products/youus-freshfood"),
        PB("https://gs25.gsretail.com/gscvs/ko/products/youus-different-service"),
        DISCOUNT("https://gs25.gsretail.com/gscvs/ko/products/event-goods"),
    }
}
