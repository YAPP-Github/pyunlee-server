import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CUCrawlingTest {

    public static void main(String[] args) {
        String webdriverPath = Paths.get("cvs-batch/src/main/resources/webdriver/mac-chromedriver").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", webdriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        cuParser(driver);

        driver.quit();
    }

    public static void cuParser(WebDriver driver) {
        // 도시락
        driver.get("https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1");

        int tableIndex = 1;
        int productIndex = 1;
        String prodcvs = "CU";
        List<String[]> prod_list = new ArrayList<>();

        while (true) {
            String[] prod_input = new String[5];

            try {
                System.out.println(productIndex + "번째 상품 파싱");
                String baseElement = String.format("#dataTable > div.prodListWrap:nth-of-type(%s) > ul > li:nth-child(%s) > div.prod_item", tableIndex, productIndex);

                prod_input[0] = driver.findElement(By.cssSelector(baseElement + " > div.prod_wrap > div.prod_text > div.name > p")).getText();
                prod_input[1] = driver.findElement(By.cssSelector(baseElement + " > div.prod_wrap > div.prod_text > div.price > strong")).getText();
                WebElement imgElement = driver.findElement(By.cssSelector(baseElement + "> div.prod_wrap > div.prod_img > img"));
                prod_input[2] = imgElement.getAttribute("src");
                prod_input[3] = driver.findElement(By.cssSelector(baseElement + "> div.badge")).getText();
                prod_input[4] = driver.findElement(By.cssSelector(baseElement + "> div.tag")) .getText(); //todo : 존재하면 true 반환하도록

                productIndex++;
                if (productIndex == 40) {
                    productIndex = 1;
                    tableIndex++;
                }

                prod_input[1] = prod_input[1].replace(",", "");
                prod_input[1] = prod_input[1].replace("원", "");

                prod_list.add(prod_input);
            } catch (NoSuchElementException e) {
                System.out.println("End of CU Crawling");
                break;
            }
        }

        // 상품 정보 출력
        for (String[] prod : prod_list) {
            System.out.println("상품 이름: " + prod[0]);
            System.out.println("상품 가격: " + prod[1]);
            System.out.println("상품 이미지 URL: " + prod[2]);
            System.out.println("행사 정보: " + prod[3]);
            System.out.println("신제품 정보: " + prod[4]);
            System.out.println("==================================");
        }
    }

    private void clickShowMoreButton(){}
}
