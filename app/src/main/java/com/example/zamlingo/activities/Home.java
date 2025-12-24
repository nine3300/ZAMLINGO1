package com.example.zamlingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.example.zamlingo.WordLibrary.WordLibrary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load word library from Firebase
        WordLibrary.loadFromFirebase();

        // Language buttons
        Button bembaButton = findViewById(R.id.lesson_1_button);
        Button nyanjaButton = findViewById(R.id.lesson_2_button);

        bembaButton.setOnClickListener(v -> handleLanguageSelection("Bemba"));
        nyanjaButton.setOnClickListener(v -> handleLanguageSelection("Nyanja"));

        // Profile icon
        ImageView profileIcon = findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> {
            // Open Profile activity when profile icon is clicked
            Intent intent = new Intent(Home.this, Profile.class);
            startActivity(intent);
        });
    }

    private void handleLanguageSelection(String language) {
        // Check if user is logged in
        if (auth.getCurrentUser() == null) {
            Toast.makeText(Home.this, "User not logged in. Please log in to continue.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the user ID
        String userId = auth.getCurrentUser().getUid();

        // Save language preference to Firebase Realtime Database
        mDatabase.child("users").child(userId).child("language").setValue(language)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If language is saved successfully, navigate to the lesson plan
                        Toast.makeText(Home.this, "Language saved: " + language, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, Basic_Language_Lessons.class);
                        intent.putExtra("selected_language", language);  // Pass the selected language
                        startActivity(intent);
                    } else {
                        // Log the error message for debugging
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(Home.this, "Error saving language: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Log and display the exception message
                    Toast.makeText(Home.this, "Failed to save language: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
