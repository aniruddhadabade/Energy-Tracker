package com.example.energytracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
public class registerr2 extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerr2);

        // Initialize fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmpassword);
        buttonSubmit = findViewById(R.id.cirRegisterButton);

        // Get the values from the first page
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String fullName = intent.getStringExtra("fullName");
        String age = intent.getStringExtra("age");
        String phone = intent.getStringExtra("phone");

        // Improved password confirmation handling
        buttonSubmit.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Validate input fields
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(registerr2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(registerr2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Submit the data to the server
            registerUser(username, fullName, age, phone, email, password);
        });

    }

    private void registerUser(String username, String fullName, String age, String phone, String email, String password) {
        String url = "http://192.168.19.211/energy_tracker/register.php"; // Adjust your server IP
        RequestQueue queue = Volley.newRequestQueue(registerr2.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle success response
                    Toast.makeText(registerr2.this, "Registration successful!", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(registerr2.this, MainActivity.class); // Adjust the target activity class name
                        startActivity(intent);
                        finish(); // Optionally, finish this activity
                    }, 2000);
                },
                error -> {
                    // Handle error response
                    Toast.makeText(registerr2.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("full_name", fullName);
                params.put("age", age);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
                Log.d("RequestParams", params.toString());
                return params;
            }
        };

        queue.add(stringRequest);
    }
}