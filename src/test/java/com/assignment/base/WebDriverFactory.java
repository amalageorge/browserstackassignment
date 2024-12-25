package com.assignment.base;

import com.assignment.utility.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebDriverFactory {

    static WebDriver driver;
    private static final String BROWSERSTACK_USERNAME =  ConfigReader.getProperty("browserstackUsername");
    private static final String BROWSERSTACK_ACCESS_KEY = ConfigReader.getProperty("browserstackAccessKey");;
    private static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub.browserstack.com/wd/hub";

    //private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

 /*   public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }
    public static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }*/
    @Test
    public static WebDriver createDriver(boolean useBrowserStack, String browser, String browserVersion, String os, String osVersion) throws Exception {
        if (useBrowserStack) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browser", browser);
            caps.setCapability("browser_version", browserVersion);
            caps.setCapability("os", os);
            caps.setCapability("os_version", osVersion);
            caps.setCapability("name", "Test with BrowserStack");
            // BrowserStack Hub URL
            String browserStackUrl = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

            driver = new RemoteWebDriver(new URL(browserStackUrl), caps);
            return driver;
        } else {
            // Local execution with WebDriverManager
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }
            //driver.manage().window().maximize();
            // Add other browser setups (e.g., Firefox, Edge) as needed
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
