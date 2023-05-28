package com.yapp.cvs.config

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.Test
import org.junit.jupiter.api.Disabled
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.concurrent.TimeUnit

@Disabled
class SeleniumTest {
    @Test
    fun openWebDriverTest() {
        WebDriverManager.chromedriver().setup()
        // 1. Start the session
        val chromeOptions = ChromeOptions()
        chromeOptions.setHeadless(true) // no window option
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage")
        val driver = ChromeDriver(chromeOptions)
        // 2. Take action on browser
        driver.get("https://google.com")
        // 3. Request browser information
        driver.title
        // 4. Establish Waiting Strategy
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS)
        // 5. Find an element
        val searchBox = driver.findElement(By.name("q"))
        val searchButton = driver.findElement(By.name("btnK"))
        // 6. Take action on element (Search)
        searchBox.sendKeys("Selenium")
        searchButton.click()
        // 7. Request element information
        driver.findElement(By.name("q")).getAttribute("value")
        // 8. End the session
        driver.quit()
    }
}
