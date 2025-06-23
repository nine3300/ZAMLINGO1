package com.example.zamlingo.activities;

public class GeminiApiClient {

    public static void getTranslation(String language, String word, TranslationCallback callback) {
        // Make network request to Gemini API
        // Replace with actual network call and handle response

        String mockTranslation = "Sample translation"; // Replace with actual translation data
        callback.onSuccess(mockTranslation);
    }

    public interface TranslationCallback {
        void onSuccess(String translation);
        void onFailure(Exception e);
    }
}
