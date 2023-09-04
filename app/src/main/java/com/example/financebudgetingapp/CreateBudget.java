package com.example.financebudgetingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.financebudgetingapp.model.TransactionModel;

import java.util.List;

public class CreateBudget extends AppCompatActivity {

    private LinearLayout btnHome, btnAct, btnStat;
    private EditText categoryEditText;
    private EditText amountEditText;
    private Button createBudgetButton;
    private ImageView btnBack;

    private SQLiteAdapter sqlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget);

        categoryEditText = findViewById(R.id.categoryEditText);
        amountEditText = findViewById(R.id.amountEditText);
        createBudgetButton = findViewById(R.id.createBudgetButton);
        btnBack = findViewById(R.id.btnbudgetBack);

        sqlAdapter = new SQLiteAdapter(this);
        sqlAdapter.openToWrite();

        createBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categoryEditText.getText().toString();
                double amount = Double.parseDouble(amountEditText.getText().toString());
                double left = calculateBudgetLeft(category, amount);

                //sqlAdapter.openToWrite();
                long result = sqlAdapter.insertBudget(category, amount, left);

                if (result != -1) {
                    Toast.makeText(CreateBudget.this, "Budget created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateBudget.this, BudgetList.class));
                } else {
                    Toast.makeText(CreateBudget.this, "Failed to create budget", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(CreateBudget.this, BudgetList.class));
            }
        });

        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(CreateBudget.this, MainActivity.class));
            }
        });

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Activities button click here
                // For example: startActivity(new Intent(MainActivity.this, ActivitiesActivity.class));
                startActivity(new Intent(CreateBudget.this, com.example.financebudgetingapp.Transaction.class));
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Statistics button click here
                // For example: startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                startActivity(new Intent(CreateBudget.this, com.example.financebudgetingapp.Statistics.class));
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlAdapter.close();
    }

    public double calculateBudgetLeft(String category, double budget) {
        // Retrieve the budget for the specified category
        //double budget = getBudgetForCategory(category);

        // Retrieve transactions for the specified category
        List<TransactionModel> transactions = sqlAdapter.getTransactionsByCategory(category);

        Log.d("TransactionData2", "Boolean: " + transactions.isEmpty());

        double totalExpenses = 0;

        for (TransactionModel transaction : transactions) {
            if (transaction.getType().equals("Expenses")) {
                Log.d("TransactionData3", "Category: " + transaction.getCategory() + ", Type: " + transaction.getType() + ", Money: " + transaction.getAmount());
                totalExpenses += transaction.getAmount();
            }
        }

        // Calculate the budget left
        return budget - totalExpenses;
    }


}