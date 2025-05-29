package org.viradeth.tests;

import org.testng.annotations.Test;
import org.viradeth.base.BaseTest;
import org.viradeth.pages.LoginPage;
import static org.testng.Assert.*;

/**
 * LoginTest contains regression scenarios for authenticating against
 * https://the-internet.herokuapp.com/login - using the Page Object Model
 *
 * This class demonstrates:
 * Inheriting WebDriver setup from BaseTest
 * Encapsulation via LoginPage abstraction
 * Assertion strategies using TestNG
 * Positive and negative path validations
 */

public class LoginTest extends BaseTest {
    /**
     * Positive Test: Valid credentials should produce a success banner
     * This test simulates a valid login using known credentials
     * and asserts that a success message is visible and contains expected confirmation text.
     *
     * TestNG's @Test annotation allows descriptive reporting via description.
     */
    @Test(description = "Should login successfully with valid credentials")
    public void validLoginTest(){
        // Arrange: Create LoginPage instance and navigate to the target login page
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.goToLoginPage();

        // Act: Perform login using valid test credentials
        loginPage.login("tomsmith", "SuperSecretPassword!");

        // Assert: Ensure success message is displayed and contact is correct
        assertTrue(
                loginPage.isLoginMessageDisplayed(),
                "Expected a login success message, but none appeared."
        );

        assertTrue(
                loginPage.getLoginMessage().contains("You logged into a secure area!"),
                "Success message content was incorrect or missing."
        );
    }
}
