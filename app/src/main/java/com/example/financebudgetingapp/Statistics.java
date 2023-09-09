package com.example.financebudgetingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Calendar;
import java.util.List;

public class Statistics extends AppCompatActivity {
    private LinearLayout btnHome, btnAct, btnStat;
    private Button buttonStats;
    private SQLiteAdapter mySQLiteAdapter;
    private TextView income, expenses;
    private LinearLayout topCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        topCategory = findViewById(R.id.topCategory);
        income = findViewById(R.id.incomeView);
        expenses = findViewById(R.id.expensesView);
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        buttonStats = findViewById(R.id.buttonStats);
        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);

        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();

        //mySQLiteAdapter.close();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        String currentDate = currentYear + "-" + String.format("%02d", currentMonth);
        buttonStats.setText(currentDate);
        retrieveDataForMonthAndYear(currentYear, currentMonth);


        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and show the DatePicker dialog when the button is clicked
                createDatePickerDialog();
            }
        });

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
                startActivity(new Intent(Statistics.this, com.example.financebudgetingapp.Transaction.class));
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Statistics button click here
                startActivity(new Intent(Statistics.this, com.example.financebudgetingapp.Statistics.class));
            }
        });



    }
    private void createDatePickerDialog() {
        // Initialize the DatePicker dialog
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select a Date");
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_picker_dialog, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.monthDatePicker);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int selectedYear = datePicker.getYear();
                int selectedMonth = datePicker.getMonth() + 1;
                retrieveDataForMonthAndYear(selectedYear, selectedMonth);
                String currentDate = selectedYear + "-" + String.format("%02d", selectedMonth);
                buttonStats.setText(currentDate);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(Statistics.this, "Date selection canceled", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the DatePicker dialog
        dialogBuilder.create().show();
    }

    private void retrieveDataForMonthAndYear(int year, int month) {

        // Use mySQLiteAdapter
        // Query SQLite database and return the relevant data for the specified year and month

        float totalIncomes = mySQLiteAdapter.queryTotalIncomeForMonthAndYear(year, month);
        float totalExpenses = mySQLiteAdapter.queryTotalExpenseForMonthAndYear(year, month);
        mySQLiteAdapter.topCat(topCategory, year , month);
        createPie(year,month);
        income.setText(String.format("RM %.2f", totalIncomes));
        expenses.setText(String.format("RM %.2f", totalExpenses));

    }

    private void createPie(int year, int month) {
        // Find the PieChart view by its ID
        PieChart pieChart = findViewById(R.id.pieChart);
        List<PieEntry> pieEntries = mySQLiteAdapter.getExpenseSumByCategory(year,month);
        PieDataSet dataSet = new PieDataSet(pieEntries, "Expense Categories");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);

        // Set data to the PieChart
        pieChart.setData(data);

        // Customize the PieChart appearance
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Expense Categories");
        pieChart.setCenterTextSize(15f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);


        pieChart.invalidate();

    }


}