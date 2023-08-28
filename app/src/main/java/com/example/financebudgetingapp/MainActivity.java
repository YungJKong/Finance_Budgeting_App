package com.example.financebudgetingapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView moneyTextView;
    private ListView recentActivitiesListView;
    private ImageButton btnHome, btnAct, btnStat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTextView = findViewById(R.id.usernameTextView);
        moneyTextView = findViewById(R.id.moneyTextView);
        recentActivitiesListView = findViewById(R.id.recentActivitiesListView);
        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Activities button click here
                // For example: startActivity(new Intent(MainActivity.this, ActivitiesActivity.class));
                startActivity(new Intent(MainActivity.this, com.example.financebudgetingapp.Transaction.class));
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Statistics button click here
                // For example: startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            }
        });

        // Set user's name
        String username = "John"; // Fetch username from your data source
        usernameTextView.setText("Hello, " + username);

        // Set credit card balance
        double cardBalance = 5000.00; // Fetch balance from your data source
        moneyTextView.setText("RM" + cardBalance);

        // Populate recent activities ListView (you need to implement an adapter)
        ArrayList<String> recentActivities = fetchRecentActivities(); // Implement this
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentActivities);
        recentActivitiesListView.setAdapter(adapter);




    }

    public class Transaction {
        private String name;
        private String date;
        private double amount;

        public Transaction(String name, String date, double amount) {
            this.name = name;
            this.date = date;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }


    private ArrayList<String> fetchRecentActivities() {
        ArrayList<String> recentActivities = new ArrayList<>();

        // Assume you have a list of Activity objects
        List<Transaction> activityList = getActivitiesFromDataSource(); // Implement this method

        // Display the most recent activities
        int maxActivitiesToShow = 5; // You can adjust this as needed
        for (int i = 0; i < Math.min(activityList.size(), maxActivitiesToShow); i++) {
            String activityDescription = formatActivityDescription(activityList.get(i)); // Implement this method
            recentActivities.add(activityDescription);
        }

        return recentActivities;
    }

    // ... Other code ...

    // Method to fetch activities from your data source (database, API, etc.)
    private List<Transaction> getActivitiesFromDataSource() {
        // Implement this method to retrieve a list of recent activities
        // For this example, let's assume you have a list of Activity objects
        List<Transaction> activityList = new ArrayList<>();

        // Populate the activityList with data from your data source
        // Example:
        activityList.add(new Transaction("Grocery shopping", "2023-08-10", -50.00));
        activityList.add(new Transaction("Dinner at restaurant", "2023-08-09", -30.00));
        // ...

        return activityList;
    }

    // Method to format activity description for display
    private String formatActivityDescription(Transaction activity) {
        String formattedDescription = activity.getName() + " on " + activity.getDate() +
                " (" + (activity.getAmount() >= 0 ? "+" : "-") + "RM" + Math.abs(activity.getAmount()) + ")";
        return formattedDescription;
    }
}