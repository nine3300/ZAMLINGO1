package com.example.zamlingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;

public class Basic_Language_Lessons extends AppCompatActivity {

    private Button vocabularyButton, grammarButton, speakingButton, contextualLearningButton, backToHomeButton;
    private String selectedLanguage;
    private static final String TAG = "BasicLanguageLessons"; // Add a tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_language_lessons);

        // Initialize buttons
        vocabularyButton = findViewById(R.id.vocabularyButton);
        grammarButton = findViewById(R.id.grammarButton);
        speakingButton = findViewById(R.id.speakingButton);
        contextualLearningButton = findViewById(R.id.contextualLearningButton); // New button for contextual learning
        backToHomeButton = findViewById(R.id.backToHomeButton);

        // Retrieve selected language from the previous intent
        selectedLanguage = getIntent().getStringExtra("selected_language");

        // Log the selected language to make sure it's passed correctly
        Log.d(TAG, "Selected Language: " + selectedLanguage);

        // Check if selectedLanguage is null
        if (selectedLanguage == null) {
            Log.e(TAG, "Selected language is null!");
            return; // Prevent further crashes if language is null
        }

        // Set up Vocabulary Practice button listener
        vocabularyButton.setOnClickListener(v -> {
            Intent intent = new Intent(Basic_Language_Lessons.this, VocabularyPractice.class);
            intent.putExtra("selected_language", selectedLanguage);
            startActivity(intent);
        });

        // Set up Grammar Exercises button listener
        grammarButton.setOnClickListener(v -> {
            if (selectedLanguage != null) {
                Intent intent = new Intent(Basic_Language_Lessons.this, GrammarExercise.class);
                intent.putExtra("selected_language", selectedLanguage);
                startActivity(intent);
            } else {
                Log.e(TAG, "Cannot start GrammarExercise activity because selectedLanguage is null.");
            }
        });

        // Set up Speaking Practice button listener
        speakingButton.setOnClickListener(v -> {
            Intent intent = new Intent(Basic_Language_Lessons.this, SpeakingPractice.class);
            intent.putExtra("selected_language", selectedLanguage);
            startActivity(intent);
        });

        // Set up Contextual Learning button listener
        contextualLearningButton.setOnClickListener(v -> {
            Intent intent = new Intent(Basic_Language_Lessons.this, ContextualLearningActivity.class);
            intent.putExtra("selected_language", selectedLanguage);
            startActivity(intent);
        });

        // Set up Back to Home button listener
        backToHomeButton.setOnClickListener(v -> {
            finish(); // Closes this activity and goes back to the previous screen
        });
    }
}
