package com.assignment.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieUtils {
    public static void acceptCookies(WebDriver driver) {
        try {
            // Example: Find and click the "Accept" button for cookies
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            WebElement acceptButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//button[@id='didomi-notice-agree-button']")));

            // If the accept button is displayed and enabled, click it
            if (acceptButton.isDisplayed() && acceptButton.isEnabled()) {
                acceptButton.click();
                System.out.println("Cookies accepted.");
            } else {
                System.out.println("Accept cookies button not found or not clickable.");
            }
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Error accepting cookies: " + e.getMessage());
        }
    }
}
