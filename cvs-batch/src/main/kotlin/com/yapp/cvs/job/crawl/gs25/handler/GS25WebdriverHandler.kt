package com.yapp.cvs.job.crawl.gs25.handler

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.enums.ProductCategoryType
import com.yapp.cvs.domains.enums.RetailerType.GS
import com.yapp.cvs.job.crawl.WebdriverHandler
import com.yapp.cvs.support.GS25ProductCollectSupport
import com.yapp.cvs.support.ProductDataParser.parseBrandName
import com.yapp.cvs.support.ProductDataParser.parsePrice
import com.yapp.cvs.support.ProductDataParser.parseProductCode
import com.yapp.cvs.support.ProductDataParser.parseProductName
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
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

    override fun <T : Enum<T>> collect(category: T, driver: ChromeDriver): List<ProductRawDataVO> {
        val gs25Category = category as GS25ProductCollectSupport
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))

        val productCollections = mutableListOf<ProductRawDataVO>()
        do {
            // 행사 상품과 PB 상품의 className 이 다름
            val items = driver.findElements(By.cssSelector(gs25Category.getItemsXPath()))
            items.forEach {
                val title = it.findElement(By.cssSelector("div > p.tit")).text
                val price = it.findElement(By.cssSelector("div > p.price")).text
                val imgURL = it.findElement(By.cssSelector("div > p.img > img")).getAttribute("src")
                val eventInfo = it.findElement(By.cssSelector("div > div > p")).text

                productCollections.add(
                    ProductRawDataVO(
                        name = parseProductName(title),
                        brandName = parseBrandName(title),
                        price = parsePrice(price),
                        categoryType = gs25Category.productCategoryType ?: ProductCategoryType.parse(title),
                        barcode = parseProductCode(imgURL) ?: "",
                        imageUrl = imgURL,
                        retailerType = GS,
                        isPbProduct = gs25Category.isPbProduct,
                    ),
                )
            }
            this.setNextPage(gs25Category, driver)
        } while (hasNextPage(gs25Category, driver))

        return productCollections
    }

    private fun hasNextPage(category: GS25ProductCollectSupport, driver: ChromeDriver): Boolean {
        val buttonXPath = category.getNextPageButtonXPath()
        return driver.findElement(By.cssSelector(buttonXPath)).getAttribute("onclick") != null
    }

    private fun setNextPage(category: GS25ProductCollectSupport, driver: ChromeDriver) {
        val buttonXPath = category.getNextPageButtonXPath()
        var exceptionCount = 0
        while (exceptionCount < 3) {
            try {
                driver.findElement(By.cssSelector(buttonXPath)).click()
                break
            } catch (_: InvalidElementStateException) {
            } catch (_: StaleElementReferenceException) {
            } catch (e: NoSuchElementException) {
                exceptionCount++
                if (exceptionCount >= 3) {
                    log.info("버튼을 찾을 수 없음")
                }
            }
        }
        Thread.sleep(1500)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GS25WebdriverHandler::class.java)
    }
}
