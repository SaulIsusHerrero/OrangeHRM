package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage extends BasePage {
    //Locators
    private By userNameLocator = By.cssSelector("input[name='username']");
    private By passwordLocator = By.cssSelector("input[name='password']");
    private By clickLoginLocator = By.xpath("//button[normalize-space()='Login']");
    private By InvalidCredentialsLocator = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
    private By EmptyUserNameTextFieldLocator = By.xpath("//span[contains(@class, 'oxd-input-field-error-message') and text()='Required']");
    private By EmptyPasswordTextFieldLocator = By.xpath("//span[contains(@class, 'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message') and text()='Required']");


    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    //Métodos

    /**
     * type the userName in the textbox on the Login page.
     *
     * @param userName as a string
     */
    public void writeUserNameField(String userName) {
        waitUntilElementIsDisplayed(userNameLocator, TIMEOUT);
        setElementText(userNameLocator, userName);
    }

    /**
     * type the password in the textbox on the Login page.
     *
     * @param password as a string
     */
    public void writePasswordField(String password) {
        waitUntilElementIsDisplayed(passwordLocator, TIMEOUT);
        setElementText(passwordLocator, password);
    }

    /**
     * Clicks login and espera que el login sea exitoso
     */
    public void clickLoginSuccessful() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);
    }

    /**
     * Clicks login y espera que el login falle por credenciales erróneas
     */
    public void clickLoginExpectFailureData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        scrollElementIntoView(clickLoginLocator);
        clickElement(clickLoginLocator);

        // Esperar que aparezca el toast de error
        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);
        WebElement toastError = wait.until(ExpectedConditions.visibilityOfElementLocated(InvalidCredentialsLocator));
        Assert.assertTrue(toastError.isDisplayed(), "No se mostró el mensaje de error aunque las credenciales eran inválidas");
    }

    /**
     * Clicks login y espera que el login falle por falta de alguna credencial
     */
    public void clickLoginExpectEmptyData() {
        waitUntilElementIsDisplayed(clickLoginLocator, TIMEOUT);
        clickElement(clickLoginLocator);

        // Esperar que aparezca el error de campo Required
        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);
        WebElement requiredUsernameError = wait.until(ExpectedConditions.visibilityOfElementLocated(EmptyUserNameTextFieldLocator));
        WebElement requiredPasswordError = wait.until(ExpectedConditions.visibilityOfElementLocated(EmptyPasswordTextFieldLocator));
        Assert.assertTrue(requiredUsernameError.isDisplayed(), "No se accedió a la Home Page ya que falta el Username");
        Assert.assertTrue(requiredPasswordError.isDisplayed(), "No se accedió a la Home Page ya que falta el Password");

        // Verificación de color rojo en el mensaje de error de username
        String color = requiredUsernameError.getCssValue("color");
        Color actual = Color.fromString(color);
        Color esperado = Color.fromString("#eb0910");
        Assert.assertEquals(actual, esperado, "El color del mensaje de error debería ser rojo");
    }

}
