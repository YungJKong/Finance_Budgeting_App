package com.example.financebudgetingapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView, incomeTV, expenseTV, allTV;
    private TextView moneyTextView;
    private LinearLayout activityContainer;
    private LinearLayout btnHome, btnAct, btnStat;
    private RelativeLayout balanceContainer;
    private RelativeLayout budgetContainer;
    private ImageView balanceIcon;
    private SQLiteAdapter mySQLiteAdapter;
    private ImageView userIcon;
    // Define a constant for the request code
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTextView = findViewById(R.id.usernameTextView);
        moneyTextView = findViewById(R.id.moneyTextView);
        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);
        incomeTV = findViewById(R.id.incomeTV);
        expenseTV = findViewById(R.id.expenseTV);
        balanceContainer = findViewById(R.id.balanceContainer);
        balanceIcon =findViewById(R.id.balanceIcon);
        budgetContainer = findViewById(R.id.budgetContainer);
        activityContainer = findViewById(R.id.activityContainer);
        allTV = findViewById(R.id.allTV);
        userIcon = findViewById(R.id.userIcon);

        // Initialize your SQLiteAdapter
        mySQLiteAdapter = new SQLiteAdapter(this);

        // Open the database for reading
        mySQLiteAdapter.openToRead();

        // Query the database to get the sum of income and expense
        float totalIncome = mySQLiteAdapter.queryTotalIncome();
        float totalExpense = mySQLiteAdapter.queryTotalExpense();

        // Close the database after querying
        mySQLiteAdapter.close();

        // Display the income and expense values
        incomeTV.setText(String.format(Locale.getDefault(), "%.2f", totalIncome));
        expenseTV.setText(String.format(Locale.getDefault(), "%.2f", totalExpense));

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        if (!savedUsername.isEmpty()) {
            usernameTextView.setText("Hi, " + savedUsername);
        }

        //SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        double incomes = mySQLiteAdapter.incomeAll();
        double expenses = mySQLiteAdapter.expensesAll();
        mySQLiteAdapter.populateData(activityContainer);
        mySQLiteAdapter.close();
        // Retrieve stored amounts (default to 0 if not available)
        double savedIncomeAmount = incomes;
        double savedExpenseAmount = expenses;
        incomeTV.setText(String.format(Locale.getDefault(), "%.2f", savedIncomeAmount));
        expenseTV.setText(String.format(Locale.getDefault(), "%.2f", savedExpenseAmount));

        //mySQLiteAdapter.populateData(activityContainer);

        allTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                // You can navigate to a new page using Intent
                startActivity(new Intent(MainActivity.this, Transaction.class));
            }
        });

        balanceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                // You can navigate to a new page using Intent
                startActivity(new Intent(MainActivity.this, CreditCard.class));
            }
        });

        budgetContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                // You can navigate to a new page using Intent
                startActivity(new Intent(MainActivity.this, BudgetList.class));
            }
        });

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
                startActivity(new Intent(MainActivity.this, com.example.financebudgetingapp.Statistics.class));
            }
        });


        // Set credit card balance
        double cardBalance = 5000.00; // Fetch balance from your data source
        moneyTextView.setText("RM" + cardBalance);



        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        // Load the saved image URI and set it to userIcon
        loadSavedImageUri();
    }

    // Method to open the image picker
    private void selectImageFromGallery() {
        // Create an intent to open the image picker
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // Specify the type of content (images in this case)

        // Start the intent to pick an image
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    // Override onActivityResult to handle the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();

            // Now you can use this URI to set the profile picture or do other operations
            // For example, you can load the image into an ImageView
            ImageView userIcon = findViewById(R.id.userIcon);
            userIcon.setImageURI(selectedImageUri);

            // You can also save the selected image URI for future use (e.g., saving it in SharedPreferences)
            saveImageUriToSharedPreferences(selectedImageUri);
        }
    }

    // Method to save the image URI to SharedPreferences
    private void saveImageUriToSharedPreferences(Uri imageUri) {
        SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("imageUri", imageUri.toString());
        editor.apply();
    }
    // Method to load the saved image URI and set it to userIcon
    private void loadSavedImageUri() {
        SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedImageUriString = sharedPreferences1.getString("imageUri", null);

        if (savedImageUriString != null) {
            try {
                Uri savedImageUri = Uri.parse(savedImageUriString);
                userIcon.setImageURI(savedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ImageLoadingError", "Error loading saved image URI: " + e.getMessage());
                // Handle the exception gracefully, such as displaying a default image
                userIcon.setImageResource(R.drawable.user); // Set the default image
            }
        } else {
            // Handle the case where there's no saved image URI (e.g., display a default image)
            userIcon.setImageResource(R.drawable.user); // Set the default image
        }
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

}