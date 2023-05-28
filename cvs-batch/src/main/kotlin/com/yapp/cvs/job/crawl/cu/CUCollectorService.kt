package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductEventType
import com.yapp.cvs.job.crawl.ProductCollectorService
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.util.WebdriverUtil
import com.yapp.cvs.job.crawl.cu.CUCategoryInstruction.Companion.setCategoryTo
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDate

class CUCollectorService(
//        private val scrappingResultService: ScrappingResultService, // todo : 내 도메인으로 변경
) : ProductCollectorService {
    private val driver = WebdriverUtil.initializeWebdriver()

    override fun getCollection(): List<ProductCollectorDto> {
        try {
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")
            // 카테고리 설정
            val productItems = mutableListOf<ProductCollectorDto>()
            ProductCategory.values().forEach {
                setCategoryTo(category = it, driver = driver)
                expandAllProductPage()
                productItems.addAll(collect(category = it))
            }
            return productItems
        } finally {
            driver.quit()
        }
    }

    private fun collect(category: ProductCategory): List<ProductCollectorDto> {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))
        return driver.findElements(By.cssSelector("#dataTable > div.prodListWrap"))
                .flatMap { wrapper ->
                    wrapper.findElements(By.cssSelector("ul > li"))
                            .mapNotNull {
                                ProductCollectorDto(
                                        name = it.findElement(By.cssSelector("a > div.prod_wrap > div.prod_text > div.name")).text,
                                        price = it.findElement(By.cssSelector("a > div.prod_wrap > div.prod_text > div.price")).text.replace(
                                                Regex("[,원\\s]"),
                                                "",
                                        ).toBigDecimal(),
                                        imageUrl = it.findElement(By.cssSelector("a > div.prod_wrap > div.prod_img > img"))
                                                .getAttribute("src"),
                                        productEventType = ProductEventType.parse(it.findElement(By.cssSelector("a > div.badge > span")).text.trim()),
                                        isNew = false,
                                        category = category,
                                        code = parseProductCode(it.findElement(By.cssSelector("a > div.prod_wrap > div.prod_img > img"))
                                                .getAttribute("src"))
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

    override fun saveAll(discountedItems: List<ProductCollectorDto>) {
        val now = LocalDate.now()
        discountedItems.forEach {
//            try {
//                scrappingResultService.create(
//                        scrappingResultCreateVo = ScrappingResultCreateVo(
//                                name = it.name ?: "",
//                                price = it.price ?: BigDecimal.ZERO,
//                                imageUrl = it.imageUrl,
//                                discountType = it.discountType,
//                                storeType = StoreType.CU,
//                                referenceDate = now,
//                                referenceUrl = it.referenceUrl,
//                        )
//                )
//            } catch (e: ScrappingResultDuplicatedException) {
//                log.warn("scrappingResult is duplicated", e)
//            }
            println(it.toString()) // debug
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CUCollectorService::class.java)
        private val LINK_PATTERN = Regex("javascript:view\\((\\d+)\\);")
        private val PRODUCT_CODE_PATTERN = Regex("\\b\\d+\\b")
    }
}
