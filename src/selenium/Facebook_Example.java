package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Facebook_Example {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_RegisterWithEmptyData() {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("");
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("");
        driver.findElement(By.xpath("//input[@id='password_step_input']")).sendKeys("");

        driver.findElement(By.xpath("//button[@name='websubmit']")).click();
        driver.findElement(By.xpath("//input[@name='firstname']")).click();

        String firstNameXpath = "//div[text()=\"What's your name?\"]";
        if (driver.findElements(By.xpath(firstNameXpath)).size() > 0) {
            Assert.assertTrue(driver.findElement(By.xpath(firstNameXpath)).isDisplayed());
        } else {
            System.out.println("Failed firstname input");
            Assert.fail();
        }

        driver.findElement(By.xpath("//input[@name='reg_email__']")).click();
        String passwordXpath = "//div[text()=\"You'll use this when you log in and if you ever need to reset your password.\"]";
        if (driver.findElements(By.xpath(passwordXpath)).size() > 0) {
            Assert.assertTrue(driver.findElement(By.xpath(passwordXpath)).isDisplayed());
        } else {
            System.out.println("Failed password input");
            Assert.fail();
        }
    }

    @Test
    public void TC_02_RegisterWithInvalidPhoneNumber() {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Luong");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Minh");
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("03488888");
        driver.findElement(By.xpath("//input[@id='password_step_input']")).sendKeys("Test@12345");

        new Select(driver.findElement(By.xpath("//select[@name='birthday_day']"))).selectByValue("12");
        new Select(driver.findElement(By.xpath("//select[@name='birthday_month']"))).selectByValue("2");
        new Select(driver.findElement(By.xpath("//select[@name='birthday_year']"))).selectByValue("2000");
        driver.findElement(By.xpath("//label[text()='Female']/following-sibling::input")).click();

        driver.findElement(By.xpath("//button[@name='websubmit']")).click();
        String phoneXpath = "//div[text()='Please enter a valid mobile number or email address.']";
        if (driver.findElements(By.xpath(phoneXpath)).size() > 0) {
            Assert.assertTrue(driver.findElement(By.xpath(phoneXpath)).isDisplayed());
        } else {
            System.out.println("Failed phone number input");
            Assert.fail();
        }
    }

    @Test
    public void TC_03_LoginFormDisplayed() throws InterruptedException {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Luong");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Minh");
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("0348886789");
        driver.findElement(By.xpath("//input[@id='password_step_input']")).sendKeys("Test@123456");

        new Select(driver.findElement(By.xpath("//select[@name='birthday_day']"))).selectByValue("24");
        new Select(driver.findElement(By.xpath("//select[@name='birthday_month']"))).selectByValue("10");
        new Select(driver.findElement(By.xpath("//select[@name='birthday_year']"))).selectByValue("2000");
        driver.findElement(By.xpath("//label[text()='Female']/following-sibling::input")).click();
        driver.findElement(By.xpath("//button[@name='websubmit']")).click();

        Thread.sleep(15000);
        String loginPageUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(loginPageUrl, "https://www.facebook.com/");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
