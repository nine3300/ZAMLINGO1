package com.example.zamlingo.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.example.zamlingo.WordLibrary.WordLibrary;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class VocabularyPractice extends AppCompatActivity {

    private EditText inputText;
    private Button translateButton;
    private TextView outputText;

    // Replace with your Gemini API key
    private static final String API_KEY = "YOUR_API_KEY_HERE";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY;

    // Assume this value comes dynamically (hardcoded for now)
    private String selectedLanguage = "Bemba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_practice);

        // Initialize views
        inputText = findViewById(R.id.inputText);
        translateButton = findViewById(R.id.translateButton);
        outputText = findViewById(R.id.outputText);

        // Enable the button if there's text in the EditText
        inputText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                translateButton.setEnabled(charSequence.length() > 0);
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        // Set button click listener to check the selected language and fetch translation
        translateButton.setOnClickListener(v -> {
            String text = inputText.getText().toString().trim();
            if (!text.isEmpty()) {
                if (selectedLanguage.equalsIgnoreCase("Bemba")) {
                    // Fetch translation from WordLibrary
                    String translation = WordLibrary.getBembaTranslation(text);
                    outputText.setText(translation);
                } else {
                    // Use Gemini API for other languages
                    translateText(text);
                }
            } else {
                Toast.makeText(VocabularyPractice.this, "Please enter text to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to perform translation using Gemini API for other languages
    private void translateText(String inputTextStr) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject jsonBody = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONObject parts = new JSONObject();
            parts.put("text", inputTextStr);

            content.put("parts", new JSONArray().put(parts));
            contents.put(content);

            jsonBody.put("contents", contents);

            RequestBody body = RequestBody.create(
                    jsonBody.toString(),
                    okhttp3.MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .build();

            // Make the API request in a background thread
            new Thread(() -> {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String jsonResponse = response.body().string();

                        // Parse the JSON response
                        JSONObject responseObject = new JSONObject(jsonResponse);
                        String translatedText = responseObject.getJSONArray("generatedContent")
                                .getJSONObject(0).getJSONArray("parts")
                                .getJSONObject(0).getString("text");

                        // Update UI with the translated text
                        runOnUiThread(() -> outputText.setText(translatedText));
                    } else {
                        runOnUiThread(() -> Toast.makeText(VocabularyPractice.this, "Translation failed", Toast.LENGTH_SHORT).show());
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(VocabularyPractice.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();

        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(VocabularyPractice.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
