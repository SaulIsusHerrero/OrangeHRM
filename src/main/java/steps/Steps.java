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

        String testCase = String.valueOf(TemporaryDataStore.getInstance().get("testCase"));

        switch (testCase.toLowerCase()) {
            case "incorrect_password_login_test":
            case "incorrect_user_login_test":
                loginPage.clickLoginExpectFailureData();
                break;

            case "blank_password_login_test":
            case "blank_user_login_test":
                loginPage.clickLoginExpectEmptyData();
                break;

            case "correct_login_test":
                loginPage.clickLoginSuccessful();
                break;

            default:
                throw new IllegalArgumentException("🚫 TestCase does not recognize: " + testCase);
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