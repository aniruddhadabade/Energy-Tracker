package com.example.energytracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<String, Void, String> {
    private Context context;

    // Constructor to get the context from MainActivity
    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        String urlStr = params[0]; // URL for login
        String username = params[1]; // Username from params
        String password = params[2]; // Password from params

        try {
            // Create URL object
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // Set request method to POST
            conn.setDoOutput(true); // Indicate that we want to send data
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Set content type

            // Prepare POST data
            String postData = "username=" + username + "&password=" + password;

            // Write data to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();

            // Read the response from the input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            response = stringBuilder.toString().trim(); // Store response
            reader.close(); // Close reader
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        }
        return response; // Return the response
    }

    @Override
    protected void onPostExecute(String result) {
        // Print the server response for debugging
        Log.d("LoginResponse", result);

        // Parse the JSON response
        try {
            JSONObject jsonResponse = new JSONObject(result);
            boolean success = jsonResponse.getBoolean("success");

            if (success) {
                // Show success message
                Toast.makeText(context, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                // Redirect to the dashboard activity
                Intent intent = new Intent(context, Dashboard.class);
                context.startActivity(intent);
            } else {
                // Show failure message
                Toast.makeText(context, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

}
