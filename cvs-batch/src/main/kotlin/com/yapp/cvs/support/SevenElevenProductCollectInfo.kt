package com.yapp.cvs.support

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.entity.ProductPromotionType

enum class SevenElevenProductCollectInfo(
    val url: String,
    val productCategoryType: ProductCategoryType? = null,
    val pTab: String,
    val pageSize: Int,
    val isPbProduct: Boolean = false,
    val promotionType: ProductPromotionType? = null,
) {
    // 세븐일레븐 pageSize는 23.06.03 기준 최대 목록 개수
    DOSIRAK_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp",
        ProductCategoryType.DOSIRAK,
        "mini",
        34,
    ),
    GIMBAP_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp",
        ProductCategoryType.GIMBAP,
        "noodle",
        46,
    ),
    SANDWICH_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp",
        ProductCategoryType.SANDWICH,
        "d_group",
        46,
    ),
    PB_INIT_URL(
        "https://www.7-eleven.co.kr/product/presentList.asp",
        pTab = "5",
        pageSize = 0,
        isPbProduct = true
    ),
    PB_URL(
        "https://www.7-eleven.co.kr/product/listMoreAjax.asp",
        pTab = "5",
        pageSize = 10,
        isPbProduct = true
    ),
    ONE_PLUS_ONE_URL(
        "https://www.7-eleven.co.kr/product/listMoreAjax.asp",
        pTab = "1",
        pageSize = 50,
        promotionType = ProductPromotionType.ONE_PLUS_ONE,
    ),
    TWO_PLUS_ONE_URL(
        "https://www.7-eleven.co.kr/product/listMoreAjax.asp",
        pTab = "2",
        pageSize = 50,
        promotionType = ProductPromotionType.TWO_PLUS_ONE,
    ),
    ;
}
