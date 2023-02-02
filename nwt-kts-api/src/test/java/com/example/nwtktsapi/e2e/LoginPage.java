package com.example.nwtktsapi.e2e;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	private static final String WEB_URL = "http://localhost:4200/login";
	
	@FindBy(name="email")
	private WebElement emailField;
	
	@FindBy(name="password")
	private WebElement passwordField;
	
	@FindBy(css="#login-button")
	private WebElement loginButton;
	
	@FindBy(css="p.message-paragraph")
	private WebElement errMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		driver.get(WEB_URL);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void filloutForm(String email, String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(emailField));
		System.out.println(emailField);
		emailField.sendKeys(email);
		
		wait.until(ExpectedConditions.visibilityOf(passwordField));
		passwordField.sendKeys(password);
	}
	
	public String clickLoginButtonErr() {
		clickLoginButton();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(errMessage));
		return errMessage.getText();
	}
	
	public void clickLoginButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(loginButton));
		Actions actions = new Actions(driver);
		actions.click(loginButton).perform();
	}
	
}
