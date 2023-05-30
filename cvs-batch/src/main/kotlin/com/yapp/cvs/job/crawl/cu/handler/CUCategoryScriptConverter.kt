package com.yapp.cvs.job.crawl.cu.handler

import com.yapp.cvs.domains.product.entity.ProductCategory

object CUCategoryScriptConverter {

    fun getMainCategoryScript(category: ProductCategory): String {
        return when (category.superCategory) {
            ProductCategory.ProductSuperCategory.SIMPLE_MEAL -> "gomaincategory('10', 1)"
            ProductCategory.ProductSuperCategory.COOK -> "gomaincategory('20', 2)"
            ProductCategory.ProductSuperCategory.SNACK -> "gomaincategory('30', 3)"
            ProductCategory.ProductSuperCategory.ICE_CREAM -> "gomaincategory('40', 4)"
            ProductCategory.ProductSuperCategory.FOOD -> "gomaincategory('50', 5)"
            ProductCategory.ProductSuperCategory.BEVERAGE -> "gomaincategory('60', 6)"
            ProductCategory.ProductSuperCategory.UNKNOWN -> ""
        }
    }

    fun getSubCategoryScript(category: ProductCategory): String {
        return when (category) {
            ProductCategory.DOSIRAK -> "gosub('1', 2)"
            ProductCategory.SANDWICH -> "gosub('3', 3)"
            ProductCategory.GIMBAP -> "gosub('2', 4)"

            ProductCategory.FRIES -> "gosub('4', 2)"
            ProductCategory.BAKERY -> "gosub('5', 3)"
            ProductCategory.INSTANT_COFFEE -> "gosub('6', 4)"

            ProductCategory.BISCUIT -> "gosub('71', 2)"
            ProductCategory.DESSERT -> "gosub('7', 3)"
            ProductCategory.CANDY -> "gosub('8', 4)"

            ProductCategory.ICE_CREAM -> "gosub('9', 2)"

            ProductCategory.INSTANT_MEAL -> "gosub('12', 2)"
            ProductCategory.MUNCHIES -> "gosub('10', 3)"
            ProductCategory.INGREDIENT -> "gosub('11', 4)"

            ProductCategory.DRINK -> "gosub('13', 2)"
            ProductCategory.ICED_DRINK -> "gosub('14', 3)"
            ProductCategory.DIARY -> "gosub('15', 4)"

            ProductCategory.UNKNOWN -> ""
        }
    }
}
