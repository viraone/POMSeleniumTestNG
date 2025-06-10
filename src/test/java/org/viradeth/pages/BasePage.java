package org.viradeth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * BasePage: Common foundation for all Page Object in the automation framework.
 * <p>
 * This class centralizes reusable UI interaction logic such as clicking, typing, visibility checks, and element text retrieval.
 * <p>
 * Why it exists:
 * Avoids duplicate Selenium logic in every page class
 * Enforces consistent wait strategies across the suite
 * Makes tests more readable and less error-prone
 * <p>
 * Usage:
 * All page objects (e.g., LoginPage, DashboardPage) should extend BasePage
 * <p>
 * Note:
 * Do not add test logic here - keep this class focused on browser interaction only.
 */

public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Constructor to initialize browser context and wait strategy
     *
     * @param driver - The WebDriver instance controlling the browser
     * @param wait   - a WebDriverWait object for managing dynamic waits
     */
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Clicks on a visible and clickable element located by the given locator.
     *
     * @param - locator The By locator for the element to click
     */
    protected void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked element: {}", locator.toString());

        } catch (Exception e) {
            logger.error("Failed to click on element: {}", locator.toString(), e);
            throw e;
        }
    }
    /**
     * Types the given string into a visible input element
     *
     * @param - locator - The By locator of the input field
     * @param - value - The string to send to the field
     */
    protected void type(By locator, String value){
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(value);
            logger.info("Typed '{}' into element: {}", value, locator.toString());
        } catch (Exception e) {
            logger.error("Failed to type into element: {}", locator.toString(), e);
            throw e;
        }
    }

    /**
     * Retrieves the visible text content of an element.
     *
     * @param locator The By locator of the element
     * @return The visible string content
     */
    protected String getText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText().trim();
            logger.info("Retrieved text '{}' from element: {}", text, locator.toString());
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator.toString(), e);
            throw e;
        }
    }

    /**
     * Checks whether the specified element is currently visible on the page
     *
     * This acts as a safe visibility check without throwing unhandled exceptions.
     *
     * @param - locator - The By locator of the element
     * @param - true - if the element is visible within the wait duration; false otherwise
     */
    protected boolean isVisible(By locator){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element is visible: {}", locator.toString());
            return true;
        } catch (Exception e) {
            logger.warn("Element not visible (timeout): {}", locator.toString());
            return false;
        }
    }
}
