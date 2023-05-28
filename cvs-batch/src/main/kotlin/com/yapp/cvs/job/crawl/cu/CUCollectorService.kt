package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.ProductService
import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductEventType
import com.yapp.cvs.domains.product.model.vo.ProductCollectionVo
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.cu.CUCategoryInstruction.Companion.setCategoryTo
import com.yapp.cvs.job.crawl.util.WebdriverUtil
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

class CUCollectorService(
    private val productService: ProductService,
) : ProductCollectorService {
    private val driver = WebdriverUtil.initializeWebdriver()

    override fun getCollection(): List<ProductCollectorDto> {
        try {
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")
            // 카테고리 설정
            val productItems = mutableListOf<ProductCollectorDto>()
//            ProductCategory.values().forEach {
            setCategoryTo(category = ProductCategory.DIARY, driver = driver)
            expandAllProductPage()
            productItems.addAll(collect(category = ProductCategory.DIARY))
//            }
            return productItems
        } finally {
            driver.quit()
        }
    }

    override fun saveAll(productCollections: List<ProductCollectorDto>) {
        productCollections.forEach {
            productService.save(
                ProductCollectionVo(
                    name = it.name ?: "",
                    price = it.price?.toInt() ?: 0,
                    imageUrl = it.imageUrl,
                    productEventType = it.productEventType,
                    isNew = it.isNew ?: false,
                    code = it.code ?: "",
                    category = it.category,
                ),
            )
            println(it.toString())
        }
    }

    private fun collect(category: ProductCategory): List<ProductCollectorDto> {
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

    private fun expandAllProductPage() {
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

    private fun parseProductCode(imageSrc: String): String? {
        return PRODUCT_CODE_PATTERN.find(imageSrc)?.value
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CUCollectorService::class.java)
        private val PRODUCT_CODE_PATTERN = Regex("\\b\\d+\\b")
    }
}
