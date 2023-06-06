package com.yapp.cvs.support

import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.support.GS25ProductCollectSupport.CategoryURL.DISCOUNT
import com.yapp.cvs.support.GS25ProductCollectSupport.CategoryURL.FRESH_FOOD
import com.yapp.cvs.support.GS25ProductCollectSupport.CategoryURL.PB

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
        null,
        tabId = "TOTAL",
        isPbProduct = false,
    ),
    ;

    fun getItemsXPath(): String {
        return when {
            this.isPbProduct -> "${BaseXPath.PB.value} > div.tab_cont > ul > li"
            else -> "${BaseXPath.EVENT.value} > ul > li"
        }
    }

    fun getNextPageButtonXPath(): String {
        return when {
            this.isPbProduct -> "${BaseXPath.PB.value} > div.paging > a.next"
            else -> "${BaseXPath.EVENT.value} > div.paging > a.next"
        }
    }

    enum class CategoryURL(val URL: String) {
        FRESH_FOOD("https://gs25.gsretail.com/gscvs/ko/products/youus-freshfood"),
        PB("https://gs25.gsretail.com/gscvs/ko/products/youus-different-service"),
        DISCOUNT("https://gs25.gsretail.com/gscvs/ko/products/event-goods")
    }

    enum class BaseXPath(val value: String) {
        PB("#contents > div.yCmsComponent > div > div > div > div > div > div.tblwrap"),
        EVENT("#contents > div.cnt > div.mt50 > div > div > div:nth-of-type(4)")
    }
}
