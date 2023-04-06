package br.ada.crudmovie;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovieTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
    }

    @AfterEach
    public void destroy() {
        driver.quit();
    }

    @Test
    public void cadatrarUmFilme() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("title")).sendKeys("O auto da compadecida");
        driver.findElement(By.id("genre")).sendKeys("Comédia");
        driver.findElement(By.id("rating")).sendKeys("10");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='O auto da compadecida']")
        );
        assertNotNull(element);
    }

    @Test
    public void cadastrarFilmeComNotaNula_naoPermitido() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("title")).sendKeys("O auto da compadecida");
        driver.findElement(By.id("genre")).sendKeys("Comédia");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("movie-form-error")
        );
        assertNotNull(element);
        assertEquals("must not be null", element.getText());
    }

}
