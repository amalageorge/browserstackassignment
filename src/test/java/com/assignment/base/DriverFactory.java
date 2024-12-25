package com.assignment.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static WebDriver createDriver(String browser) {
        WebDriver driver = null;

        if (browser.equalsIgnoreCase("chrome")) {
            // Automatically downloads and sets up the ChromeDriver
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Automatically downloads and sets up the FirefoxDriver
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser type not supported: " + browser);
        }

        // Additional configurations like maximizing the window
        //driver.manage().window().maximize();
        return driver;
    }
}
