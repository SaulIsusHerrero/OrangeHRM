package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class HomePage extends BasePage {
    // ANSI color codes
    private static final String BLUE = "\u001B[34m";   // Info
    private static final String GREEN = "\u001B[32m";  // Success
    private static final String RED = "\u001B[31m";    // Error
    private static final String RESET = "\u001B[0m";

    //Locators
    private By userHomeLocator = By.xpath("//div[@id='sidebar-profile-picture']");

    //Constructor
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    //Methods
    /**
     * Checks if we are in the next Page "HomePage".
     */
    public void verifyYouAreInHomePage() {
        boolean isHomePageVisible;

        try {
            waitUntilElementIsDisplayed(userHomeLocator, TIMEOUT);
            isHomePageVisible = true;
            Reporter.log(BLUE + "INFO: Home page element found." + RESET, true);
        } catch (TimeoutException e) {
            isHomePageVisible = false;
            Reporter.log(RED + "ERROR: Home page element NOT found within " + TIMEOUT + " seconds." + RESET, true);
        }

        if (isHomePageVisible) {
            Reporter.log(GREEN + "SUCCESS: You are in the Home page." + RESET, true);
        } else {
            Assert.assertFalse(isHomePageVisible, "Home page verification failed.");
            Reporter.log(RED + "ERROR: You are NOT in the Home page." + RESET, true);
        }
    }

}
