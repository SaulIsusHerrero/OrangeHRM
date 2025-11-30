package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

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
        boolean isHomePageVisible = false;

        try {
            waitUntilElementIsDisplayed(userHomeLocator, TIMEOUT);
            isHomePageVisible = true;
        } catch (TimeoutException e) {
            isHomePageVisible = false; // Element not found within timeout.
        }
        Assert.assertTrue(isHomePageVisible, "You are not in the Home page.");
    }

}
