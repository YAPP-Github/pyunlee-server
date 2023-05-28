package com.yapp.cvs.job.crawl.instruction

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.job.crawl.ProductCollectorDto
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

interface WebdriverInstruction {
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

    fun setCategoryTo(category: ProductCategory?, driver: ChromeDriver)
    fun expandAllProductPage(driver: ChromeDriver)
    fun collect(category: ProductCategory, driver: ChromeDriver): List<ProductCollectorDto>
}
