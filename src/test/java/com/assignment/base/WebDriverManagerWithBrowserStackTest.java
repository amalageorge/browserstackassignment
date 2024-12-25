package com.assignment.base;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;

public class WebDriverManagerWithBrowserStackTest {
    WebDriver driver;

    @Parameters({"useBrowserStack", "browser", "browserVersion", "os", "osVersion"})
    @Test
    public void testExecution(String useBrowserStack, String browser, String browserVersion, String os, String osVersion) throws Exception {
        boolean useBS = Boolean.parseBoolean(useBrowserStack);

        driver = WebDriverFactory.createDriver(useBS, browser, browserVersion, os, osVersion);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
