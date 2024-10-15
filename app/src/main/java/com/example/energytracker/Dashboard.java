package com.example.energytracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private LineChart lineChart;
    private ImageView profileIcon; // Declare ImageView for profile icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize the LineChart
        lineChart = findViewById(R.id.lineChart);

        // Initialize the profileIcon
        profileIcon = findViewById(R.id.profile_icon); // Ensure this ID matches your XML layout

        // Set onClickListener for profile icon to show PopupMenu
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileMenu(v);
            }
        });

        // Fetch readings data
        fetchReadings();
    }

    private void fetchReadings() {
        String url = "http://192.168.19.211/energy_tracker/fetch_readings.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Entry> entries = new ArrayList<>();
                        float cumulativeKWh = 0;
                        float timeInterval = 1f / 60; // Assuming readings are taken every minute

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                float voltage = (float) jsonObject.getDouble("voltage");
                                float current = (float) jsonObject.getDouble("current");

                                // Calculate power in watts
                                float power = voltage * current; // in watts

                                // Calculate kWh for this interval
                                float kWh = (power * timeInterval) / 1000; // Convert to kWh
                                cumulativeKWh += kWh;

                                // Create a new entry for the cumulative kWh
                                entries.add(new Entry(i, cumulativeKWh)); // Use cumulative kWh for the Y value
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Set up LineDataSet and LineData
                        LineDataSet dataSet = new LineDataSet(entries, "Energy Consumption (kWh)");
                        LineData lineData = new LineData(dataSet);
                        lineChart.setData(lineData);
                        lineChart.invalidate(); // Refresh the chart
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(Dashboard.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void showProfileMenu(View v) {
        // Create a PopupMenu
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_username) {
                    // Display the logged-in username (can be fetched dynamically if needed)
                    Toast.makeText(Dashboard.this, "Logged in as: Aniruddha", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.menu_logout) {
                    // Handle the logout action
                    Toast.makeText(Dashboard.this, "Logged out", Toast.LENGTH_SHORT).show();
                    // Start the LoginActivity
                    Intent intent = new Intent(Dashboard.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
                    startActivity(intent);
                    finish(); // Close the Dashboard activity
                    return true;
                }
                return false;
            }
        });

        // Show the popup menu
        popupMenu.show();
    }


}
