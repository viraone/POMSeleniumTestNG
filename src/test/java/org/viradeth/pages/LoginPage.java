package org.viradeth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object model (POM) for the login page
 * This class encapsulates:
 * - The locators used to interact with the login UI
 * - The behaviors supported on this page: navigation, input, form submission
 * This abstraction separates page logic from test logic.  Improving reusability and test maintainability
 */

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Element locators - defined using By strategy for ease of refactoring and readability
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button.radius");
    private By errorMsg = By.id("flash"); // Used for both success and error messages

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
     * Navigates to the browser to the login page URL
     * This method is typically called in test setup or page chaining
     * Keeping the URL inside the Page Object allows for central management if endpoints change.
     */
    public void goToLoginPage(){
        driver.get("https://the-internet.herokuapp.com/login");
    }

    /**
     * Fills in the login form and submits it
     *
     * @param username - the username to enter
     * @param password - the password to enter
     *
     * Represents a high level user interaction with the page.
     * Tests should call this instead of interacting with raw elements.
     */
    public void login(String username, String password){
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    /**
     * Checks whether a login message (success or error) is visible
     * This can be used in assertions to confirm a feedback banner is shown.
     */
    public boolean isLoginMessageDisplayed(){
        try {
            wait.until(driver -> driver.findElement(errorMsg).isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves the text content of the login message banner
     *
     * Useful for verifying the message is displayed after login attempt
     * Could be extended in the future to return message type - error / success.
     * Using regex or styling
     */
    public String getLoginMessage(){
        return wait.until(driver -> driver.findElement(errorMsg).getText());
    }
}
