package com.assignment.base;

import com.assignment.utility.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    // Factory method to create a WebDriver instance based on the browser type
    public static WebDriver createDriver(String browser) {
        WebDriver driver = null;

        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser type must not be null or empty");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("CHROME_DRIVER_PATH"));
                // Automatically downloads and sets up the ChromeDriver
                //WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                // Automatically downloads and sets up the FirefoxDriver
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browser);
        }

        // Optional: Maximize the browser window
        if (driver != null) {
            driver.manage().window().maximize();
        }

        return driver;
    }
}
