package org.viradeth.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.viradeth.base.BaseTest;
import org.viradeth.pages.LoginPage;
import static org.testng.Assert.*;
import static org.viradeth.tests.TestMessages.*;

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
     * - Serves as a baseline for all login-related behavior
     *
     * Example failure reasons:
     *
     * @Test - is TestNG's annotation it allows descriptive reporting via description.
     */
    @Test(description = "Valid credentials should login successfully and show success banner")
    public void isAbleToLoginWithValidCredentials(){

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.goToLoginPage();

        assertTrue(
                loginPage.isAtLoginPage(),
                LOGIN_PAGE_LOAD_ERROR
        );

        logger.info("Attempting login with username: [{}], password: [****]", validUserName);

        // Act
        loginPage.login(validUserName, validPassword);

        // Assert
        assertTrue(
                loginPage.isLoginMessageDisplayed(),
                LOGIN_SUCCESS_MESSAGE_MISSING
        );

        String actualMessage = loginPage.getLoginMessage().replace("\n", "").trim();
        assertTrue(
                actualMessage.contains(expectedSuccessMessage),
                LOGIN_SUCCESS_TEXT_INVALID
        );
    }

}
