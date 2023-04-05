package br.ada.crudmovie;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MovieTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
    }

    @Test
    public void cadatrarUmFilme() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

    }

}
