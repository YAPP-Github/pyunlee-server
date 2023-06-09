package com.yapp.cvs.batch

import com.yapp.cvs.domains.product.entity.ProductCategory
import com.yapp.cvs.domains.product.entity.ProductEventType
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

class CUCrawlingTest {
    private lateinit var driver: ChromeDriver

    @Before
    fun setup() {
        driver = initializeWebdriver()
    }

    @After
    fun cleanup() {
        driver.quit()
    }

    @Test
    fun cuCollectorInstructionsTest() {
        try {
            // cu 전체상품 페이지 - 전체
            driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1")

//            ProductCategory.values().forEach {category ->
            setCategoryTo(category = ProductCategory.DIARY)
            expandAllProductPage()

            val items = collectProductItems(category = ProductCategory.DIARY)
            items.forEach {
                println("상품명 : ${it.name}")
                println("가격 : ${it.price}")
                println("이미지 : ${it.imageUrl}")
                println("이벤트 : ${it.productEventType}")
                println("신상품유무 : ${it.isNew}")
                println("카테고리 : ${it.category.kr}")
                println("유통코드 : ${it.code}")
                println("=================================")
            }

            val invalidItems = items.filter {
                try {
                    it.validate()
                    false
                } catch (e: Exception) {
                    true
                }
            }
            println("invalid items: $invalidItems")
//            }
        } finally {
            driver.quit()
        }
    }

    private fun initializeWebdriver(): ChromeDriver {
        WebDriverManager.chromedriver().setup()
        val chromeOptions = ChromeOptions()
        chromeOptions.setHeadless(true) // no window option
        chromeOptions.addArguments("--remote-allow-origins=*")
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage")
        val driver = ChromeDriver(chromeOptions)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1))
        return driver
    }

    private fun setCategoryTo(category: ProductCategory) {
        val jsExecutor = driver as JavascriptExecutor
        val mainCategoryInstruction = getMainCategoryInstruction(category)
        val subCategoryInstruction = getSubCategoryInstruction(category)

        jsExecutor.executeScript(mainCategoryInstruction)
        Thread.sleep(2000)
        jsExecutor.executeScript(subCategoryInstruction)
    }

    private fun getMainCategoryInstruction(category: ProductCategory): String {
        return when (category.superCategory) {
            ProductCategory.ProductSuperCategory.SIMPLE_MEAL -> "gomaincategory('10', 1)"
            ProductCategory.ProductSuperCategory.COOK -> "gomaincategory('20', 2)"
            ProductCategory.ProductSuperCategory.SNACK -> "gomaincategory('30', 3)"
            ProductCategory.ProductSuperCategory.ICE_CREAM -> "gomaincategory('40', 4)"
            ProductCategory.ProductSuperCategory.FOOD -> "gomaincategory('50', 5)"
            ProductCategory.ProductSuperCategory.BEVERAGE -> "gomaincategory('60', 6)"
            ProductCategory.ProductSuperCategory.UNKNOWN -> ""
        }
    }

    private fun getSubCategoryInstruction(category: ProductCategory): String {
        return when (category) {
            ProductCategory.DOSIRAK -> "gosub('1', 2)"
            ProductCategory.SANDWICH -> "gosub('3', 3)"
            ProductCategory.GIMBAP -> "gosub('2', 4)"

            ProductCategory.FRIES -> "gosub('4', 2)"
            ProductCategory.BAKERY -> "gosub('5', 3)"
            ProductCategory.INSTANT_COFFEE -> "gosub('6', 4)"

            ProductCategory.BISCUIT -> "gosub('71', 2)"
            ProductCategory.DESSERT -> "gosub('7', 3)"
            ProductCategory.CANDY -> "gosub('8', 4)"

            ProductCategory.ICE_CREAM -> "gosub('9', 2)"

            ProductCategory.INSTANT_MEAL -> "gosub('12', 2)"
            ProductCategory.MUNCHIES -> "gosub('10', 3)"
            ProductCategory.INGREDIENT -> "gosub('11', 4)"

            ProductCategory.DRINK -> "gosub('13', 2)"
            ProductCategory.ICED_DRINK -> "gosub('14', 3)"
            ProductCategory.DIARY -> "gosub('15', 4)"

            ProductCategory.UNKNOWN -> ""
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
                    println("더 보기 버튼이 없음")
                }
            } catch (e: Exception) {
                exceptionCount++
                println("retry... $exceptionCount")
                if (exceptionCount >= 5) {
                    println("알 수 없는 에러. ${e.message}")
                }
            }
            Thread.sleep(1000)
        }
    }

    private fun collectProductItems(category: ProductCategory): List<ProductCollectorDto> {
        val pattern = Regex("\\b\\d+\\b")

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0))
        return driver.findElements(By.cssSelector("#dataTable > div.prodListWrap"))
            .flatMap { wrapper ->
                wrapper.findElements(By.cssSelector("ul > li"))
                    .map {
                        ProductCollectorDto(
                            it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.name > p")).text,
                            it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_text > div.price")).text.replace(Regex("\\D+"), "").toIntOrNull() ?: 0,
                            it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img")).getAttribute("src"),
                            ProductEventType.parse(it.findElement(By.cssSelector("div.prod_item > div.badge")).text.trim()),
                            it.findElement(By.cssSelector("div.prod_item > div.tag")).findElements(By.cssSelector("span.new")).isNotEmpty(),
                            category,
                            pattern.find(it.findElement(By.cssSelector("div.prod_item > div.prod_wrap > div.prod_img > img")).getAttribute("src"))?.value,
                        )
                    }
            }
    }
}
