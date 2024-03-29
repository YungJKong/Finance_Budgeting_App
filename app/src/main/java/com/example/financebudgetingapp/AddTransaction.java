package com.example.financebudgetingapp;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

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
    private String bt_category="";
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
                if(type=="Incomes"){
                    Intent intent = new Intent(AddTransaction.this, IncomesButton.class);
                    startActivityForResult(intent, 1);
                }
                else if(type=="Expenses"){
                    Intent intent = new Intent(AddTransaction.this, ExpensesButton.class);
                    startActivityForResult(intent, 2);
                }
                else if(type=="Savings"){
                    Intent intent = new Intent(AddTransaction.this, ExpensesButton.class);
                    startActivityForResult(intent, 2);
                }
                else{

                }

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
                if (sMoney.isEmpty()) {
                    Toast.makeText(AddTransaction.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                } else if (category.isEmpty()) {
                    Toast.makeText(AddTransaction.this, "Please choose a category", Toast.LENGTH_SHORT).show();
                } else if(date.isEmpty()){
                    Toast.makeText(AddTransaction.this, "Please choose a date", Toast.LENGTH_SHORT).show();
                }else if(note.isEmpty()) {
                    note="";
                    money = Float.parseFloat(sMoney);
                    mySQLiteAdapter.openToWrite();
                    mySQLiteAdapter.insert(type, money, wallet, category, note, date);
                    mySQLiteAdapter.close();
                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTransaction.this, Transaction.class));
                }
                else {
                    // SQL Adapter Open To Write
                    money = Float.parseFloat(sMoney);
                    mySQLiteAdapter.openToWrite();
                    mySQLiteAdapter.insert(type, money, wallet, category, note, date);
                    mySQLiteAdapter.close();
                    Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTransaction.this, Transaction.class));
                }



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
            categoryButton.setText("Category");
            category="";
        }
        else if (activeButton == btnExpenses){
            type = "Expenses";
            categoryButton.setText("Category");
            category="";
        }
        else if (activeButton == btnSavings){
            type = "Savings";
            categoryButton.setText("Category");
            category="";
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
                        noteButton.setText(enteredText);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("incomesText")) {
                String incomesText = data.getStringExtra("incomesText");
                category = incomesText;
                categoryButton.setText(incomesText);
            }
        }
        else if((requestCode == 2 && resultCode == Activity.RESULT_OK)) {
            if (data != null && data.hasExtra("expensesText")) {
                String expensesText = data.getStringExtra("expensesText");
                category = expensesText;
                categoryButton.setText(expensesText);
            }
        }
    }
}