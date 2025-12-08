package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import pages.FileManagerPage;

public class File_Management_Test {

    private WebDriver webDriver;
    private FileManagerPage fileManagerPage;
    private String downloadDir;

    @BeforeMethod
    public void setup() {
        downloadDir = System.getProperty("user.dir") + "\\files\\downloads";

        // Configure Chrome to use custom download directory
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.get("https://demoqa.com/upload-download");

        fileManagerPage = new FileManagerPage(webDriver);
    }

    /**
     * Test to download a file and verify its existence dynamically.
     */
    @Test
    public void download_Test() {
        String downloadedFile = downloadDir + "\\sampleFile.jpeg";

        // Ensure file does not exist before download
        Assert.assertFalse(fileManagerPage.isFilePathCreated(downloadedFile), "File should not exist before download.");

        // Click download button
        fileManagerPage.clickDownloadButton();

        // Dynamic wait until file exists
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        boolean fileDownloaded = wait.until(driver -> new File(downloadedFile).exists());

        Assert.assertTrue(fileDownloaded, "File was not downloaded within timeout!");

        // Delete file after verification
        new File(downloadedFile).delete();
    }

    /**
     * Test to upload a file and verify the result label.
     */
    @Test
    public void upload_Test() {
        String fileToUpload = System.getProperty("user.dir") + "\\resources\\uploads_downloads\\dog_qa.png";

        fileManagerPage.uploadFilePath(fileToUpload);

        // Dynamic wait until result label updates
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        boolean labelUpdated = wait.until(driver -> fileManagerPage.getResultLabel().contains("dog_qa.png"));

        Assert.assertTrue(labelUpdated, "Uploaded file name not found in result label!");
    }

    @AfterMethod
    public void screenshotIfFails(ITestResult result) throws IOException {
        System.out.println("🧪 Test status: " + result.getStatus() + " (" + result.getName() + ")");
        if (result.getStatus() == ITestResult.FAILURE && webDriver != null) {
            if (result.getThrowable() != null) {
                System.err.println("❗ Exception in test: " + result.getThrowable().getMessage());
            }
            File screenshot = ((org.openqa.selenium.TakesScreenshot) webDriver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destination = new File("screenshots/" + result.getName() + "_" + timestamp + ".png");
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath());
            System.out.println("📸 Screenshot saved at: " + destination.getAbsolutePath());
        }
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
