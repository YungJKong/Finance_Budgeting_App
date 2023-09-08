package com.example.financebudgetingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class IncomesButton extends AppCompatActivity {
    private Button other;
    private ImageView btnBack;
    private Button button1, button2,button3,button4,button5,button6,button7,button8,button9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes_button);
        other = findViewById(R.id.button10);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        Drawable drawable1 = getResources().getDrawable(R.drawable.salary);
        Drawable drawable2 = getResources().getDrawable(R.drawable.investment);
        Drawable drawable3 = getResources().getDrawable(R.drawable.retirement);
        Drawable drawable4 = getResources().getDrawable(R.drawable.business);
        Drawable drawable5 = getResources().getDrawable(R.drawable.rent);
        Drawable drawable6 = getResources().getDrawable(R.drawable.government);
        Drawable drawable7 = getResources().getDrawable(R.drawable.scholarship);
        Drawable drawable8 = getResources().getDrawable(R.drawable.lottery);
        Drawable drawable9 = getResources().getDrawable(R.drawable.car_rental);


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




        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(IncomesButton.this, AddTransaction.class));
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(IncomesButton.this);
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
                                resultIntent.putExtra("incomesText", text);

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
        resultIntent.putExtra("incomesText", buttonText);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}