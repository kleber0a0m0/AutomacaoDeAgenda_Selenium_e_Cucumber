package br.ada.crudmovie;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest {

    private static WebDriver driver;
    private static String movieTitle;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
        movieTitle = RandomStringUtils.randomAlphabetic(15);
    }

    @AfterAll
    public static void destroy() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void cadatrarUmFilme() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("title")).sendKeys(movieTitle);
        driver.findElement(By.id("genre")).sendKeys(RandomStringUtils.randomAlphabetic(10));
        driver.findElement(By.id("rating")).sendKeys(RandomStringUtils.randomNumeric(1));
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='" + movieTitle + "']")
        );
        assertNotNull(element);
    }

    @Test
    public void cadatrarUmFilmeSemGenero_deveTerSucesso() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        String title = RandomStringUtils.randomAlphabetic(15);
        driver.findElement(By.id("title")).sendKeys(title);
        driver.findElement(By.id("rating")).sendKeys(RandomStringUtils.randomNumeric(1));
        driver.findElement(By.xpath("//form/button")).click();
        WebElement element = driver.findElement(
                By.xpath("//*[text()='" + title + "']")
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

    @Test
    public void cadastrarFilmeComTituloNull_naoPermitido() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("genre")).sendKeys("Comédia");
        driver.findElement(By.id("rating")).sendKeys("10");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("movie-form-error")
        );
        assertNotNull(element);
        assertEquals("must not be blank", element.getText());
    }

    @Test
    public void cadastrarFilmeComNotaMaiorQue10_naoPermitido() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("title")).sendKeys("Uma comédia");
        driver.findElement(By.id("genre")).sendKeys("Comédia");
        driver.findElement(By.id("rating")).sendKeys("11");
        driver.findElement(By.xpath("//form/button")).click();

        String message = driver.findElement(By.id("rating")).getAttribute("validationMessage");
        assertEquals("Value must be less than or equal to 10.", message);
    }

    @Test
    @Order(2)
    public void listarFilmesCadastrados_encontrarSeleniumTest() {
        driver.get("http://localhost:8080/app/movies");

        WebElement element = driver.findElement(
                By.xpath("//*[text()='" + movieTitle + "']")
        );
        assertNotNull(element);
    }

    @Test
    @Order(3)
    public void editarUmFilmeJaCadastrado() {
        driver.get("http://localhost:8080/app/movies");

        WebElement editElement = driver.findElement(
                By.xpath("//td[text()='" + movieTitle + "']/following-sibling::td/a[text()='Edit']")
        );
        editElement.click();

        movieTitle = RandomStringUtils.randomAlphabetic(20);
        WebElement titleElement = driver.findElement(By.id("title"));
        titleElement.clear();
        titleElement.sendKeys(movieTitle);
        driver.findElement(By.xpath("//form/button")).click();

        WebElement movieElement = driver.findElement(
                By.xpath("//td[text()='" + movieTitle + "']")
        );
        assertNotNull(movieElement);
    }

    @Test
    @Order(4)
    public void deletarUmFilmeCadastrado() {
        driver.get("http://localhost:8080/app/movies");

        WebElement deleteElement = driver.findElement(
                By.xpath("//td[text()='" + movieTitle + "']/following-sibling::td/a[text()='Delete']")
        );
        deleteElement.click();

        assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.xpath("//td[text()='" + movieTitle + "']"))
        );
    }

}
