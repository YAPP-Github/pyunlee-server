package com.yapp.cvs.job.crawl.util

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

object WebdriverUtil {
    fun initializeWebdriver(): ChromeDriver {
        WebDriverManager.chromedriver().setup()
        val chromeOptions = ChromeOptions()
//        chromeOptions.setHeadless(true)
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage")
        val driver = ChromeDriver(chromeOptions)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1))
        return driver
    }
}
