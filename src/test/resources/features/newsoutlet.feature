Feature: Load the news outlet page and ensure that the text is displayed in spanish
    Scenario: Ensure that the text is displayed in spanish
     Given Load the news outlet page
     When The cookies are accepted
     Then The page is loaded in spanish
     And Navigate to Opinion
     When First five articles are fetched and their titles and contents are printed
     Then Cover images of articles are downloaded
     When each header is translated to English
     Then identify any words that are repeated more than twice across all headers combined