package steps;

import org.openqa.selenium.WebDriver;
import pages.*;

public class Steps extends BasePage {

    public Steps(WebDriver webDriver) {
        // Empty constructor
        super(webDriver); //Calls to the constructor from parent class and their variable
        this.webDriver = webDriver; //Current instance
    }

}