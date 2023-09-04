package com.example.financebudgetingapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.Calendar;

public class AddTransaction extends AppCompatActivity {
    private Button addButton;
    private String enteredText = "Note";
    private Button categoryButton, noteButton, dateButton;
    private Button btnIncomes, btnExpenses, btnSavings;
    private Button btnBank, btnCredit, btnEwallet;
    private Calendar calendar;
    private ImageView btnBack;
    private SQLiteAdapter mySQLiteAdapter;
    private String type="";
    private Float money;
    private String wallet="";
    private String category="";
    private String note="";
    private String date="";
    private EditText amountEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        btnIncomes = findViewById(R.id.btnIncomes);
        btnExpenses = findViewById(R.id.btnExpenses);
        btnSavings = findViewById(R.id.btnSavings);
        btnBank = findViewById(R.id.btnBank);
        btnCredit = findViewById(R.id.btnCredit);
        btnEwallet = findViewById(R.id.btnEwallet);
        btnBack = findViewById(R.id.btnBack);
        categoryButton = findViewById(R.id.categoryButton);
        noteButton = findViewById(R.id.noteButton);
        dateButton = findViewById(R.id.dateButton);
        addButton= findViewById(R.id.btnAdd);
        mySQLiteAdapter = new SQLiteAdapter(this);
        amountEditText = findViewById(R.id.amountEditText);

        // Load the original drawable
        Drawable originalDrawable = getResources().getDrawable(R.drawable.bank);
        Drawable originalDrawable1 = getResources().getDrawable(R.drawable.credit_card);
        Drawable originalDrawable2 = getResources().getDrawable(R.drawable.wallet);
        Drawable originalDrawable3 = getResources().getDrawable(R.drawable.calendar);
        Drawable originalDrawable4 = getResources().getDrawable(R.drawable.categories);
        Drawable originalDrawable5 = getResources().getDrawable(R.drawable.note);

    // Calculate the desired width and height for the resized drawable
        int desiredWidth = 30;  // Set your desired width in pixels
        int desiredHeight = 30; // Set your desired height in pixels
        int desiredWidth1 = 40;
        int desiredHeight1 = 40;

    // Resize the drawable
        Drawable resizedDrawable = resizeDrawable(originalDrawable, desiredWidth, desiredHeight);
        Drawable resizedDrawable1 = resizeDrawable(originalDrawable1, desiredWidth, desiredHeight);
        Drawable resizedDrawable2 = resizeDrawable(originalDrawable2, desiredWidth, desiredHeight);
        Drawable resizedDrawable3 = resizeDrawable(originalDrawable3, desiredWidth1, desiredHeight1);
        Drawable resizedDrawable4 = resizeDrawable(originalDrawable4, desiredWidth1, desiredHeight1);
        Drawable resizedDrawable5 = resizeDrawable(originalDrawable5, desiredWidth1, desiredHeight1);

    // Set the resized drawable as the top drawable for the Button
        btnBank.setCompoundDrawablesWithIntrinsicBounds(null, resizedDrawable, null, null);
        btnCredit.setCompoundDrawablesWithIntrinsicBounds(null, resizedDrawable1, null, null);
        btnEwallet.setCompoundDrawablesWithIntrinsicBounds(null, resizedDrawable2, null, null);
        categoryButton.setCompoundDrawablesWithIntrinsicBounds(resizedDrawable4, null, null, null);
        dateButton.setCompoundDrawablesWithIntrinsicBounds(resizedDrawable3, null, null, null);
        noteButton.setCompoundDrawablesWithIntrinsicBounds(resizedDrawable5, null, null, null);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Home button click here
                startActivity(new Intent(AddTransaction.this, Transaction.class));
            }
        });

        setActiveButton(btnIncomes);
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
        setActiveButton2(btnBank);
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


        noteButton.setOnClickListener(new View.OnClickListener() {
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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMoney = amountEditText.getText().toString();
                money = Float.parseFloat(sMoney);
                //SQL Adapter Open To Write
                mySQLiteAdapter.openToWrite();
                mySQLiteAdapter.insert(type,money,wallet,category,note,date);
                mySQLiteAdapter.close();

            }
        });


    }

    private Drawable resizeDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return new BitmapDrawable(getResources(), resizedBitmap);
    }


    private void setActiveButton(Button activeButton) {
        btnIncomes.setEnabled(activeButton != btnIncomes);
        btnExpenses.setEnabled(activeButton != btnExpenses);
        btnSavings.setEnabled(activeButton != btnSavings);

        if(activeButton == btnIncomes){
            type = "Incomes";
        }
        else if (activeButton == btnExpenses){
            type = "Expenses";
        }
        else if (activeButton == btnSavings){
            type = "Savings";
        }
        else{
            type = "";
        }
    }

    private void setActiveButton2(Button activeButton) {
        btnBank.setEnabled(activeButton != btnBank);
        btnCredit.setEnabled(activeButton != btnCredit);
        btnEwallet.setEnabled(activeButton != btnEwallet);


        if(activeButton == btnBank){
            wallet = "Bank";
        }
        else if (activeButton == btnCredit){
            wallet = "Credit Card";
        }
        else if (activeButton == btnEwallet){
            wallet = "E-Wallet";
        }
        else{
            wallet = "";
        }
    }

    private void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.getMenuInflater().inflate(R.menu.category_transaction, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedCategory = item.getTitle().toString();
                category = selectedCategory;
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
                        note = enteredText;
                        noteButton.setText("Note: " + enteredText);
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
                    String selectedDate = year1 + "-" + String.format("%02d", monthOfYear + 1) + "-" + dayOfMonth;
                    date = selectedDate;
                    dateButton.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}