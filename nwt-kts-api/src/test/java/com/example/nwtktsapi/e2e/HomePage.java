package com.example.nwtktsapi.e2e;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	
	@FindBy(css="app-burger-menu-icon")
	WebElement burgerMenu;
	
	@FindBy(css=".drawer-title")
	WebElement drawerTitle;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickBurgerIcon() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Actions actions = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(burgerMenu));
		actions.click(burgerMenu).perform();
	}
	
	public String getDrawerTitle() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(drawerTitle));
		return drawerTitle.getText();
	}
}
