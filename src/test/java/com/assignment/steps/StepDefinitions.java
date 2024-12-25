package com.assignment.steps;


import com.assignment.base.DriverFactory;
import com.assignment.base.TestBase;
import com.assignment.base.WebDriverFactory;
import com.assignment.base.WebDriverManagerWithBrowserStackTest;
import com.assignment.runner.TestRunner;
import com.assignment.utility.ConfigReader;
import com.assignment.utility.CookieUtils;
import com.assignment.utility.GoogleTranslate;
import com.assignment.utility.SaveImage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions extends TestBase{

    List<String[]> titleWords = new ArrayList<>();

 @Before
 public void setUpBrowser() {
     // Initialize WebDriver before each scenario (you can specify the browser here)
     setUp("chrome");  // Example: "chrome", "firefox", "edge"
     System.out.println("WebDriver initialized before scenario");
 }
    @Given("Load the news outlet page")
    public void load_the_news_outlet_page() {

        loadUrl("https://elpais.com/");

    }
    @When("The cookies are accepted")
    public void the_cookies_are_accepted() {
        CookieUtils.acceptCookies(driver);
    }
    @Then("The page is loaded in spanish")
    public void the_page_is_loaded_in_spanish() {
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[(contains(@href,'elpais.com/internacional'))  and (contains(text(),'Internacional'))])[1]")));

        String spanishText = element.getText().toLowerCase();
        Assert.assertEquals("INTERNACIONAL".toLowerCase(), spanishText);
    }

    @Then("Navigate to Opinion")
    public void navigate_to_opinion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement opinion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[(contains(@href,'elpais.com/opinion'))  and (contains(text(),'Opinión'))])[1]")));
            opinion.click();
    }
    @When("First five articles are fetched and their titles and contents are printed")
    public void first_five_articles_are_fetched_and_their_titles_and_contents_are_printed() throws InterruptedException {
        List<WebElement> articles = driver.findElements(By.tagName("article"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        for(int i = 0; i < 5; i++){

            wait.until(ExpectedConditions.visibilityOfAllElements(articles));

            WebElement titleElement = articles.get(i).findElement(By.xpath(".//h2"));
            String title = titleElement.getText();
            String[] titleString = title.split(" ");
            titleWords.add(titleString);

            WebElement contentElement = articles.get(i).findElement(By.xpath(".//p"));
            String content = contentElement.getText();

            // Print the article's title and content
            System.out.println("Title " + (i + 1) + ": " + title);
            System.out.println("Content " + (i + 1) + ": " + content);
            System.out.println("-----------------------------------------------------");
            Thread.sleep(5000);

        }

    }

    @Then("Cover images of articles are downloaded")
    public void cover_images_of_articles_are_downloaded() throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        List<WebElement> articles = driver.findElements(By.tagName("article"));

        for(int i = 0; i < 5; i++){
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfAllElements(articles));
            articles.get(i).findElement(By.xpath(".//h2//a")).click();


            List<WebElement> images = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("(//article//figure//span//img)[1]")));
            //WebElement image = driver.findElement(By.xpath("(//article//figure//span//img)[1]"));

            if(images.get(0).isDisplayed()) {

                String imageUrl = images.get(0).getAttribute("src");
                System.out.println("Downloading image: " + imageUrl);

                // Download and save the image
                SaveImage.saveImage(imageUrl, "images/cover_image_" + i + ".jpg");
            }
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://elpais.com/opinion/"));
            Assert.assertEquals("https://elpais.com/opinion/", driver.getCurrentUrl());
            //Thread.sleep(5000);
        }
    }

    @When("each word is translated to English")
    public void each_word_is_translated_to_english() {
     StringBuilder s = new StringBuilder();
        for(String[] array : titleWords){
            for(String word : array){
                System.out.print(word + " ");
                s.append(word);
            }
        }
        System.out.println();

        String translatedText = GoogleTranslate.translateText(s.toString(), "en", "grounded-tine-445717-m6");
        System.out.println("TRANSLATED TEXT = " + translatedText);
        System.out.println("=========================================");
    }
    @After
    public void tearDown() throws InterruptedException {
        quitDriver();
    }
}
