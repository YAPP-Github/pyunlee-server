package com.yapp.cvs.job.crawl.cu.handler

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductCategory.UNKNOWN
import com.yapp.cvs.domains.product.entity.ProductEventType
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.WebdriverHandler
import com.yapp.cvs.job.crawl.cu.CUCollectorService
import com.yapp.cvs.job.crawl.cu.handler.CUCategoryScriptConverter.getMainCategoryScript
import com.yapp.cvs.job.crawl.cu.handler.CUCategoryScriptConverter.getSubCategoryScript
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
class CUWebdriverHandler : WebdriverHandler {
    override fun setCategoryTo(category: ProductCategory?, driver: ChromeDriver) {
        category ?: return
        val jsExecutor = driver as JavascriptExecutor
        val mainCategoryInstruction = getMainCategoryScript(category)
        val subCategoryInstruction = getSubCategoryScript(category)

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
                if (exceptionCount >= 5) {
                    log.info("더 보기 버튼이 없음")
                }
            } catch (e: Exception) {
                exceptionCount++
                if (exceptionCount >= 5) {
                    log.error("알 수 없는 에러. ${e.message}")
                }
            }
            Thread.sleep(1000)
        }
    }

    override fun collect(category: ProductCategory?, driver: ChromeDriver): List<ProductCollectorDto> {
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
                            category = category ?: UNKNOWN,
                            code = parseProductCode(
                                it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img"))
                                    .getAttribute("src"),
                            ),
                        )
                    }
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
