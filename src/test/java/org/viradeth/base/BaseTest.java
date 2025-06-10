package org.viradeth.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseTest is a reusable setup/teardown class that defines the WebDriver lifecycle
 * for all TestNG-based classes that extend it
 *
 * Responsibilities:
 * - Initialize and configure WebDriver(via WebDriverManager)
 * - Set up WebDriverWait for explicit waits
 * - Clean up browser sessions after each test
 *
 * Why this matters:
 * Centralizing browser management improves maintainability, eliminates duplicate setup code
 * and ensures test environment consistency across suites
 * Enables CI/CD and multi-environment configuration via property injection
 *
 * This structure os foundational for scalable test automation frameworks
 */
public abstract class BaseTest {

    // Logger instance for capturing structured log output
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Shared WebDriver instance for child test classes
    // Protected modifier allows subclass access without exposing it globally
    protected WebDriver driver;

    // WebDriver instance for use in page classes or dynamic test logic
    protected WebDriverWait wait;

    // Test data loaded from properties
    protected String validUserName;
    protected String validPassword;
    protected String invalidUsername;
    protected String invalidPassword;

    protected String expectedSuccessMessage;
    protected String expectedInvalidUsernameMessage;
    protected String expectedInvalidPasswordMessage;

    // Loads key-value pairs from testdata.properties at runtime
    // Used to inject credentials, expected messages and other environment-specific test data
    // Allow configuration to be externalized for maintainability and CI/CD flexibility.
    private final Properties testData = new Properties();


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

       // Load credentials and messages from .properties
       loadTestData();
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

    /**
     * Loads test data from an external properties file.
     * Support credential injection and message customization across environments.
     */
    private void loadTestData(){
        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata.properties")){
            testData.load(fis);

        // Assign loaded values to test data fields
        validUserName = testData.getProperty("valid.username");
        validPassword = testData.getProperty("valid.password");
        invalidUsername = testData.getProperty("invalid.username");
        invalidPassword = testData.getProperty("invalid.password");

        expectedSuccessMessage = testData.getProperty("expected.success.message");
        expectedInvalidUsernameMessage = testData.getProperty("expected.invalid.username.message");
        expectedInvalidPasswordMessage = testData.getProperty("expected.invalid.password.message");

    } catch (IOException e){
            throw new RuntimeException("Failed to load test data from testdata.properties", e);
        }
    }
}
