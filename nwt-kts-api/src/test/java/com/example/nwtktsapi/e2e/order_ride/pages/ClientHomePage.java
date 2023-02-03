package com.example.nwtktsapi.e2e.order_ride.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClientHomePage {

    WebDriver driver;
    @FindBy(name="start")
    private WebElement startField;
    @FindBy(name="start-search")
    private WebElement startSearchButton;
    @FindBy(name="end")
    private WebElement endField;
    @FindBy(name="end-search")
    private WebElement endSearchButton;
    @FindBy(name="order-ride")
    private WebElement orderRideButton;

    @FindBy(id="scrollable-content")
    private WebElement scrollableForm;

    @FindBy(name = "accept-ride")
    private WebElement acceptRideButton;

    @FindBy(css="p.message-paragraph")
    private WebElement message;

    @FindBy(name="logout")
    private WebElement logoutButton;

    public ClientHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void filloutForm(String start, String end) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(startField));
        startField.sendKeys(start);
        wait.until(ExpectedConditions.visibilityOf(startSearchButton));
        Actions actions = new Actions(driver);
        actions.click(startSearchButton).perform();

        wait.until(ExpectedConditions.visibilityOf(endField));
        endField.sendKeys(end);
        wait.until(ExpectedConditions.visibilityOf(endSearchButton));
        actions = new Actions(driver);
        actions.click(endSearchButton).perform();
    }

    public String clickOrderRideWithMessage() {
        clickOrderRide();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(message));
        return message.getText();
    }

    public void clickOrderRide() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 100", scrollableForm);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(orderRideButton));
        Actions actions = new Actions((driver));
        actions.click(orderRideButton).perform();
    }

    public String acceptRide() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(acceptRideButton));
        Actions actions = new Actions((driver));
        actions.click(acceptRideButton).perform();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(message));
        return message.getText();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        Actions actions = new Actions((driver));
        actions.click(logoutButton).perform();
    }
}
