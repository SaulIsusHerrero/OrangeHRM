package steps;

import org.openqa.selenium.WebDriver;
import pages.*;
import utils.TemporaryDataStore;

public class Steps extends BasePage {

    private final TemporaryDataStore dataStore;

    public Steps(WebDriver webDriver) {
        // Empty constructor
        super(webDriver); //Calls to the constructor from parent class and their variable
        this.webDriver = webDriver; //Current instance
        this.dataStore = TemporaryDataStore.getInstance();
    }

    /**
     * Type userName and password,
     * @param userName
     * @param password
     */
    public void performLogin(String userName, String password) {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.writeUserNameField(userName);
        loginPage.writePasswordField(password);

        // Verificar usando la clave "testCase" si el login fue correcto o no
        String testCase = (String) TemporaryDataStore.getInstance().get("testCase");
        if ("Incorrect_Password_Login_Test".equalsIgnoreCase(testCase)) {
            loginPage.clickLoginExpectFailure();
        } else if ("Incorrect_User_Login_Test".equalsIgnoreCase(testCase)) {
            loginPage.clickLoginExpectFailure();
        } else {
            loginPage.clickLogin();
        }
    }

    /**
     * Verify you are in the next page The "Home Page"
     */
    public void perfomHomePage() {
        HomePage homePage = new HomePage(webDriver);
        homePage.verifyYouAreInHomePage();
    }
}