package com.yapp.cvs.job.crawl

import com.yapp.cvs.domain.collect.ProductRawDataVO
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

interface WebdriverHandler {
    fun initializeWebdriver(): ChromeDriver {
        WebDriverManager.chromedriver().setup()
        val chromeOptions = ChromeOptions()
        chromeOptions.setHeadless(true)
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage")
        val driver = ChromeDriver(chromeOptions)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1))
        return driver
    }

    fun <T : Enum<T>>setCategoryTo(category: T, driver: ChromeDriver)
    fun expandAllProductPage(driver: ChromeDriver) {}
    fun setNextPage(driver: ChromeDriver) {}
    fun <T : Enum<T>> collect(category: T, driver: ChromeDriver): List<ProductRawDataVO>
}
