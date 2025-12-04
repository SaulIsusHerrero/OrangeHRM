package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class HomePage extends BasePage {
    //Locators
    private By userHomeLocator = By.xpath("//div[@id='sidebar-profile-picture']");

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
        } catch (TimeoutException e) {
            isHomePageVisible = false;
        }
        if (isHomePageVisible) {
            Reporter.log("INFO: You are in the Home page.", true);
        } else {
            Reporter.log("INFO: You are NOT in the Home page.", true);

        }
    }

}
