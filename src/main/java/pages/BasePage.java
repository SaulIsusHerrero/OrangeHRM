package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    //Driver initialization
    protected WebDriver webDriver; // Protected in order to be used by child classes.

    //Constructor with WebDriver as a parameter.
    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    //Variables and Constants
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    /**
     * Writes text inside a given element locator.
     *
     * @param locator By with the locator of the element.
     * @param text    String with the text that should be written.
     */
    public void setElementText(By locator, String text) {
        webDriver.findElement(locator).sendKeys(text);
    }

    /**
     * Clicks a given element locator.
     *
     * @param locator By with the locator of the element.
     */
    public void clickElement(By locator) {
        WebElement element = webDriver.findElement(locator);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
    }

    /**
     * Returns "true" or "false" depending on if a given element locator is currently selected or unselected.
     * Normally used to interact with checkboxes or radio buttons.
     *
     * @param inputLocator By with the input locator of the element.
     */
    public boolean isElementSelected(By inputLocator) {
        return webDriver.findElement(inputLocator).isSelected();
    }

    /**
     * Scrolls a given element locator to the center of the screen.
     *
     * @param locator By with the locator of the element.
     */
    public void scrollElementIntoView(By locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});",
                webDriver.findElement(locator));
    }

    /**
     * Scrolls a given element locator to the center of the screen.
     *
     * @param element WebElement
     */
    public void scrollElementIntoViewElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Marks a given element locator as selected or unselected.
     * Normally used to interact with checkboxes or radio buttons.
     *
     * @param inputLocator     By with the input locator of the element
     * @param labelLocator     By with the label locator of the element
     * @param expectedSelected boolean with the expected selected state of the element
     */
    public void setElementSelected(By inputLocator, By labelLocator, boolean expectedSelected) {
        boolean actualSelected = isElementSelected(inputLocator);
        if (actualSelected != expectedSelected) {
            scrollElementIntoView(labelLocator);
            clickElement(labelLocator);
        }
    }

    /**
     * Waits until an element is displayed in any Page.
     * @param TIMEOUT Duration
     * @param locator By
     */
    public void waitUntilElementIsDisplayed(By locator, Duration TIMEOUT) {
        WebDriverWait wait = new WebDriverWait(webDriver, TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Normaliza el formato del precio para asegurar que usa coma como separador decimal
     * y siempre tenga dos decimales.
     * @param priceText Texto del precio (ej: "66.5€", "66,50€" o "54€")
     * @return Precio normalizado (ej: "66,50€" o "54,00€")
     */
    public String normalizePrice(String priceText) {
        // Limpiar el texto: eliminar espacios y caracteres no numéricos excepto , . y €
        String cleaned = priceText.trim()
                .replaceAll("\\s+", "")
                .replace(".", ",");

        // Verificar si ya tiene decimales
        if (cleaned.contains(",")) {
            // Asegurar dos decimales después de la coma
            cleaned = cleaned.replaceAll(",(\\d)€", ",$10€")
                    .replaceAll(",(\\d{2})€", ",$1€");
        } else {
            // No tiene decimales, añadir ,00
            cleaned = cleaned.replace("€", ",00€");
        }

        return cleaned;
    }
}
