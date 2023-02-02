package com.example.nwtktsapi.e2e;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class LoginTestBase {

	public static WebDriver driver;
	
	@BeforeSuite
	public void initializeWebDriver() {
		TestNG testng = new TestNG();
		testng.setVerbose(0);
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@AfterSuite
	public void quitDriver() {
		driver.quit();
	}
}
