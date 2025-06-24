package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage extends BasePage{
    //Locators
    private By userNameLocator = By.xpath("//p[contains(@class, 'oxd-userdropdown-name')]");

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    //Métodos
    /**
     * Checks if we are in the next Page "HomePage".
     */
    public void verifyYouAreInHomePage() {
        waitUntilElementIsDisplayed(userNameLocator, TIMEOUT);
        Boolean youAreInHomePage = webDriver.findElement(userNameLocator).isDisplayed();
        Assert.assertTrue(youAreInHomePage, "No te encuentras en la Home Page");
    }
}
