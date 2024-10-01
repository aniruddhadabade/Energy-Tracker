package com.example.energytracker;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class NetworkTask extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Set connection timeout
            conn.setReadTimeout(5000); // Set read timeout

            InputStream inputStream = conn.getInputStream();

            // Read from the input stream and process the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            response = stringBuilder.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print the error to log
        }
        return response; // Return the response from the server
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Here you can update your UI with the result or log it
        System.out.println("Response from server: " + result);
    }
}
