package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Xpath_CSS_Example {
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
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		driver.findElement(By.xpath("//form[@id='frmLogin']//button")).click();

		Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
		Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
	}

	@Test
	public void TC_02_RegisterWithInvalidEmail() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");

		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("abc");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("da@343@22");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("da@343@22");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0123456789");

		driver.findElement(By.xpath("//form[@id='frmLogin']//button")).click();

		Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}

	@Test
	public void TC_03_RegisterWithIncorrectConfirmEmail() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");

		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("abc");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("minh@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("minh1@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0123456789");

		driver.findElement(By.xpath("//form[@id='frmLogin']//button")).click();

		Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
	}

	@Test
	public void TC_04_RegisterWithPasswordLessThan6Characters() {
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");

		driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("abc");
		driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("minh@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("minh@gmail.com");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123");
		driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123");
		driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0123456789");

		driver.findElement(By.xpath("//form[@id='frmLogin']//button")).click();

		Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
