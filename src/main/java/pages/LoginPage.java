package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
    //Locators
    private By userNameLocator = By.xpath("//input[@name='username']");
    private By passwordLocator = By.xpath("//input[@name='password']");
    private By clickLoginButton = By.xpath("//button[normalize-space()='Login']");


    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    //Métodos
    /**
     * type the userName in the textbox on the Login page.
     * @param userName as a string
     */
    public void writeUserNameField(String userName) {
        waitUntilElementIsDisplayed(userNameLocator, TIMEOUT);
        setElementText(userNameLocator, userName);
    }

    /**
     * type the password in the textbox on the Login page.
     * @param password as a string
     */
    public void writePasswordField(String password) {
        waitUntilElementIsDisplayed(passwordLocator, TIMEOUT);
        setElementText(passwordLocator, password);
    }

    /**
     * click in login button
     */
    public void clickLogin() {
        waitUntilElementIsDisplayed(clickLoginButton, TIMEOUT);
        scrollElementIntoView(clickLoginButton);
        clickElement(clickLoginButton);
    }

}
