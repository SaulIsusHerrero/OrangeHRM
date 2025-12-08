package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;



public class LoginPage extends BasePage {
    // ANSI color codes
    private static final String BLUE = "\u001B[34m";   // Info
    private static final String GREEN = "\u001B[32m";  // Success
    private static final String RED = "\u001B[31m";    // Error
    private static final String RESET = "\u001B[0m";


    //Locators
    private By userNameLocator = By.xpath("//input[@id='txtUsername']");
    private By passwordLocator = By.xpath("//input[@id='txtPassword']");
    private By clickLoginLocator = By.xpath("//button[normalize-space()='Login']");
    private By InvalidCredentialsLocator = By.xpath("//div[@class='dashboardCard-title-for-card']");
    private By EmptyUserNameTextFieldLocator = By.xpath("//span[@id='txtUsername-error']");
    private By EmptyPasswordTextFieldLocator = By.xpath("//span[@id='txtPassword-error']");

    //Constructor
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
        //Typed Username
        Reporter.log(BLUE + "INFO: Typed username: " + userName + RESET, true);
    }

    /**
     * Type the password in the textbox on the Login page.
     *
     * @param password as a string
     */
    public void writePasswordField(String password) {
        waitUntilElementIsDisplayed(passwordLocator, TIMEOUT);
        setElementText(passwordLocator, password);
        //Typed Password
        Reporter.log(BLUE + "INFO: Typed password: [HIDDEN for security]" + RESET, true);
    }

    /**
     * Click login and wait it be successful.
     */
    public void clickLoginSuccessful() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);
        //Click on Login button
        Reporter.log(GREEN + "SUCCESS: Clicked on Login button expecting success." + RESET, true);
    }

    /**
     * Click login and wait the login to fail due to incorrect credentials.
     */
    public void clickLoginExpectFailureData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);
        //Click expecting failure after typing invalid credentials
        Reporter.log(BLUE + "INFO: Clicked on Login button expecting failure due to invalid credentials." + RESET, true);
        // Wait up till the error´s toast appears.
        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);
        WebElement toastError = wait.until(ExpectedConditions.visibilityOfElementLocated(InvalidCredentialsLocator));
        Assert.assertTrue(toastError.isDisplayed(), "The error message was not displayed even though the credentials were invalid.");
        //Error message because invalid credentials
        Reporter.log(RED + "ERROR: Error message displayed as expected for invalid credentials." + RESET, true);
    }

    /**
     * Click login and wait for the login to fail due to missing credentials
     * (either username OR password).
     */
    public void clickLoginExpectEmptyData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        clickElement(clickLoginLocator);
        //Empty username or password
        Reporter.log(BLUE + "INFO: Clicked on Login button expecting failure due to empty username or password." + RESET, true);
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

            Assert.assertTrue(errorElement.isDisplayed(),"Expected error message was not displayed for missing credentials.");
            //Error missing credentials
            Reporter.log(RED + "ERROR: Error message displayed for missing credentials." + RESET, true);
            // Verify color is red
            String colorValue = errorElement.getCssValue("color");
            Color actualColor = Color.fromString(colorValue);
            Color expectedColor = Color.fromString("#eb0910");
            Assert.assertEquals(actualColor.asHex(), expectedColor.asHex(),"The error message color must be red.");
            //Color is red
            Reporter.log(GREEN + "SUCCESS: Verified error message color is red." + RESET, true);
        } catch (TimeoutException e) {
            Reporter.log(RED + "ERROR: No error message appeared within " + TIMEOUT + " seconds." + RESET, true);
            Assert.fail("No error message appeared within " + TIMEOUT + " seconds.");
        }
    }

}
