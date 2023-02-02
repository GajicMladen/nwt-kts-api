package com.example.nwtktsapi.e2e;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
properties = {"spring.config.name=application-test"})
@ActiveProfiles("test")
public class LoginTest extends LoginTestBase{

	@Test
	public void shouldFailLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.filloutForm("fail@mail.com", "testpass");
		String err = loginPage.clickLoginButtonErr();
		String expected = "Neispravan e-mail ili lozinka!";
		assertEquals(err, expected);
	}
	
	@Test
	public void shouldSucceedLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.filloutForm("p3r5kul45@gmail.com", "testpass");
		loginPage.clickLoginButton();
		HomePage homePage = new HomePage(driver);
		homePage.clickBurgerIcon();
		String title = homePage.getDrawerTitle();
		String expectedStart = "Dobrodošao";
		assertTrue(title.startsWith(expectedStart));
	}
}
