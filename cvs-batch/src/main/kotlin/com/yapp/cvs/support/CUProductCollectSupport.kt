package com.yapp.cvs.support

import com.yapp.cvs.domain.enums.ProductCategoryType
import com.yapp.cvs.domain.product.entity.ProductPromotionType

enum class CUProductCollectSupport(
    val url: String,
    val productCategoryType: ProductCategoryType? = null,
    val promotionType: ProductPromotionType? = null,
    val isPbProduct: Boolean = false,
) {
    DOSIRAK_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=10&searchSubCategory=1",
        ProductCategoryType.DOSIRAK,
    ),
    SANDWICH_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=10&searchSubCategory=3",
        ProductCategoryType.SANDWICH,
    ),
    GIMBAP_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=10&searchSubCategory=3",
        ProductCategoryType.GIMBAP,
    ),

    FRIES_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=20&searchSubCategory=4",
        ProductCategoryType.FRIES,
    ),
    BAKERY_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=20&searchSubCategory=5",
        ProductCategoryType.BAKERY,
    ),
    INSTANT_COFFEE_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=20&searchSubCategory=6",
        ProductCategoryType.INSTANT_COFFEE,
    ),

    BISCUIT_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=30&searchSubCategory=71",
        ProductCategoryType.BISCUIT,
    ),
    DESSERT_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=30&searchSubCategory=7",
        ProductCategoryType.DESSERT,
    ),
    CANDY_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=30&searchSubCategory=8",
        ProductCategoryType.CANDY,
    ),

    ICE_CREAM_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=40&searchSubCategory=9",
        ProductCategoryType.ICE_CREAM,
    ),

    INSTANT_MEAL_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=50&searchSubCategory=12",
        ProductCategoryType.INSTANT_MEAL,
    ),

    MUNCHIES_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=50&searchSubCategory=10",
        ProductCategoryType.MUNCHIES,
    ),
    INGREDIENT_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=50&searchSubCategory=11",
        ProductCategoryType.INGREDIENT,
    ),

    DRINK_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=60&searchSubCategory=13",
        ProductCategoryType.DRINK,
    ),
    ICED_DRINK_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=60&searchSubCategory=14",
        ProductCategoryType.ICED_DRINK,
    ),
    DIARY_URL(
        "https://cu.bgfretail.com/product/productAjax.do?searchMainCategory=60&searchSubCategory=15",
        ProductCategoryType.DIARY,
    ),

    PB_URL(
        "https://cu.bgfretail.com/product/pbAjax.do?searchgubun=PBG",
        isPbProduct = true
    ),

    CU_ONLY_URL(
        "https://cu.bgfretail.com/product/pbAjax.do?searchgubun=CUG",
        isPbProduct = true
    ),

    ONE_PLUS_ONE_URL(
        "https://cu.bgfretail.com/event/plusAjax.do?searchCondition=23",
        promotionType = ProductPromotionType.ONE_PLUS_ONE
    ),
    TWO_PLUS_ONE_URL(
        "https://cu.bgfretail.com/event/plusAjax.do?searchCondition=24",
        promotionType = ProductPromotionType.TWO_PLUS_ONE
    )
    ;
}
