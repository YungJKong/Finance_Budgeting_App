package com.example.financebudgetingapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView, incomeTV, expenseTV;
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
        incomeTV = findViewById(R.id.incomeTV);
        expenseTV = findViewById(R.id.expenseTV);

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        if (!savedUsername.isEmpty()) {
            usernameTextView.setText("Hi, " + savedUsername);
        }

        SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve stored amounts (default to 0 if not available)
        float savedIncomeAmount = sharedPreferences1.getFloat("incomeAmount", 0);
        float savedExpenseAmount = sharedPreferences1.getFloat("expenseAmount", 0);
        incomeTV.setText(String.format(Locale.getDefault(), "%.2f", savedIncomeAmount));
        expenseTV.setText(String.format(Locale.getDefault(), "%.2f", savedExpenseAmount));

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


        // Set credit card balance
        double cardBalance = 5000.00; // Fetch balance from your data source
        moneyTextView.setText("RM" + cardBalance);

        // Populate recent activities ListView (you need to implement an adapter)
        ArrayList<String> recentActivities = fetchRecentActivities(); // Implement this
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentActivities);
        recentActivitiesListView.setAdapter(adapter);




    }

    public void showUsernamePopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Username");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = input.getText().toString();
                if (!username.isEmpty()) {
                    usernameTextView.setText("Hi, " + username);
                    // Save the entered username to SharedPreferences if needed
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    sharedPreferences.edit().putString("username", username).apply();
                } else {
                    // Show an error message if the username is empty
                    Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void showPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Amount");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", null); // Set OK button initially to null

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = builder.create(); // Create the AlertDialog

        // Add a text changed listener to the input EditText
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if the input is empty
                boolean isEmpty = s.toString().trim().isEmpty();

                // Enable or disable the OK button based on whether the input is empty
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(!isEmpty);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        // Set the positive button's click listener
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // Initially disable the OK button
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
                // Set the click listener for the OK button
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount = input.getText().toString();
                        // Convert the entered amount to a float
                        float enteredAmount = Float.parseFloat(amount);

                        if (!amount.isEmpty()) { // Check if the amount is not empty
                            try {
                                if (view.getId() == R.id.incomeBox) {
                                    incomeTV.setText("RM " + amount);
                                    SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    sharedPreferences1.edit().putFloat("incomeAmount", enteredAmount).apply();
                                } else if (view.getId() == R.id.expenseBox) {
                                    expenseTV.setText("RM " + amount);
                                    SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    sharedPreferences1.edit().putFloat("expenseAmount", enteredAmount).apply();
                                }
                                alertDialog.dismiss(); // Close the dialog after setting the amount
                            } catch (NumberFormatException e) {
                                // Show an error message if the entered amount is not a valid number
                                Toast.makeText(MainActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        alertDialog.show(); // Show the AlertDialog
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