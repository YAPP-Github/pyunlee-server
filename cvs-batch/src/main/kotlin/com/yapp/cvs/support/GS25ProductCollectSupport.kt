package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.product.entity.ProductPromotionType

enum class GS25ProductCollectSupport(
    val url: String,
    val jsonDataKey: String?,
    val productCategoryType: ProductCategoryType? = null,
    val searchProduct: String? = null,
    val isPbProduct: Boolean = false,
    val promotionType: ProductPromotionType? = null,
) {
    DOSIRAK_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=FreshFoodKey",
        "SubPageListData",
        ProductCategoryType.DOSIRAK,
        "productLunch",
        true
    ),
    GIMBAP_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=FreshFoodKey",
        "SubPageListData",
        ProductCategoryType.GIMBAP,
        "productRice",
        true
    ),
    SANDWICH_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=FreshFoodKey",
        "SubPageListData",
        ProductCategoryType.SANDWICH,
        "productBurger",
        true
    ),
    SIMPLE_MEAL_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=FreshFoodKey",
        "SubPageListData",
        ProductCategoryType.INSTANT_MEAL,
        "productSnack",
        true
    ),

    DRINK_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=DifferentServiceKey",
        "SubPageListData",
        ProductCategoryType.DRINK,
        "productDrink",
        true
    ),
    DIARY_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=DifferentServiceKey",
        "SubPageListData",
        ProductCategoryType.DIARY,
        "productMilk",
        true
    ),
    BISCUIT_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=DifferentServiceKey",
        "SubPageListData",
        ProductCategoryType.BISCUIT,
        "productCookie",
        true
    ),
    INSTANT_MEAL_URL(
        "http://gs25.gsretail.com/products/youus-freshfoodDetail-search?searchSrvFoodCK=DifferentServiceKey",
        "SubPageListData",
        ProductCategoryType.INSTANT_MEAL,
        "productRamen",
        true
    ),

    ONE_PLUS_ONE_URL(
        "http://gs25.gsretail.com/gscvs/ko/products/event-goods-search?parameterList=TOTAL&parameterList=ONE_TO_ONE",
        "results",
        promotionType = ProductPromotionType.ONE_PLUS_ONE,
    ),

    TWO_PLUS_ONE_URL(
        "http://gs25.gsretail.com/gscvs/ko/products/event-goods-search?parameterList=TOTAL&parameterList=TWO_TO_ONE",
        "results",
        promotionType = ProductPromotionType.TWO_PLUS_ONE,
    )
    ;

    fun getQueryParams(): Map<String, Any?> {
        return mapOf(
            "pageNum" to 1,
            "pageSize" to 10000,
            "searchProduct" to searchProduct
        )
    }
}
