package com.example.financebudgetingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.financebudgetingapp.model.Budget;

import java.util.List;

public class BudgetList extends AppCompatActivity {

    private ListView budgetListView;
    private SQLiteAdapter sqlAdapter;
    private Button budgetButton;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_list);

        budgetListView = findViewById(R.id.budgetListView);
        sqlAdapter = new SQLiteAdapter(this);
        budgetButton = findViewById(R.id.BudgetButton);
        btnBack = findViewById(R.id.btnbudgetlistBack);

        sqlAdapter.openToRead();

        List<Budget> budgets = getBudgetsFromDatabase();

        BudgetListAdapter adapter = new BudgetListAdapter(this, budgets);
        budgetListView.setAdapter(adapter);

        budgetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(BudgetList.this, CreateBudget.class));
                }
            }
        );

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(BudgetList.this, MainActivity.class));
            }
        });
    }

    private List<Budget> getBudgetsFromDatabase() {
        SQLiteAdapter mySQLiteAdapter = new SQLiteAdapter(this);
        return mySQLiteAdapter.getBudgets();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlAdapter.close();
    }

}