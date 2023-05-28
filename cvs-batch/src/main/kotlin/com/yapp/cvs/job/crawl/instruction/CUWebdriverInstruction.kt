package com.yapp.cvs.job.crawl.instruction

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductEventType
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.cu.CUCollectorService
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CUWebdriverInstruction : WebdriverInstruction {
    override fun setCategoryTo(category: ProductCategory?, driver: ChromeDriver) {
        category ?: return
        val jsExecutor = driver as JavascriptExecutor
        val mainCategoryInstruction = getMainCategoryInstruction(category)
        val subCategoryInstruction = getSubCategoryInstruction(category)

        jsExecutor.executeScript(mainCategoryInstruction)
        Thread.sleep(2000)
        jsExecutor.executeScript(subCategoryInstruction)
    }

    override fun expandAllProductPage(driver: ChromeDriver) {
        var exceptionCount = 0
        while (exceptionCount < 5) {
            try {
                driver.findElement(By.cssSelector("#contents > div.relCon > div > div > div > div.prodListBtn-w > a")).click()
            } catch (_: InvalidElementStateException) {
            } catch (_: StaleElementReferenceException) {
            } catch (e: NoSuchElementException) {
                exceptionCount++
                println("retry... $exceptionCount")
                if (exceptionCount >= 5) {
                    log.info("더 보기 버튼이 없음")
                }
            } catch (e: Exception) {
                exceptionCount++
                println("retry... $exceptionCount")
                if (exceptionCount >= 5) {
                    log.error("알 수 없는 에러. ${e.message}")
                }
            }
            Thread.sleep(1000)
        }
    }

    override fun collect(category: ProductCategory, driver: ChromeDriver): List<ProductCollectorDto> {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))
        return driver.findElements(By.cssSelector("#dataTable > div.prodListWrap"))
            .flatMap { wrapper ->
                wrapper.findElements(By.cssSelector("ul > li"))
                    .mapNotNull {
                        ProductCollectorDto(
                            name = it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.name > p")).text,
                            price = it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.price")).text.replace(
                                Regex("[,원\\s]"),
                                "",
                            ).toBigDecimal(),
                            imageUrl = it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img"))
                                .getAttribute("src"),
                            productEventType = ProductEventType.parse(it.findElement(By.cssSelector("div.prod_item > div.badge")).text.trim()),
                            isNew = it.findElement(By.cssSelector("div.prod_item > div.tag")).findElements(By.cssSelector("span.new")).isNotEmpty(),
                            category = category,
                            code = parseProductCode(
                                it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img"))
                                    .getAttribute("src"),
                            ),
                        )
                    }
            }
    }

    private fun getMainCategoryInstruction(category: ProductCategory): String {
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

    private fun getSubCategoryInstruction(category: ProductCategory): String {
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

    private fun parseProductCode(imageSrc: String): String? {
        return PRODUCT_CODE_PATTERN.find(imageSrc)?.value
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CUCollectorService::class.java)
        private val PRODUCT_CODE_PATTERN = Regex("\\b\\d+\\b")
    }
}
