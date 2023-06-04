package com.yapp.cvs.job.crawl.gs25

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.job.crawl.gs25.GS25Category.GS25SuperCategory.FRESH_FOOD
import com.yapp.cvs.job.crawl.gs25.GS25Category.GS25SuperCategory.PB
import com.yapp.cvs.job.crawl.gs25.GS25Category.GS25SuperCategory.UNKNOWN

@Suppress("SpellCheckingInspection")
enum class GS25Category(val kr: String, val gS25SuperCategory: GS25SuperCategory) {

    DOSIRAK("도시락", FRESH_FOOD),
    GIMBAP("김밥/주먹밥", FRESH_FOOD),
    SANDWICH("햄버거/샌드위치", FRESH_FOOD),
    SIMPLE_MEAL("간편식", FRESH_FOOD),

    DRINK("음료/커피", PB),
    DIARY("유제품", PB),
    BISCUIT("과자/간식", PB),
    INSTANT_MEAL("라면/가공식품", PB),

    UNKNOWN("알수없음", GS25SuperCategory.UNKNOWN),
    ;

    companion object {
        fun convert(category: GS25Category): ProductCategory = when (category) {
            DOSIRAK -> ProductCategory.DOSIRAK
            GIMBAP -> ProductCategory.GIMBAP
            SANDWICH -> ProductCategory.SANDWICH
            SIMPLE_MEAL, INSTANT_MEAL -> ProductCategory.INSTANT_MEAL
            DRINK -> ProductCategory.DRINK
            DIARY -> ProductCategory.DIARY
            BISCUIT -> ProductCategory.BISCUIT

            else -> ProductCategory.UNKNOWN
        }
    }

    enum class GS25SuperCategory(val kr: String) {
        FRESH_FOOD("냉장식품"),
        PB("유어스상품"),
        DISCOUNT("행사상품"),
        UNKNOWN("알수없음"),
    }
}
