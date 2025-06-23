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

public class Login extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText, forgotPasswordText;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize views
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        // Set up login button listener
        loginButton.setOnClickListener(v -> {
            if (!validateUsername() || !validatePassword()) return;
            loginUser();
        });

        // Set up signup redirect listener
        signupRedirectText.setOnClickListener(v -> startActivity(new Intent(Login.this, Signup.class)));

        // Set up forgot password listener
        forgotPasswordText.setOnClickListener(v -> startActivity(new Intent(Login.this, Forgot_Password.class)));
    }

    private Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        }
        loginUsername.setError(null);
        return true;
    }

    private Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        loginPassword.setError(null);
        return true;
    }

    private void loginUser() {
        String email = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        // Sign in the user with Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // User is signed in, redirect to Home Activity
                            startActivity(new Intent(Login.this, Home.class));
                        }
                    } else {
                        // If sign-in fails, show an error message
                        Toast.makeText(Login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
