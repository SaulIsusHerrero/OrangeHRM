package tests;

import org.testng.annotations.Factory;
import java.util.ArrayList;
import java.util.List;

public class TestFactory {

    @Factory
    public Object[] createTests() {
        String[] browsers = {"chrome", "firefox", "edge"};
        List<Object> tests = new ArrayList<>();

        for (String browser : browsers) {
            // Create one instance of each test per browser
            tests.add(new Blank_Password_Login_Test(browser));
            tests.add(new Blank_User_Login_Test(browser));
            tests.add(new Correct_Login_Test(browser));
            tests.add(new Incorrect_Password_Login_Test(browser));
            tests.add(new Incorrect_User_Login_Test(browser));
            // If ApiTest_Rest_Assured is not browser-dependent, add it only once outside the loop
        }

        // Add API test only once (not per browser)
        tests.add(new ApiTest_Rest_Assured());

        return tests.toArray();
    }
}
