package com.yapp.cvs.job.crawl.cu.handler

import com.yapp.cvs.domains.product.entity.ProductCategory
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
    override fun <T : Enum<T>> setCategoryTo(category: T, driver: ChromeDriver) {
        val cuCategory = category as ProductCategory
        val jsExecutor = driver as JavascriptExecutor
        val mainCategoryInstruction = getMainCategoryScript(cuCategory)
        val subCategoryInstruction = getSubCategoryScript(cuCategory)

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

    override fun <T : Enum<T>>collect(category: T, driver: ChromeDriver): List<ProductCollectorDto> {
        val cuCategory = category as ProductCategory
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))
        return driver.findElements(By.cssSelector("#dataTable > div.prodListWrap"))
            .flatMap { wrapper ->
                wrapper.findElements(By.cssSelector("ul > li"))
                    .mapNotNull {
                        ProductCollectorDto(
                            name = it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.name > p")).text,
                            price = parseProductPrice(it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.price")).text),
                            imageUrl = it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img"))
                                .getAttribute("src"),
                            productEventType = ProductEventType.parse(it.findElement(By.cssSelector("div.prod_item > div.badge")).text.trim()),
                            isNew = it.findElement(By.cssSelector("div.prod_item > div.tag")).findElements(By.cssSelector("span.new")).isNotEmpty(),
                            category = cuCategory,
                            code = parseProductCode(
                                it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img"))
                                    .getAttribute("src"),
                            ),
                        )
                    }
            }
    }

    private fun parseProductPrice(price: String): Int {
        return price.replace(
            Regex("\\D+"),
            "",
        ).toIntOrNull() ?: 0
    }

    /** 주어진 URL 에서 13자리 숫자(유통코드)를 정규식으로 찾습니다 */
    private fun parseProductCode(imageSrc: String): String? {
        return PRODUCT_CODE_PATTERN.find(imageSrc)?.value
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CUCollectorService::class.java)
        private val PRODUCT_CODE_PATTERN = Regex("\\d{13}")
    }
}
