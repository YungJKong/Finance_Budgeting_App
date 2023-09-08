package com.example.financebudgetingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ExpensesButton extends AppCompatActivity {
    private Button other;
    private ImageView btnBack;
    private Button button1, button2,button3,button4,button5,button6,button7,button8,button9,
            button10,button11,button12,button13,button14,button15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_button);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);
        button13 = findViewById(R.id.button13);
        button14 = findViewById(R.id.button14);
        button15 = findViewById(R.id.button15);

        Drawable drawable1 = getResources().getDrawable(R.drawable.food);
        Drawable drawable2 = getResources().getDrawable(R.drawable.entertainment);
        Drawable drawable3 = getResources().getDrawable(R.drawable.transport);
        Drawable drawable4 = getResources().getDrawable(R.drawable.utility);
        Drawable drawable5 = getResources().getDrawable(R.drawable.care);
        Drawable drawable6 = getResources().getDrawable(R.drawable.healthcare);
        Drawable drawable7 = getResources().getDrawable(R.drawable.education);
        Drawable drawable8 = getResources().getDrawable(R.drawable.debt);
        Drawable drawable9 = getResources().getDrawable(R.drawable.tax);
        Drawable drawable10 = getResources().getDrawable(R.drawable.insurance);
        Drawable drawable11 = getResources().getDrawable(R.drawable.topup);
        Drawable drawable12 = getResources().getDrawable(R.drawable.wifi);
        Drawable drawable13 = getResources().getDrawable(R.drawable.car);
        Drawable drawable14 = getResources().getDrawable(R.drawable.pet);
        Drawable drawable15 = getResources().getDrawable(R.drawable.social);


        // Set the bounds (size) of the drawable
        drawable1.setBounds(0, 0, 100, 100);
        drawable2.setBounds(0, 0, 100, 100);
        drawable3.setBounds(0, 0, 100, 100);
        drawable4.setBounds(0, 0, 100, 100);
        drawable5.setBounds(0, 0, 100, 100);
        drawable6.setBounds(0, 0, 100, 100);
        drawable7.setBounds(0, 0, 100, 100);
        drawable8.setBounds(0, 0, 100, 100);
        drawable9.setBounds(0, 0, 100, 100);
        drawable10.setBounds(0, 0, 100, 100);
        drawable11.setBounds(0, 0, 100, 100);
        drawable12.setBounds(0, 0, 100, 100);
        drawable13.setBounds(0, 0, 100, 100);
        drawable14.setBounds(0, 0, 100, 100);
        drawable15.setBounds(0, 0, 100, 100);

        // Set the drawable on the button
        button1.setCompoundDrawables(null, drawable1, null, null);
        button2.setCompoundDrawables(null, drawable2, null, null);
        button3.setCompoundDrawables(null, drawable3, null, null);
        button4.setCompoundDrawables(null, drawable4, null, null);
        button5.setCompoundDrawables(null, drawable5, null, null);
        button6.setCompoundDrawables(null, drawable6, null, null);
        button7.setCompoundDrawables(null, drawable7, null, null);
        button8.setCompoundDrawables(null, drawable8, null, null);
        button9.setCompoundDrawables(null, drawable9, null, null);
        button10.setCompoundDrawables(null, drawable10, null, null);
        button11.setCompoundDrawables(null, drawable11, null, null);
        button12.setCompoundDrawables(null, drawable12, null, null);
        button13.setCompoundDrawables(null, drawable13, null, null);
        button14.setCompoundDrawables(null, drawable14, null, null);
        button15.setCompoundDrawables(null, drawable15, null, null);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(ExpensesButton.this, AddTransaction.class));
            }
        });

        other = findViewById(R.id.button16);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ExpensesButton.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.input_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText inputEditText = dialogView.findViewById(R.id.inputEditText);

                dialogBuilder.setTitle("Enter Category")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text ="";
                                text = inputEditText.getText().toString();
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("expensesText", text);

                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

    }
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();

        // Create an Intent to return the result
        Intent resultIntent = new Intent();
        resultIntent.putExtra("expensesText", buttonText);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}