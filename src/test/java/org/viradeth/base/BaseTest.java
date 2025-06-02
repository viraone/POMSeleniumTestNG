package org.viradeth.base;

// The WebDrivers and WebDriverWait for the browser control
// Core selenium classes for browser automation
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// WebDriver manager that auto-manages downloading the correct browser driver binaries
import io.github.bonigarcia.wdm.WebDriverManager;

// TestNG annotations for lifecycle control (before/after each test method)
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * BaseTest is a reusable setup/teardown class that defines the WebDriver lifecycle
 * for all TestNG-based classes that extend it
 *
 * Responsibilities:
 * - Initialize and configure WebDriver(via WebDriverManager)
 * - Set up WebDriverWait for explicit waits
 * - Clean up browser sessions after each test
 *
 * - Why this matters:
 * Centralizing browser management improves maintainability, eliminates duplicate setup code
 * and ensures test environment consistency across suites
 *
 * This structure os foundational for scalable test automation frameworks
 */
public class BaseTest {

    // Shared WebDriver instance for child test classes
    // Protected modifier allows subclass access without exposing it globally
    protected WebDriver driver;

    // WebDriver instance for use in page classes or dynamic test logic
    protected WebDriverWait wait;

   /**
     * @BeforeMethod - is a TestNG lifecycle hook
     * It runs before each @Test method execution
     *
     * Responsibilities:
    * Set up ChromeDriver via WebDriverManager (auto-handles driver binaries)
    * Instantiate WebDriver
    * Apply a 10-second explicit wait wrapper
    * Maximize the browser window for visual consistency
    *
    * Why this matters:
    * Ensure each test runs in a clean, fresh browser instance to avoid flaky behavior
    * Build to be extended later with cross-browser or grid configurations.
     */
   @BeforeMethod
    public void setUp(){
       WebDriverManager.chromedriver().setup(); // Resolves, downloads, and configures ChromeDriver
       driver = new ChromeDriver(); // Starts new Chrome browser session
       wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Sets up reusable dynamic waits
       driver.manage().window().maximize();
    }

    /**
     * @AfterMethod is a TestNG lifecycle hook
     * It runs after each @Test method execution
     *
     * Responsibilities:
     * - Quit the browser
     * - Clean up WebDriver session and memory
     *
     * Why this matters:
     * Prevents test interdependency and resource leakage across tests.
     * Encourages test isolation - a core principle in automation.
     */
    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();  // Gracefully close the web browser session
        }
    }
}
