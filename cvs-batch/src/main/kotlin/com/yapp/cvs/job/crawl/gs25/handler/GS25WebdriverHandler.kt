package com.yapp.cvs.job.crawl.gs25.handler

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domains.enums.RetailerType.GS
import com.yapp.cvs.support.GS25ProductCollectSupport
import com.yapp.cvs.support.ProductCategoryRule
import com.yapp.cvs.support.ProductDataParser.PBBrandNameRule.GS25
import com.yapp.cvs.support.ProductDataParser.parseBrandName
import com.yapp.cvs.support.ProductDataParser.parsePrice
import com.yapp.cvs.support.ProductDataParser.parseProductCode
import com.yapp.cvs.support.ProductDataParser.parseProductName
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

class GS25WebdriverHandler {
    private val driver: ChromeDriver
    private val wait: WebDriverWait
    private val jsExecutor: JavascriptExecutor

    init {
        WebDriverManager.chromedriver().setup()
        val chromeOptions = ChromeOptions()
        chromeOptions.setHeadless(true)
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage")
        driver = ChromeDriver(chromeOptions)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2))
        wait = WebDriverWait(driver, Duration.ofSeconds(2))
        jsExecutor = driver
    }

    fun collect(category: GS25ProductCollectSupport): List<ProductRawDataVO> {
        log.info("Target Category : ${category.productCategoryType?.displayName}")
        this.setCategoryTo(category)
        val productCollections = mutableListOf<ProductRawDataVO>()
        do {
            // 행사 상품과 PB 상품의 className 이 다름
            val items = driver.findElements(By.cssSelector(category.getItemsXPath()))
            items.forEach {
                val title = it.findElement(By.cssSelector("div > p.tit")).text
                val price = it.findElement(By.cssSelector("div > p.price")).text
                val imgURL = it.findElement(By.cssSelector("div > p.img > img")).getAttribute("src")

                productCollections.add(
                    ProductRawDataVO(
                        name = parseProductName(title, GS25),
                        brandName = parseBrandName(title, GS25),
                        price = parsePrice(price),
                        categoryType = category.productCategoryType ?: ProductCategoryRule.parse(title),
                        barcode = parseProductCode(imgURL) ?: "",
                        imageUrl = imgURL,
                        retailerType = GS,
                        isPbProduct = category.isPbProduct,
                    ),
                )
            }
            this.setNextPage(category)
        } while (this.hasNextPage(category))
        return productCollections
    }

    private fun setCategoryTo(category: GS25ProductCollectSupport) {
        driver.get(category.url)
        driver.findElement(By.id(category.tabId)).click()
        this.waitReadyState()
    }

    private fun setNextPage(category: GS25ProductCollectSupport) {
        driver.findElement(By.cssSelector(category.getNextPageButtonXPath())).click()
        this.waitReadyState()
    }

    private fun hasNextPage(category: GS25ProductCollectSupport): Boolean {
        return driver.findElement(By.cssSelector(category.getNextPageButtonXPath()))
            .getAttribute("onclick") != null
    }

    private fun waitReadyState() {
        val jQueryLoad = ExpectedConditions.jsReturnsValue("return jQuery.active == 0")
        val jsLoad = ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")
        wait.until(jQueryLoad)
        wait.until(jsLoad)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GS25WebdriverHandler::class.java)
    }
}
