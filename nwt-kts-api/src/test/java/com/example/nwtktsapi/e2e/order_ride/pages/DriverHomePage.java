package com.example.nwtktsapi.e2e.order_ride.pages;

import com.beust.ah.A;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverHomePage {

    WebDriver driver;

    @FindBy(css = "mat-slide-toggle")
    private WebElement driverStatusToggle;

    @FindBy(name = "accept-ride")
    private WebElement acceptRideButton;

    @FindBy(name="logout")
    private WebElement logoutButton;

    @FindBy(name="end-ride")
    private WebElement endRide;

    public DriverHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setDriverAvailable() {
        if (!driverStatusToggle.getText().equals("Dostupan")) {
            Actions actions = new Actions(driver);
            actions.click(driverStatusToggle).perform();
        }
    }

    public void setDriverUnavailable() {
        if (driverStatusToggle.getText().equals("Dostupan")) {
            Actions actions = new Actions(driver);
            actions.click(driverStatusToggle).perform();
        }
    }

    public void acceptRide() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(acceptRideButton));
        Actions actions = new Actions((driver));
        actions.click(acceptRideButton).perform();
    }

    public void endRide() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(endRide));
        Actions actions = new Actions((driver));
        actions.click(endRide).perform();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        Actions actions = new Actions((driver));
        actions.click(logoutButton).perform();
    }
}
