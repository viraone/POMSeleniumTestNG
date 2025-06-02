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
     *
     * This test verifies the standard successful login flow using known-good credentials.
     * It checks both the presence of a feedback message and that its content confirms secure access.
     *
     * Preconditions:
     * - The login page must be reachable at the expected URL.
     * -
     * Steps:
     * 1. navigate to the login page
     * 2. enter valid credentials and submit the form
     * 3. verify a success banner is displayed
     * 4. confirm the banner contains the expected success message
     *
     * Why this test matters:
     *
     * Example failure reasons:
     *
     * @Test - is TestNG's annotation it allows descriptive reporting via description.
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

    /**
     * Negative Path Test: Invalid username with valid password should trigger an error message.
     *
     * This confirms that the login system does not accept unknown users.
     */
    @Test(description = "Should show error message for incorrect username")
    public void invalidUsernameTest(){

    }


}
