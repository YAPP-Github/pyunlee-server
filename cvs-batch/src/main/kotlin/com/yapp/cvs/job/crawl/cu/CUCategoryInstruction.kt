package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductCategory.BAKERY
import com.yapp.cvs.domains.product.entity.ProductCategory.BISCUIT
import com.yapp.cvs.domains.product.entity.ProductCategory.CANDY
import com.yapp.cvs.domains.product.entity.ProductCategory.DESSERT
import com.yapp.cvs.domains.product.entity.ProductCategory.DIARY
import com.yapp.cvs.domains.product.entity.ProductCategory.DOSIRAK
import com.yapp.cvs.domains.product.entity.ProductCategory.DRINK
import com.yapp.cvs.domains.product.entity.ProductCategory.FRIES
import com.yapp.cvs.domains.product.entity.ProductCategory.GIMBAP
import com.yapp.cvs.domains.product.entity.ProductCategory.ICED_DRINK
import com.yapp.cvs.domains.product.entity.ProductCategory.ICE_CREAM
import com.yapp.cvs.domains.product.entity.ProductCategory.INGREDIENT
import com.yapp.cvs.domains.product.entity.ProductCategory.INSTANT_COFFEE
import com.yapp.cvs.domains.product.entity.ProductCategory.INSTANT_MEAL
import com.yapp.cvs.domains.product.entity.ProductCategory.MUNCHIES
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.BEVERAGE
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.COOK
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.FOOD
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.SIMPLE_MEAL
import com.yapp.cvs.domains.product.entity.ProductCategory.ProductSuperCategory.SNACK
import com.yapp.cvs.domains.product.entity.ProductCategory.SANDWICH
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver

class CUCategoryInstruction {
    companion object {
        fun setCategoryTo(category: ProductCategory, driver: ChromeDriver) {
            setMainCategoryOf(category, driver)
            val jsExecutor = driver as JavascriptExecutor
            val subCategoryInstruction = getSubCategoryInstruction(category)
            jsExecutor.executeScript(subCategoryInstruction)
        }

        private fun setMainCategoryOf(category: ProductCategory, driver: ChromeDriver) {
            val depth = when (category.superCategory) {
                SIMPLE_MEAL -> 1
                COOK -> 2
                SNACK -> 3
                ProductCategory.ProductSuperCategory.ICE_CREAM -> 4
                FOOD -> 5
                BEVERAGE -> 6
            }
            driver.findElement(By.cssSelector("#contents > div.depth3Lnb > ul > li.prodInfo_0$depth > a")).click()
        }

        private fun getSubCategoryInstruction(category: ProductCategory): String {
            return when (category) {
                DOSIRAK -> "gosub('1', 2)"
                SANDWICH -> "gosub('3', 3)"
                GIMBAP -> "gosub('2', 4)"

                FRIES -> "gosub('4', 2)"
                BAKERY -> "gosub('5', 3)"
                INSTANT_COFFEE -> "gosub('6', 4)"

                BISCUIT -> "gosub('71', 2)"
                DESSERT -> "gosub('7', 3)"
                CANDY -> "gosub('8', 4)"

                ICE_CREAM -> "gosub('9', 2)"

                INSTANT_MEAL -> "gosub('12', 2)"
                MUNCHIES -> "gosub('10', 3)"
                INGREDIENT -> "gosub('11', 4)"

                DRINK -> "gosub('13', 2)"
                ICED_DRINK -> "gosub('14', 3)"
                DIARY -> "gosub('15', 4)"
            }
        }
    }
}
