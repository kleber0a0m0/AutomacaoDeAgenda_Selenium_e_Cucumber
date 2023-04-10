package br.ada.crudmovie;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest {

    private WebDriver driver;
    private String movieTitle = "Selenium test";

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
    @Order(1)
    public void cadatrarUmFilme() {
        driver.get("http://localhost:8080/app/movies");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("title")).sendKeys(movieTitle);
        driver.findElement(By.id("genre")).sendKeys("Horror");
        driver.findElement(By.id("rating")).sendKeys("10");
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

        driver.findElement(By.id("title")).sendKeys("O auto da compadecida");
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
    @Order(2)
    public void editarUmFilmeJaCadastrado() {

    }

}
