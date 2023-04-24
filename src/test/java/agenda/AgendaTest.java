package agenda;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaTest {

    private static WebDriver driver;
    private static String name;
    private static String userName;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );

        name = RandomStringUtils.randomAlphabetic(15);
        userName = RandomStringUtils.randomAlphabetic(10);
    }

//    @AfterAll
//    public static void destroy() {
//        driver.quit();
//    }


    @Test
    @Order(1)
    public void cadastrarUmUsuario() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.className("create")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(RandomStringUtils.randomNumeric(1));
        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='" + name + "']")
        );
        assertNotNull(element);
    }
    @Test
    @Order(2)
    public void listarUsuariosCadastrados_encontraruserName() {
        driver.get("http://localhost:8080/app/users");
        WebElement element = driver.findElement(
                By.xpath("//td[text()='"+name+"']\n")
        );
        assertNotNull(element);
    }
    @Test
    @Order(3)
    public void editarUmFilmeJaCadastrado() {
        driver.get("http://localhost:8080/app/users");

        driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/a[@class='edit']")).click();

        name = RandomStringUtils.randomAlphabetic(20);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(name);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(RandomStringUtils.randomNumeric(1));

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/button")).click();

        WebElement movieElement = driver.findElement(
                By.xpath("//td[text()='"+name+"']\n")
        );
        assertNotNull(movieElement);
    }

    @Test
    @Order(4)
    public void cadastrarUsuarioComMesmoUsername_naoPermitido() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.className("create")).click();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(RandomStringUtils.randomNumeric(1));
        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/button")).click();

        WebElement error = driver.findElement(By.className("user-form-error"));

        assertEquals("Username already in use",error.getText());
    }

    @Test
    @Order(5)
    public void cadastrarUsuarioComNameUsernamePasswordNull_naoPermitido() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.className("create")).click();
        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/button")).click();

        List<WebElement> errorList = driver.findElements(By.className("user-form-error"));


        assertEquals("não deve estar em branco",errorList.get(0).getText());
        assertEquals("não deve estar em branco",errorList.get(1).getText());
        assertEquals("não deve estar em branco",errorList.get(2).getText());

    }

    @Test
    @Order(6)
    public void verificarCampoSenhaEscondendoSenha() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.className("create")).click();

        String senha = RandomStringUtils.randomAlphanumeric(8);
        driver.findElement(By.id("password")).sendKeys(senha);

        WebElement senhaInput = driver.findElement(By.id("password"));
        assertEquals("password", senhaInput.getAttribute("type"));

        String novaSenha = RandomStringUtils.randomAlphanumeric(10);
        senhaInput.clear();
        senhaInput.sendKeys(novaSenha);

        assertEquals(novaSenha, senhaInput.getAttribute("value"));
    }

    @Test
    @Order(7)
    public void editarUsuario_senhaAntigaNaoDeveSerCarregada() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/a[@class='edit']")).click();
        WebElement senhaInput = driver.findElement(By.id("password"));
        assertEquals("",senhaInput.getText());
    }

    @Test
    @Order(8)
    public void verificarCampoUsernameReadOnly() {
        driver.get("http://localhost:8080/app/users");
        driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/a[@class='edit']")).click();
        WebElement usernameField = driver.findElement(By.id("username"));
        System.out.println((usernameField.getAttribute("readonly")));
        assertTrue(usernameField.getAttribute("readonly").equals("true"));
    }



}
