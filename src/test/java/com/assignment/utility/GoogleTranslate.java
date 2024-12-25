package com.assignment.utility;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GoogleTranslate {
    public static String translateText(String text, String targetLanguage, String projectId) {
        try {
            // Set the credentials JSON file path
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "/path/to/your-service-account-file.json");

            // Create a Translate instance
            Translate translate = TranslateOptions.newBuilder().setProjectId(projectId).build().getService();

            // Translate the text
            Translation translation = translate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );

            // Return the translated text
            return translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
