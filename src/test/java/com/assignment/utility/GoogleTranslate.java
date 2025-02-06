package com.assignment.utility;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class GoogleTranslate {
    public static String translateText(String text, String targetLanguage, String apiKey) {
        try {

            // Create a Translate instance
            Translate translate = TranslateOptions.newBuilder()
                    .setApiKey(apiKey)
                    .build()
                    .getService();

            // Translate the text
            Translation translation = translate.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );

            // Return the translated text
            return translation.getTranslatedText();
        } catch (TranslateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
