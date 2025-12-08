package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import steps.Steps;
import utils.CSVDataProvider;
import utils.DriverManager;
import utils.TemporaryDataStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pages.BasePage.TIMEOUT;

public class Blank_User_Login_Test {

    private WebDriver webDriver;
    private Steps steps;
    private String browser;

    // ✅ Constructor added for browser injection
    public Blank_User_Login_Test(String browser) {
        this.browser = browser;
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return CSVDataProvider.readDataLoginBlankUserName();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        webDriver = DriverManager.getDriver(browser);
        webDriver.manage().timeouts().implicitlyWait(TIMEOUT);
        webDriver.manage().window().maximize();
        webDriver.get("https://saul1-trials719.orangehrmlive.com/auth/seamlessLogin");
        steps = new Steps(webDriver);
    }

    /**
    * Login in the web without data in userName textfield and correct password.
    */
    @Test(dataProvider = "loginData")
    public void blank_User_Login_Test(
            String Username,
            String password){
        TemporaryDataStore.getInstance().set("testCase", "Blank_User_Login_Test");
        // Bloques reutilizables (steps)
        steps.performLogin(Username, password);
        steps.perfomHomePage();
    }

    @AfterMethod
    public void screnshootIfFails(ITestResult result) throws IOException {
        System.out.println("🧪 Estado del test: " + result.getStatus() + " (" + result.getName() + ")");

        if (result.getStatus() == ITestResult.FAILURE && webDriver != null) {
            if (result.getThrowable() != null) {
                System.err.println("❗ Excepción en test: " + result.getThrowable().getMessage());
            }

            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String testName = result.getName();
            File destination = new File("screenshots/" + testName + "_" + timestamp + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath());
            System.out.println("📸 Captura guardada en: " + destination.getAbsolutePath());
        }
        if (webDriver != null) {
            webDriver.quit();
        }
    }

}

