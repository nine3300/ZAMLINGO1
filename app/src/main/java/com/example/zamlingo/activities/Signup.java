package com.example.zamlingo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize views
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(v -> {
            if (validateSignupFields()) {
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();

                // Create new user with Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // User signed up with Firebase Auth
                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                // Get user details
                                String name = signupName.getText().toString();
                                String username = signupUsername.getText().toString();

                                // Create a HelperClass object to store user details
                                HelperClass helperClass = new HelperClass(name, email, username, password);

                                // Get a reference to the users node in Realtime Database
                                reference = FirebaseDatabase.getInstance().getReference("users");

                                // Store user details under the unique userId
                                if (firebaseUser != null) {
                                    reference.child(firebaseUser.getUid()).setValue(helperClass)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(Signup.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Signup.this, Login.class));
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(Signup.this, "Failed to store user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                // If sign-up fails, show an error message
                                Toast.makeText(Signup.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        loginRedirectText.setOnClickListener(v -> startActivity(new Intent(Signup.this, Login.class)));
    }

    private Boolean validateSignupFields() {
        if (signupName.getText().toString().isEmpty()) {
            signupName.setError("Name cannot be empty");
            return false;
        }
        if (signupEmail.getText().toString().isEmpty()) {
            signupEmail.setError("Email cannot be empty");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signupEmail.getText().toString()).matches()) {
            signupEmail.setError("Invalid email format");
            return false;
        }
        if (signupUsername.getText().toString().isEmpty()) {
            signupUsername.setError("Username cannot be empty");
            return false;
        }
        if (signupPassword.getText().toString().isEmpty()) {
            signupPassword.setError("Password cannot be empty");
            return false;
        }
        return true;
    }
}
