package com.example.financebudgetingapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.Calendar;

public class AddTransaction extends AppCompatActivity {
    private Button addButton;
    private String enteredText = "Note";
    private Button categoryButton;
    private Button btnIncomes, btnExpenses, btnSavings;
    private Button btnBank, btnCredit, btnEwallet;
    private Button dateButton;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        btnIncomes = findViewById(R.id.btnIncomes);
        btnExpenses = findViewById(R.id.btnExpenses);
        btnSavings = findViewById(R.id.btnSavings);

        btnIncomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(btnIncomes);
            }
        });

        btnExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(btnExpenses);
            }
        });

        btnSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(btnSavings);
            }
        });

        btnBank = findViewById(R.id.btnBank);
        btnCredit = findViewById(R.id.btnCredit);
        btnEwallet = findViewById(R.id.btnEwallet);

        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton2(btnBank);
            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton2(btnCredit);
            }
        });

        btnEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton2(btnEwallet);
            }
        });

        //Button for CATEGORY
        categoryButton = findViewById(R.id.categoryButton);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        //Button for NOTE
        addButton = findViewById(R.id.noteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        //Button for DATE
        dateButton = findViewById(R.id.dateButton);

        calendar = Calendar.getInstance();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }
    private void setActiveButton(Button activeButton) {
        btnIncomes.setEnabled(activeButton != btnIncomes);
        btnExpenses.setEnabled(activeButton != btnExpenses);
        btnSavings.setEnabled(activeButton != btnSavings);
    }

    private void setActiveButton2(Button activeButton) {
        btnBank.setEnabled(activeButton != btnBank);
        btnCredit.setEnabled(activeButton != btnCredit);
        btnEwallet.setEnabled(activeButton != btnEwallet);
    }

    private void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.category_transaction, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedCategory = item.getTitle().toString();
                categoryButton.setText(selectedCategory);
                return true;
            }
        });

        popupMenu.show();
    }
    private void showInputDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText inputEditText = dialogView.findViewById(R.id.inputEditText);

        dialogBuilder.setTitle("Enter Content")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enteredText = inputEditText.getText().toString();
                        addButton.setText(enteredText);
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year1;
                    dateButton.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}