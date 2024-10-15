package com.example.energytracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Forgot_password extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        // Set OnClickListener for the reset password button
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResetPassword();
            }
        });
    }

    private void handleResetPassword() {
        String email = emailEditText.getText().toString().trim();

        // Basic validation
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement your password reset logic here
        // e.g., sending a request to your backend server for password reset.

        // Simulate password reset success
        Toast.makeText(this, "Password reset email sent to " + email, Toast.LENGTH_SHORT).show();

        // Optionally, navigate back to the login screen
        Intent intent = new Intent(Forgot_password.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
