package com.example.zamlingo.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    private EditText editUsername, editEmail;
    private Button saveButton;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editUsername = findViewById(R.id.edit_username);
        editEmail = findViewById(R.id.edit_email);
        saveButton = findViewById(R.id.save_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        // Pre-fill current details
        if (currentUser != null) {
            editEmail.setText(currentUser.getEmail());
            mDatabase.child("username").get().addOnSuccessListener(snapshot -> {
                String username = snapshot.getValue(String.class);
                editUsername.setText(username);
            });
        }

        saveButton.setOnClickListener(v -> saveUserInfo());
    }

    private void saveUserInfo() {
        String newUsername = editUsername.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();

        if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase.child("username").setValue(newUsername).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                finish(); // Close activity after saving
            } else {
                Toast.makeText(EditProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
