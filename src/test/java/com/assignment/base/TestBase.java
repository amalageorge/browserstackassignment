package com.assignment.base;

import org.openqa.selenium.WebDriver;

public class TestBase {
    protected WebDriver driver;
    private DriverFactory driverFactory = new DriverFactory();

   /* public void launch() {
        String browser = System.getProperty("browser", "chrome"); // Default to chrome if not specified
        driver = driverFactory.createDriver(browser);
    }*/
    // Method to initialize WebDriver with the specified browser
    public void setUp(String browser) {
        driver = DriverFactory.createDriver(browser);
    }


    public void quitDriver() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
            driver.quit();
        }
    }
    public void loadUrl(String url) {
        driver.get(url);
        //System.out.println(driver.getCurrentUrl());
    }
}
