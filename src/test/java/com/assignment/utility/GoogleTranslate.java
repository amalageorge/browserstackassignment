package com.assignment.utility;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GoogleTranslate {
    public static String translateText(String text, String targetLanguage, String projectId) {
        try {
            // Set the credentials JSON file path
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS",
                    "C:\\Users\\amala\\learnings\\Assignment\\MyAssignment\\src\\test\\resources\\googlecloud\\googletranslateapi.json");
                    //ConfigReader.getProperty("GOOGLE_TRANSLATE_JSON_PATH"));

            // Create a Translate instance
            Translate translate = TranslateOptions.newBuilder().setProjectId(projectId).build().getService();
            /*Translate translate = TranslateOptions.newBuilder()
                    .setApiKey(apiKey)
                    .build()
                    .getService();*/

            System.out.println("Project ID: " + projectId);
            System.out.println("Credentials Path: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));


            // Translate the text
            Translation translation = translate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );

            System.out.println("Project ID: " + projectId);
            System.out.println("Credentials Path: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));


            // Return the translated text
            return translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
