package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.enums.SellerType

enum class ProductCollectInfo(
    val url: String,
    val sellerType: SellerType,
    val productCategoryType: ProductCategoryType,
    val pageSize: Int
) {
    // 세븐일레븐 pageSize는 23.06.03 기준 최대 목록 개수
    SEVEN_ELEVEN_DOSIRAK_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=mini", SellerType.SEVEN_ELEVEN, ProductCategoryType.DOSIRAK, 34),
    SEVEN_ELEVEN_GIMBAP_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=noodle", SellerType.SEVEN_ELEVEN, ProductCategoryType.GIMBAP, 46),
    SEVEN_ELEVEN_SANDWICH_URL(
        "https://www.7-eleven.co.kr/product/dosirakNewMoreAjax.asp?pTab=d_group", SellerType.SEVEN_ELEVEN, ProductCategoryType.SANDWICH, 46),
    ;

    companion object {
        fun getAllBySellerType(targetSellerType: SellerType): List<ProductCollectInfo> {
            return ProductCollectInfo.values()
                .filter { it.sellerType == targetSellerType}
        }
    }
}