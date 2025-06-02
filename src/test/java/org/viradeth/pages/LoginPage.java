package org.viradeth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page object model (POM) for the login page
 * This class encapsulates:
 * - The locators used to interact with the login UI
 * - The behaviors supported on this page: navigation, input, form submission
 * This abstraction separates page logic from test logic.  Improving reusability and test maintainability
 */

public class LoginPage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private WebDriver driver;

    private WebDriverWait wait;

    // Element locators - defined using By strategy for ease of refactoring and readability
    // Login form input field - stable and unique ID used
    private By usernameField = By.id("username");

    // Password input - Stable ID
    private By passwordField = By.id("password");

    // Login button - only has a class, so we rely on CSS class
    private By loginButton = By.cssSelector("button.radius");

    // Feedback banner - stable ID used
    // Use for both success and error messages
    private By loginErrorBanner = By.id("flash");

    /**
     * Constructor for LoginPage
     *
     * @param driver the WebDriver instance controlling the browser
     * @param wait the WebDriverWait used to manage dynamic waits
     *
     * This pattern follows constructor injection for easier testing and flexibility.
     * All page classes receive their driver/wait objects from BaseTest or DI frameworks.
     */
    public LoginPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Navigates the browser to the login page url
     *
     * This method should be balled at the start of any login related test case or
     * before performing any interaction with the login page
     * It ensure the WebDriver is pointing to the correct endpoint before continuing.
     *
     * URL is hardcoded here for simplicity, but in production framework it should be
     * externalized to a configuration file or environment variable.
     *
     * Example usage:
     * <pre>
     *     LoginPage loginPage = new LoginPage(driver, wait);
     *     loginPage.goToLoginPage();
     * </pre>
     *
     * Why this matters:
     * - Centralizes page navigation logic in the POM class
     * - Reduces duplication of hardcoded URLS in test classes
     * - Improves maintainability if login endpoint changes
     * - Clarifies page ownership in multi-page flows
     */
    public void goToLoginPage(){
        driver.get("https://the-internet.herokuapp.com/login");
    }

    /**
     * Logs into the application using the specified username and password.
     *
     * This method abstracts the low level details of entering credentials and clicking
     * the login button. It should be the preferred method for test cases that perform automation
     * Ensuring consistency and maintainability
     *
     * Example usage:
     * <pre>
     *     LoginPage loginPage = new LoginPage(driver, wait);
     *     loginPage.goToLoginPage();
     *     loginPage.login("tomsmith", "SuperSecretPassword!");
     * </pre>
     *
     * @param username - the username to enter
     * @param password - the password to enter
     *
     * Best practices:
     * - Always call {@code goToLoginPage()} before invoking this method unless the browser is already on the login page.
     * - Avoid duplicating low-level field interactions in test classes; use this method instead.
     *
     * Known limitations:
     * - Assumes the login form is present and visible. This method does not validate page readiness; use explicit waits in tests if needed.
     */
    public void login(String username, String password){
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    /**
     * Determines whether a login feedback message (either success or error) is currently visible on the page.
     * This method is typically used in test assertions to verify that the application responded
     * to a login attempt - either by displaying a successful banner or an error message.
     *
     * Example usage:
     * <pre>
     *     assertTrue(loginPage.isLoginMessageDisplayed(), "Expected a login message, but none appeared.");
     * </pre>
     *
     * Logging:
     * - Logs an error with stack track if the message does not appear within the configured wait duration.
     * - Useful for debugging flaky or timing-sensitive login tests.
     *
     * Best practices:
     * - Call this method after invoking {@code login()} to confirm UI feedback.
     * - Combine with {@code getLoginMessage()} to validate the message content.
     *
     * Known limitations:
     * - Does not differentiate between success and error banners - only checks visibility.
     * - Assumes the browser is already on the login page and has submitted a login attempt.
     *
     * @return true if a login message banner is visible; false if it times out or fails to appear.
     */
    public boolean isLoginMessageDisplayed(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorBanner));
            return true;
        } catch (TimeoutException e) {
            logger.error("Login message did not appear within wait duration.", e);
            return false;
        }
    }

    /**
     * Retrieves the text content of the login message banner
     *
     * @return - The message string shown in the login alert banner
     * @throws - TimeoutException if the banner does not appear within the wait duration.
     *
     * Why this matters:
     * Ensures login feedback is captured for validation.
     * Logs message content for debugging in CI environments.
     * Promotes stability by using explicit wait conditions.
     */
    public String getLoginMessage(){
        try {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorBanner));
            String messageText = messageElement.getText().trim();

            logger.info("Login message appeared: '{}'", messageText);
            return messageText;
        } catch (TimeoutException e) {
            logger.error("Login message banner did not appear within the wait duration.", e);
            throw e;
        }
    }
}
