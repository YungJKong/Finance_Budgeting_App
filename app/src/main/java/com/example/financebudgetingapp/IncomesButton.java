package com.example.financebudgetingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IncomesButton extends AppCompatActivity {
    private Button other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes_button);
        other = findViewById(R.id.button10);
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