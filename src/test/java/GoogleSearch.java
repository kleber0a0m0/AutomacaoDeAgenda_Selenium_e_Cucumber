import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleSearch {

    @Test
    public void searchAdaTech() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
        try {
            driver.get("https://www.google.com");
            driver.findElement(By.cssSelector("[name='q']")).sendKeys("Ada tech");

            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(
                            ExpectedConditions.elementToBeClickable(By.cssSelector("[name='btnK']"))
                    ).click();
        } finally {
            driver.quit();
        }
    }

}
