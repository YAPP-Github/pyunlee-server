package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.extension.containsKeywords

enum class SevenElevenProductCollectInfo(
    val url: String,
    val productCategoryType: ProductCategoryType? = null,
    val pTab: String,
    val pageSize: Int,
    val isPbProduct: Boolean = false,
) {
    // 세븐일레븐 pageSize는 23.06.03 기준 최대 목록 개수
    DOSIRAK_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=mini",
        ProductCategoryType.DOSIRAK,
        "mini",
        34,
    ),
    GIMBAP_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=noodle",
        ProductCategoryType.GIMBAP,
        "noodle",
        46,
    ),
    SANDWICH_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=d_group",
        ProductCategoryType.SANDWICH,
        "d_group",
        46,
    ),
    PB_INIT_URL(
        "https://www.7-eleven.co.kr/product/presentList.asp",
        pTab = "5",
        pageSize = 0,
        isPbProduct = true,
    ),
    PB_URL(
        "https://www.7-eleven.co.kr/product/listMoreAjax.asp?pTab=5",
        pTab = "5",
        pageSize = 10,
        isPbProduct = true,
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
}
