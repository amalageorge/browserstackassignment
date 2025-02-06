package com.assignment.steps;


import com.assignment.base.TestBase;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefinitions extends TestBase{

    List<String> titleWords = new ArrayList<>();
    List<String> translatedTitles = new ArrayList<>();

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
            titleWords.add(title);

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(85));

        for(int i = 0; i < 5; i++){

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//article//h2//a)[" + (i +1) + "]")));
            driver.findElement(By.xpath("(//article//h2//a)[" + (i +1) + "]")).click();

            List<WebElement> images = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("(//article//figure//span//img)[1]")));

            if(images.get(0).isDisplayed()) {

                String imageUrl = images.get(0).getAttribute("src");
                System.out.println("Downloading image: " + imageUrl);

                // Download and save the image
                SaveImage.saveImage(imageUrl, "images/cover_image_" + i + ".jpg");
            }

            //navigate back to opinion page
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://elpais.com/opinion/"));
            //Assert.assertEquals("https://elpais.com/opinion/", driver.getCurrentUrl());

        }
    }

    @When("each header is translated to English")
    public void each_word_is_translated_to_english() {
     StringBuilder s = new StringBuilder();
     //to print each word of the titles
        for(String title : titleWords){
            //for(String word : array){
                System.out.println("ACTUAL TITLE : " + title + " ");
            //use google translate api to translate Spanish to English
            String translatedText = GoogleTranslate.translateText(title, "en", ConfigReader.getProperty("GOOGLE_TRANSLATE_API_KEY"));
            System.out.println("TRANSLATED TITLE : " + translatedText);
            System.out.println("=========================================");

            translatedTitles.add(translatedText);
        }
    }

    @Then("identify any words that are repeated more than twice across all headers combined")
    public void identify_any_words_that_are_repeated_more_than_twice_across_all_headers_combined() {

        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String title : translatedTitles) {
            // Split the title into words
            String[] words = title.toLowerCase().split("\\s+");

            // Update the word count map
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", ""); // Remove non-alphabetic characters
                if (!word.isEmpty()) {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Print words that occur more than twice
        System.out.println("Words that appear more than twice:");
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

    }

    @After
    public void tearDown() throws InterruptedException {
        quitDriver();
    }
}
