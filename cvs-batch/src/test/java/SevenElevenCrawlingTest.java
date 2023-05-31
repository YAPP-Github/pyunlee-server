import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SevenElevenCrawlingTest {

    public static void main(String[] args) {
        String webdriverPath = Paths.get("cvs-batch/src/main/resources/webdriver/mac-chromedriver").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", webdriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        sevenElevenParser(driver);

        driver.quit();
    }

    public static void sevenElevenParser(WebDriver driver) {
        driver.get("https://www.7-eleven.co.kr/product/presentList.asp");

        int productIndex = 2;
        String prodcvs = "SevenEleven";
        List<String[]> prod_list = new ArrayList<>();

        try {
            // 1+1 tab
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("javascript: fncTab(1)");
            Thread.sleep(1);

            // 상품 조회
            while (true) {
                String[] prod_input = new String[4];

                try {
                    System.out.println(productIndex + "번째 상품 파싱");
                    String baseElement = String.format("#listUl > li:nth-child(%s)", productIndex);

                    prod_input[0] = driver.findElement(By.cssSelector(baseElement + " > div.pic_product > div > div.name")).getText();
                    prod_input[1] = driver.findElement(By.cssSelector(baseElement + " > div.pic_product > div > div.price")).getText();
                    WebElement imgElement = driver.findElement(By.cssSelector(baseElement + " > div.pic_product > img"));
                    prod_input[2] = imgElement.getAttribute("src");
                    prod_input[3] = driver.findElement(By.cssSelector(baseElement + " > ul.tag_list_01")).getText();

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
                    System.out.println("End of SevenEleven Crawling");
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
        } catch (Exception e) {
        } finally {

        }
    }

    private void clickShowMoreButton(){}
}
