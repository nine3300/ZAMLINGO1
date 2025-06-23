package com.example.zamlingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Levels extends AppCompatActivity {

    private Button easyButton, mediumButton, hardButton;
    private String selectedLanguage, activityType;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize buttons
        easyButton = findViewById(R.id.easyLevelButton);
        mediumButton = findViewById(R.id.mediumLevelButton);
        hardButton = findViewById(R.id.hardLevelButton);

        // Retrieve data from the intent
        selectedLanguage = getIntent().getStringExtra("selected_language");
        activityType = getIntent().getStringExtra("activityType");

        // Set up button listeners for difficulty levels
        easyButton.setOnClickListener(v -> navigateToActivity("Easy"));
        mediumButton.setOnClickListener(v -> navigateToActivity("Medium"));
        hardButton.setOnClickListener(v -> navigateToActivity("Hard"));
    }

    private void navigateToActivity(String difficulty) {
        // Save the difficulty level in Firebase for tracking
        saveDifficultyToFirebase(difficulty);

        // Check the selected activity and navigate accordingly
        if ("Grammar Practice".equals(activityType)) {
            // For Grammar Practice, navigate to GrammarExercise activity
            Intent intent = new Intent(Levels.this, GrammarExercise.class);
            intent.putExtra("selected_language", selectedLanguage);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        } else if ("Vocabulary Practice".equals(activityType)) {
            // For Vocabulary Practice, navigate to VocabularyPractice activity
            Intent intent = new Intent(Levels.this, VocabularyPractice.class);
            intent.putExtra("selected_language", selectedLanguage);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        } else if ("Speaking Practice".equals(activityType)) {
            // For Speaking Practice, navigate to SpeakingPractice activity
            Intent intent = new Intent(Levels.this, SpeakingPractice.class);
            intent.putExtra("selected_language", selectedLanguage);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        }
    }

    private void saveDifficultyToFirebase(String difficulty) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(userId).child("selected_activity")
                .child("difficulty").setValue(difficulty)
                .addOnSuccessListener(aVoid -> {
                    // Difficulty saved successfully
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(Levels.this, "Error saving difficulty level", Toast.LENGTH_SHORT).show();
                });
    }
}
