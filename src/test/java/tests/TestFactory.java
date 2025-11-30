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
            tests.add(new Blank_Password_Login_Test(browser));
            tests.add(new Blank_User_Login_Test(browser));
            tests.add(new Correct_Login_Test(browser));
            tests.add(new Incorrect_Password_Login_Test(browser));
            tests.add(new Incorrect_User_Login_Test(browser));
        }
        return tests.toArray();
    }
}