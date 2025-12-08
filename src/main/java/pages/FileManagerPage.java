package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import java.io.File;

public class FileManagerPage extends BasePage {

    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    private By downloadButton = By.id("downloadButton");
    private By uploadButton = By.id("uploadFile");
    private By resultLabel = By.id("uploadedFilePath");

    public FileManagerPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isFilePathCreated(String filePath) {
        File file = new File(filePath);
        boolean exists = file.exists();
        Reporter.log(GREEN + "SUCCESS: File exists? " + exists + RESET, true);
        return exists;
    }

    public void clickDownloadButton() {
        waitUntilElementIsDisplayed(downloadButton, TIMEOUT);
        clickElement(downloadButton);
        Reporter.log(BLUE + "INFO: Clicked download button" + RESET, true);
    }

    public void uploadFilePath(String filePath) {
        waitUntilElementIsDisplayed(uploadButton, TIMEOUT);
        setElementText(uploadButton, filePath);
        Reporter.log(GREEN + "SUCCESS: Uploaded file: " + filePath + RESET, true);
    }

    public String getResultLabel() {
        waitUntilElementIsDisplayed(resultLabel, TIMEOUT);
        return getElementText(resultLabel);
    }
}
