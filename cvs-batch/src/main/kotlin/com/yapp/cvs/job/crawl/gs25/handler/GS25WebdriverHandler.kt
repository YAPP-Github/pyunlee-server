package com.yapp.cvs.job.crawl.gs25.handler

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.enums.RetailerType.GS
import com.yapp.cvs.job.crawl.ProductCollectorDto
import com.yapp.cvs.job.crawl.WebdriverHandler
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class GS25WebdriverHandler : WebdriverHandler {
    override fun <T : Enum<T>>setCategoryTo(category: T, driver: ChromeDriver) {
        val gs25Category = category as GS25ProductCollectSupport
        log.info("Target Category : ${gs25Category.productCategoryType?.displayName}")
        driver.get(gs25Category.url)
        driver.findElement(By.id(gs25Category.tabId)).click()
    }

    override fun <T : Enum<T>> collect(category: T, driver: ChromeDriver): List<ProductCollectorDto> {
        val gs25Category = category as GS25ProductCollectSupport
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))

        val productCollections = mutableListOf<ProductRawDataVO>()
        do {
            val items = driver.findElements(By.cssSelector("$BASE_ELEMENT_XPATH > div.tab_cont > ul > li"))
            items.forEach {
                val title = it.findElement(By.cssSelector("div > p.tit")).text
                val price = it.findElement(By.cssSelector("div > p.price")).text
                val imgURL = it.findElement(By.cssSelector("div > p.img > img")).getAttribute("src")
//                    val flag = it.findElement(By.cssSelector("div > div > p")).text // 신상품, 행사정보

                productCollections.add(
                    ProductRawDataVO(
                        name = parseProductName(title),
                        brandName = parseBrandName(title),
                        price = parseProductPrice(price),
                        categoryType = gs25Category.productCategoryType
                            ?: gs25Category.parseProductCategoryType(parseProductName(title)),
                        barcode = parseProductCode(imgURL) ?: "",
                        imageUrl = imgURL,
                        retailerType = GS,
                        isPbProduct = gs25Category.isPbProduct,
                    ),
                )
            }
            this.setNextPage(driver)
        } while (hasNextPage(driver))

        println(productCollections)
        return listOf()
    }

    private fun hasNextPage(driver: ChromeDriver): Boolean {
        return driver.findElement(By.cssSelector("$BASE_ELEMENT_XPATH > div.paging > a.next")).getAttribute("onClick") != null
    }

    override fun setNextPage(driver: ChromeDriver) {
        driver.findElement(By.cssSelector("$BASE_ELEMENT_XPATH > div.paging > a.next")).click()
        Thread.sleep(1000)
    }

    private fun parseProductName(name: String): String {
        val closingParenIndex = name.indexOf(')')
        return if (closingParenIndex != -1) {
            name.substring(closingParenIndex + 1)
        } else {
            name
        }
    }

    private fun parseBrandName(name: String): String {
        val closingParenIndex = name.indexOf(')')
        return if (closingParenIndex != -1) {
            name.substring(0, closingParenIndex + 1)
        } else {
            ""
        }
    }

    private fun parseProductPrice(price: String): Long {
        return price.replace(
            Regex("\\D+"),
            "",
        ).toLongOrNull() ?: 0
    }

    /** 주어진 URL 에서 13자리 숫자(유통코드)를 정규식으로 찾습니다 */
    private fun parseProductCode(imageSrc: String): String? {
        return PRODUCT_CODE_PATTERN.find(imageSrc)?.value
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GS25WebdriverHandler::class.java)
        private const val BASE_ELEMENT_XPATH = "#contents > div.yCmsComponent > div > div > div > div > div > div.tblwrap"
        private val PRODUCT_CODE_PATTERN = Regex("\\d{13}")
    }
}
