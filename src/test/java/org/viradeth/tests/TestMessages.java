package org.viradeth.tests;

/**
 * TestMessages is a centralized repository for all assertion strings used across the test suite.
 *
 */

public class TestMessages {

    private TestMessages() {
        // Prevent instantiation — utility class only
    }

    // Login Page Load Assertions
    public static final String LOGIN_PAGE_LOAD_ERROR = "Login page did not load properly.";


    public static final String LOGIN_SUCCESS_MESSAGE_MISSING = "Expected a login success message, but none appeared.";
    public static final String LOGIN_SUCCESS_TEXT_INVALID = "Success message content was incorrect or missing.";

    public static final String LOGIN_ERROR_MESSAGE_MISSING = "Expected an error message after login, but none appeared.";
    public static final String INVALID_USERNAME_MESSAGE_MISMATCH = "Expected username error message was missing or incorrect.";
    public static final String BLANK_USERNAME_MESSAGE_MISMATCH = "Error message for blank username was missing or incorrect.";

    public static final String PASSWORD_BLANK_MESSAGE = "Password cannot be blank.";
    public static final String USERNAME_AND_PASSWORD_REQUIRED = "Username and password are required.";
    public static final String INVALID_INPUT_SCRIPT_MESSAGE = "Invalid username or password."; // Adjust as needed

}
