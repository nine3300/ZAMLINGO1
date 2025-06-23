package com.example.zamlingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private TextView userNameTextView, userEmailTextView;
    private Button editProfileButton, logoutButton;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        // Initialize UI elements
        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
        editProfileButton = findViewById(R.id.edit_profile_button);
        logoutButton = findViewById(R.id.logout_button);

        // Load user data
        loadUserProfile();

        // Edit profile button click listener
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            startActivity(intent);
        });

        // Logout button click listener
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(Profile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserProfile() {
        // Display user email
        if (currentUser != null) {
            userEmailTextView.setText(currentUser.getEmail());

            // Display user name from Firebase Realtime Database
            mDatabase.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        userNameTextView.setText(username);
                    } else {
                        userNameTextView.setText("User");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(Profile.this, "Failed to load profile info", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
