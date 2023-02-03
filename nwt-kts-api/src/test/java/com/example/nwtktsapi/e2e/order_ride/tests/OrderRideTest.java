package com.example.nwtktsapi.e2e.order_ride.tests;

import com.example.nwtktsapi.e2e.LoginPage;
import com.example.nwtktsapi.e2e.order_ride.pages.ClientHomePage;
import com.example.nwtktsapi.e2e.order_ride.pages.DriverHomePage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrderRideTest extends OrderRideTestBase {

    @Test
    public void shouldOrderRide() {
        LoginPage loginPage = new LoginPage(driver_chrome);
        loginPage.filloutForm("c", "testpass");
        loginPage.clickLoginButton();

        ClientHomePage clientHomePage = new ClientHomePage(driver_chrome);
        clientHomePage.filloutForm("Dragise Brasovana 14a", "Djordja Jovanovica 7");

        loginPage = new LoginPage(driver_edge);
        loginPage.filloutForm("p3r5kul45@gmail.com", "testpass");
        loginPage.clickLoginButton();

        DriverHomePage driverHomePage = new DriverHomePage(driver_edge);
        driverHomePage.setDriverAvailable();

        clientHomePage.clickOrderRide();
        driverHomePage.acceptRide();
        String result = clientHomePage.acceptRide();
        driverHomePage.endRide();
        clientHomePage.logout();
        driverHomePage.logout();
        System.out.println(result+"**********************");
        assertEquals(result, "Pronašli smo vozača");
    }


    @Test
    public void noAvailableDriver() {
        LoginPage loginPage = new LoginPage(driver_chrome);
        loginPage.filloutForm("c", "testpass");
        loginPage.clickLoginButton();
        ClientHomePage clientHomePage = new ClientHomePage(driver_chrome);
        clientHomePage.filloutForm("Dragise Brasovana 14a", "Djordja Jovanovica 7");

        loginPage = new LoginPage(driver_edge);
        loginPage.filloutForm("p3r5kul45@gmail.com", "testpass");
        loginPage.clickLoginButton();
        DriverHomePage driverHomePage = new DriverHomePage(driver_edge);
        driverHomePage.setDriverUnavailable();

        String result = clientHomePage.clickOrderRideWithMessage();
        clientHomePage.logout();
        driverHomePage.logout();
        assertEquals(result, "Nažalost trenutno nema slobodnih vozača!");
    }

    @Test
    public void noTokensForRide() {
        LoginPage loginPage = new LoginPage(driver_chrome);
        loginPage.filloutForm("djordjejovanovic27@gmail.com", "testpass");
        loginPage.clickLoginButton();
        ClientHomePage clientHomePage = new ClientHomePage(driver_chrome);
        clientHomePage.filloutForm("Dragise Brasovana 14a", "Djordja Jovanovica 7");

        loginPage = new LoginPage(driver_edge);
        loginPage.filloutForm("p3r5kul45@gmail.com", "testpass");
        loginPage.clickLoginButton();
        DriverHomePage driverHomePage = new DriverHomePage(driver_edge);
        driverHomePage.setDriverAvailable();

        String result = clientHomePage.clickOrderRideWithMessage();
        clientHomePage.logout();
        driverHomePage.logout();
        assertEquals(result, "Nemate dovoljno tokena");
    }

    @Test
    public void formInvalid() {
        LoginPage loginPage = new LoginPage(driver_chrome);
        loginPage.filloutForm("c", "testpass");
        loginPage.clickLoginButton();
        ClientHomePage clientHomePage = new ClientHomePage(driver_chrome);

        loginPage = new LoginPage(driver_edge);
        loginPage.filloutForm("p3r5kul45@gmail.com", "testpass");
        loginPage.clickLoginButton();
        DriverHomePage driverHomePage = new DriverHomePage(driver_edge);
        driverHomePage.setDriverAvailable();

        String result = clientHomePage.clickOrderRideWithMessage();
        clientHomePage.logout();
        driverHomePage.logout();
        assertEquals(result, "Niste zadali početnu i krajnju lokaciju!");
    }
}
