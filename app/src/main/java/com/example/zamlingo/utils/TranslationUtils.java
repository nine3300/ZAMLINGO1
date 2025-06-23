package com.example.zamlingo.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TranslationUtils {

    public static void getTranslation(String language, String word, TranslationCallback callback) {
        // Gemini API URL - replace with the actual API endpoint
        String apiUrl = "https://api.gemini.com/translate?language=" + language + "&word=" + word;

        // OkHttpClient for making the request
        OkHttpClient client = new OkHttpClient();

        // Create a request to the API
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        // Enqueue the request to make it asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String translation = response.body().string();
                    callback.onSuccess(translation); // Send translation result back
                } else {
                    callback.onFailure(new Exception("Failed to get translation: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e); // Send error back if request fails
            }
        });
    }

    public interface TranslationCallback {
        void onSuccess(String translation);
        void onFailure(Exception e);
    }
}
