package com.example.energytracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class registerr1 extends AppCompatActivity {
    EditText editTextUsername, editTextFullName, editTextAge, editTextPhone;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerr1);

        // Initialize fields
        editTextUsername = findViewById(R.id.editTextUser);
        editTextFullName = findViewById(R.id.editTextFullname); // Updated ID
        editTextAge = findViewById(R.id.editTextAge);
        editTextPhone = findViewById(R.id.editNumberPhone);
        buttonNext = findViewById(R.id.cirNextButton);

        // On Next button click, pass the values to the next page
        buttonNext.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String fullName = editTextFullName.getText().toString().trim();
            String age = editTextAge.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();

            // Simple data validation
            if (username.isEmpty() || fullName.isEmpty() || age.isEmpty() || phone.isEmpty()) {
                // Optionally show a Toast message to notify the user
                return; // Exit early if validation fails
            }

            // Passing the data to second page using Intent
            Intent intent = new Intent(registerr1.this, registerr2.class);
            intent.putExtra("username", username);
            intent.putExtra("fullName", fullName);
            intent.putExtra("age", age);
            intent.putExtra("phone", phone);
            startActivity(intent);
        });
    }
}