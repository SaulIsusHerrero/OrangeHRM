package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage extends BasePage {
    //Locators
    private By userNameLocator = By.xpath("//input[@id='txtUsername']");
    private By passwordLocator = By.xpath("//input[@id='txtPassword']");
    private By clickLoginLocator = By.xpath("//button[normalize-space()='Login']");
    private By InvalidCredentialsLocator = By.xpath("//div[@class='dashboardCard-title-for-card']");
    private By EmptyUserNameTextFieldLocator = By.xpath("//span[@id='txtUsername-error']");
    private By EmptyPasswordTextFieldLocator = By.xpath("//span[@id='txtPassword-error']");

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    //Methods
    /**
     * Type the userName in the textbox on the Login page.
     *
     * @param userName as a string
     */
    public void writeUserNameField(String userName) {
        waitUntilElementIsDisplayed(userNameLocator, TIMEOUT);
        setElementText(userNameLocator, userName);
    }

    /**
     * Type the password in the textbox on the Login page.
     *
     * @param password as a string
     */
    public void writePasswordField(String password) {
        waitUntilElementIsDisplayed(passwordLocator, TIMEOUT);
        setElementText(passwordLocator, password);
    }

    /**
     * Click login and wait it be successful.
     */
    public void clickLoginSuccessful() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);
    }

    /**
     * Click login and wait the login to fail due to incorrect credentials.
     */
    public void clickLoginExpectFailureData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);

        // Wait up till the error´s toast appears.
        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);
        WebElement toastError = wait.until(ExpectedConditions.visibilityOfElementLocated(InvalidCredentialsLocator));
        Assert.assertTrue(toastError.isDisplayed(), "The error message was not displayed even though the credentials were invalid.");
    }


    /**
     * Click login and wait for the login to fail due to missing credentials
     * (either username OR password).
     */
    public void clickLoginExpectEmptyData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        clickElement(clickLoginLocator);

        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);

        try {
            // Wait for either username or password error to appear
            WebElement errorElement = wait.until(driver -> {
                if (isElementPresent(EmptyUserNameTextFieldLocator)) {
                    return webDriver.findElement(EmptyUserNameTextFieldLocator);
                } else if (isElementPresent(EmptyPasswordTextFieldLocator)) {
                    return webDriver.findElement(EmptyPasswordTextFieldLocator);
                }
                return null;
            });

            Assert.assertTrue(errorElement.isDisplayed(),
                    "Expected error message was not displayed for missing credentials.");

            // Verify color is red
            String colorValue = errorElement.getCssValue("color");
            Color actualColor = Color.fromString(colorValue);
            Color expectedColor = Color.fromString("#eb0910");
            Assert.assertEquals(actualColor.asHex(), expectedColor.asHex(),
                    "The error message color must be red.");

        } catch (TimeoutException e) {
            Assert.fail("No error message appeared within " + TIMEOUT + " seconds.");
        }
    }

}
