package com.example.nwtktsapi.e2e.order_ride.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.TestNG;
import org.testng.annotations.*;

import java.awt.Toolkit;
import java.time.Duration;

public class OrderRideTestBase {

    public static WebDriver driver_chrome;
    public static WebDriver driver_edge;

    @BeforeMethod
    public void initializeWebDriver() {
        TestNG testng = new TestNG();
        testng.setVerbose(0);

        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver_chrome = new ChromeDriver();
        driver_chrome.manage().window().setSize(new Dimension(screenWidth / 2, screenHeight));
        driver_chrome.manage().window().setPosition(new Point(screenWidth / 2, 0));
        driver_chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
        driver_edge = new EdgeDriver();
        driver_edge.manage().window().setSize(new Dimension(screenWidth / 2, screenHeight));
        driver_edge.manage().window().setPosition(new Point(0, 0));
        driver_edge.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void quitDriver() {
        driver_chrome.quit();
        driver_edge.quit();
    }
}
