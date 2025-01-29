package ru.netology.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void sendFormSuccessfulTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", resultElement.getText().trim());
    }

    @Test
    void incorrectNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов_Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub"));
        assertTrue(actualElement.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualElement.getText().trim());
    }

    @Test
    void nameFieldIsEmptyTest() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id = 'name'].input_invalid .input__sub"));
        assertTrue(actualElement.isDisplayed());
        assertEquals("Поле обязательно для заполнения", actualElement.getText().trim());
    }

    @Test
    void incorrectPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791112345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id = 'phone'].input_invalid .input__sub"));
        assertTrue(actualElement.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualElement.getText().trim());
    }

    @Test
    void phoneFieldIsEmptyTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id = 'phone'].input_invalid .input__sub"));
        assertTrue(actualElement.isDisplayed());
        assertEquals("Поле обязательно для заполнения", actualElement.getText().trim());
    }

    @Test
    void uncheckedCheckboxTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111234567");
        driver.findElement(By.cssSelector("button")).click();
        WebElement actualElement = driver.findElement(By.cssSelector("[data-test-id = 'agreement'].input_invalid .checkbox__text"));
        assertTrue(actualElement.isDisplayed());
    }
}
