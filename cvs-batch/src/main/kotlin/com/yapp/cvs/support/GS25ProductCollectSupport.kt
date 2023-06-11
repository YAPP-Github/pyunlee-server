package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType

enum class GS25ProductCollectSupport(
    val url: String,
    val jsonDataKey: String?,
    val productCategoryType: ProductCategoryType?,
    val searchProduct: String?,
    val isPbProduct: Boolean = false
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

    DISCOUNT_URL(
        "http://gs25.gsretail.com/gscvs/ko/products/event-goods-search?parameterList=TOTAL",
        "results",
        null,
        null,
        isPbProduct = false
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
