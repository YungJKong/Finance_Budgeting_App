package com.example.financebudgetingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {
    private LinearLayout btnHome, btnAct, btnStat;
    private DatePicker monthDatePicker;
    private Button buttonStats;
    private SQLiteAdapter mySQLiteAdapter;
    private TextView income, expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        monthDatePicker = findViewById(R.id.monthDatePicker);
        income = findViewById(R.id.incomeView);
        expenses = findViewById(R.id.expensesView);
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();

        buttonStats = findViewById(R.id.buttonStats);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedYear = monthDatePicker.getYear();
                int selectedMonth = monthDatePicker.getMonth() + 1;
                retrieveDataForMonthAndYear(selectedYear, selectedMonth);


            }
        });



        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(Statistics.this, MainActivity.class));
            }
        });

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Activities button click here
                // For example: startActivity(new Intent(MainActivity.this, ActivitiesActivity.class));
                startActivity(new Intent(Statistics.this, com.example.financebudgetingapp.Transaction.class));
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Statistics button click here
                // For example: startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                startActivity(new Intent(Statistics.this, com.example.financebudgetingapp.Statistics.class));
            }
        });
    }
    private void retrieveDataForMonthAndYear(int year, int month) {
        // Assuming you have a method in your SQLiteAdapter class to retrieve data for a specific month and year
        // You can use mySQLiteAdapter.methodToRetrieveDataForMonthAndYear(year, month) here
        // This method should query your database and return the relevant data for the specified year and month

        // Example:
        float totalIncomes = mySQLiteAdapter.queryTotalIncomeForMonthAndYear(year, month);
        float totalExpenses = mySQLiteAdapter.queryTotalExpenseForMonthAndYear(year, month);
        income.setText(String.format("RM %.2f", totalIncomes));
        expenses.setText(String.format("RM %.2f", totalExpenses));

    }


}