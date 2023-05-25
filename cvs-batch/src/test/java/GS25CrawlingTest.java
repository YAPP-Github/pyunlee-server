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

public class GS25CrawlingTest {

    public static void main(String[] args) {
        String webdriverPath = Paths.get("cvs-batch/src/main/resources/webdriver/mac-chromedriver").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", webdriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        gs25Parser(driver);

        driver.quit();
    }

    public static void gs25Parser(WebDriver driver) {
        // 도시락
        driver.get("https://gs25.gsretail.com/gscvs/ko/products/event-goods");

        int productIndex = 1;
        String prodcvs = "GS25";
        List<String[]> prod_list = new ArrayList<>();

        try {
            // 전체 상품 클릭
            driver.findElement(By.cssSelector("#TOTAL")).click();

            // 상품 조회
            while (true) {
                String[] prod_input = new String[4];

                try {
                    System.out.println(productIndex + "번째 상품 파싱");
                    final String baseElement = "#contents > div.cnt > div.cnt_section.mt50 > div > div > div.mt50:nth-of-type(4) > ul.prod_list";

                    prod_input[0] = driver.findElement(By.cssSelector(baseElement + " > li:nth-child(" + productIndex + ") > div.prod_box > p.tit")).getText();
                    prod_input[1] = driver.findElement(By.cssSelector(baseElement + " > li:nth-child(" + productIndex + ") > div.prod_box > p.price > span")).getText();
                    WebElement imgElement = driver.findElement(By.cssSelector(baseElement + " > li:nth-child(" + productIndex + ") > div.prod_box > p.img > img"));
                    prod_input[2] = imgElement.getAttribute("src");
                    prod_input[3] = driver.findElement(By.cssSelector(baseElement + " > li:nth-child(" + productIndex + ") > div.prod_box > div")).getText();

                    productIndex++;
                    if (productIndex == 8) {
                        productIndex = 1;
                        // todo : 다음 버튼 누르기
                        break; //
                    }

                    prod_input[1] = prod_input[1].replace(",", "");
                    prod_input[1] = prod_input[1].replace("원", "");

                    prod_list.add(prod_input);
                } catch (NoSuchElementException e) {
                    System.out.println("End of GS25 Crawling");
                    break;
                }
            }

            // 상품 정보 출력
            for (String[] prod : prod_list) {
                System.out.println("상품 이름: " + prod[0]);
                System.out.println("상품 가격: " + prod[1]);
                System.out.println("상품 이미지 URL: " + prod[2]);
                System.out.println("행사 정보: " + prod[3]);
                System.out.println("==================================");
            }
        }
        finally {

        }
    }

    private void clickShowMoreButton(){}
}
