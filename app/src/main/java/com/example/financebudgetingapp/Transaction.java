package com.example.financebudgetingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Transaction extends AppCompatActivity {

    private ImageButton btnHome, btnAct, btnStat;

    private Button add;
    private TextView viewTransaction;
    private SQLiteAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        add = findViewById(R.id.addTran);
        viewTransaction = findViewById(R.id.viewTransaction);
        btnHome = findViewById(R.id.btnHome);
        btnAct = findViewById(R.id.btnAct);
        btnStat = findViewById(R.id.btnStat);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Transaction.this, AddTransaction.class));

            }
        });

        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        String contentRead =
                mySQLiteAdapter.queueAll();
        mySQLiteAdapter.close();

        viewTransaction.setText(contentRead);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(Transaction.this, MainActivity.class));
            }
        });

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Activities button click here
                // For example: startActivity(new Intent(MainActivity.this, ActivitiesActivity.class));
                startActivity(new Intent(Transaction.this, com.example.financebudgetingapp.Transaction.class));
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Statistics button click here
                // For example: startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            }
        });



    }
}