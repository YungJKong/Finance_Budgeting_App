package com.example.financebudgetingapp;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.financebudgetingapp.model.Budget;
import com.example.financebudgetingapp.model.TransactionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SQLiteAdapter extends AppCompatActivity {
    public static final String MYDATABASE_NAME = "MY_DATABASE";
    public static final String MYDATABASE_TABLE = "DB_TRANSACTION";
    public static final int MYDATABASE_VERSION = 1;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_WALLET = "wallet";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";

    public static final String BUDGET_TABLE_NAME = "DB_BUDGET";
    public static final String COLUMN_BUDGET_CATEGORY = "category";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_LEFT = "budgetleft";

    private static final String SCRIPT_CREATE_DATABASE ="CREATE TABLE " + MYDATABASE_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_MONEY + " REAL, " +
            COLUMN_WALLET + " TEXT, " +
            COLUMN_CATEGORY + " TEXT, " +
            COLUMN_NOTE + " TEXT, " +
            COLUMN_DATE + " TEXT)";

    private static final String SCRIPT_CREATE_BUDGET_TABLE = "CREATE TABLE " + BUDGET_TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BUDGET_CATEGORY + " TEXT, " +
            COLUMN_AMOUNT + " REAL, " +
            COLUMN_LEFT + " REAL DEFAULT 0)";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public float queryTotalIncome() {
        float totalIncome = 0;

        String[] columns = new String[] {
                "SUM(" + COLUMN_MONEY + ")"
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                COLUMN_TYPE + "=?",
                new String[]{"Income"},
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalIncome = cursor.getFloat(0);
            }
            cursor.close();
        }

        return totalIncome;
    }


    public float queryTotalExpense() {
        float totalExpense = 0;

        String[] columns = new String[] {
                "SUM(" + COLUMN_MONEY + ")"
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                COLUMN_TYPE + "=?",
                new String[]{"Expense"},
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalExpense = cursor.getFloat(0);
            }
            cursor.close();
        }

        return totalExpense;
    }

    public SQLiteAdapter(Context c){
        context = c;
    }

    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteHelper.close();
    }

    public long insert(String type, float money, String wallet, String category, String note, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_MONEY, money);
        contentValues.put(COLUMN_WALLET, wallet);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_NOTE, note);
        contentValues.put(COLUMN_DATE, date);

        // update budget real time
        if (type.equals("Expenses")) {
            updateBudgetLeft(category, money);
        }

        return sqLiteDatabase.insert(MYDATABASE_TABLE, null,
                contentValues);
    }

    public int deleteAll() {
        return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
    }

    public void populateDataLayout(LinearLayout dataLayout) {
        String[] columns = new String[] {
                COLUMN_TYPE,
                COLUMN_CATEGORY,
                COLUMN_MONEY,
                COLUMN_NOTE,
                COLUMN_DATE
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        int index_TYPE = cursor.getColumnIndex(COLUMN_TYPE);
        int index_CATEGORY = cursor.getColumnIndex(COLUMN_CATEGORY);
        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);
        int index_NOTE = cursor.getColumnIndex(COLUMN_NOTE);
        int index_DATE = cursor.getColumnIndex(COLUMN_DATE);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String type = cursor.getString(index_TYPE);
            String category = cursor.getString(index_CATEGORY);
            double money = cursor.getDouble(index_MONEY);
            String note = cursor.getString(index_NOTE);
            String date = cursor.getString(index_DATE);

            // Create a separator line
            View separator = new View(context);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1)); // Adjust the height as needed
            separator.setBackgroundColor(Color.GRAY); // Set the separator color

            // Add the separator to the dataLayout
            dataLayout.addView(separator);


            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);


            LinearLayout ll_left = new LinearLayout(context);
            ll_left.setOrientation(LinearLayout.VERTICAL);
            ll_left.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));


            LinearLayout ll_right = new LinearLayout(context);
            ll_right.setOrientation(LinearLayout.VERTICAL);
            ll_right.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));


            TextView tv_category = new TextView(context);
            TextView tv_money = new TextView(context);
            TextView tv_note = new TextView(context);
            TextView tv_date = new TextView(context);
            tv_money.setTypeface(null, Typeface.BOLD);
            tv_category.setTextSize(18);
            tv_money.setTextSize(18);
            tv_note.setTextSize(18);
            tv_date.setTextSize(18);
            tv_category.setText(category);
            if ("Incomes".equals(type)) {
                tv_money.setTextColor(Color.parseColor("#28B463"));
                tv_money.setText(String.format("%.2f", money));
            } else if ("Expenses".equals(type) || "Savings".equals(type)) {
                tv_money.setTextColor(Color.RED);
                tv_money.setText("-"+String.format("%.2f", money));
            }

            tv_money.setGravity(Gravity.END);
            tv_note.setText(note);
            tv_date.setText(date);
            tv_date.setGravity(Gravity.END);

            // Create layout parameters for TextViews with margins
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            );
            layoutParams.setMargins(0, 16, 0, 8); // Left, Top, Right, Bottom margins (adjust values as needed)

// Apply layout parameters to TextViews
            tv_category.setLayoutParams(layoutParams);
            tv_money.setLayoutParams(layoutParams);
            tv_note.setLayoutParams(layoutParams);
            tv_date.setLayoutParams(layoutParams);

            ll_left.addView(tv_category);
            ll_left.addView(tv_note);
            ll_right.addView(tv_money);
            ll_right.addView(tv_date);
            ll.addView(ll_left);
            ll.addView(ll_right);
            dataLayout.addView(ll);
            cursor.moveToNext();
        }

        cursor.close();
    }

    public void populateData(LinearLayout activityContainer) {
        String[] columns = new String[] {
                COLUMN_TYPE,
                COLUMN_CATEGORY,
                COLUMN_MONEY,
                COLUMN_DATE
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                null,
                null,
                null,
                null,
                COLUMN_DATE + " DESC", // Order by date in descending order
                "3"
        );
        int index_TYPE = cursor.getColumnIndex(COLUMN_TYPE);
        int index_CATEGORY = cursor.getColumnIndex(COLUMN_CATEGORY);
        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);
        int index_DATE = cursor.getColumnIndex(COLUMN_DATE);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String type = cursor.getString(index_TYPE);
            String category = cursor.getString(index_CATEGORY);
            double money = cursor.getDouble(index_MONEY);
            String date = cursor.getString(index_DATE);

            View separator = new View(context);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1)); // Adjust the height as needed
            separator.setBackgroundColor(Color.GRAY); // Set the separator color

            // Add the separator to the dataLayout
            activityContainer.addView(separator);

            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout ll_left = new LinearLayout(context);
            ll_left.setOrientation(LinearLayout.VERTICAL);
            ll_left.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

            LinearLayout ll_right = new LinearLayout(context);
            ll_right.setOrientation(LinearLayout.VERTICAL);
            ll_right.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

            TextView tv_category = new TextView(context);
            TextView tv_money = new TextView(context);
            TextView tv_date = new TextView(context);
            tv_money.setTypeface(null, Typeface.BOLD);
            tv_category.setTextSize(22);
            tv_category.setTypeface(null, Typeface.BOLD);
            tv_money.setTextSize(18);
            tv_date.setTextSize(18);
            tv_category.setText(category);
            //tv_money.setText(String.format("RM %.2f", money));
            if ("Incomes".equals(type)) {
                tv_money.setTextColor(Color.parseColor("#28B463"));
                tv_money.setText(String.format("RM %.2f", money));
            } else if ("Expenses".equals(type) || "Savings".equals(type)) {
                tv_money.setTextColor(Color.RED);
                tv_money.setText("-"+String.format("RM %.2f", money));
            }
            tv_money.setGravity(Gravity.END);
            tv_date.setText(date);
            tv_date.setGravity(Gravity.END);

            tv_category.setTextColor(Color.parseColor("#2980B9"));

            ll_left.addView(tv_category);
            ll_right.addView(tv_money);
            ll_right.addView(tv_date);
            ll.addView(ll_left);
            ll.addView(ll_right);
            activityContainer.addView(ll);
            cursor.moveToNext();
        }

        cursor.close();
    }

    public void topCat(LinearLayout topCategory, int year, int month) {
        String[] columns = new String[] {
                COLUMN_TYPE,
                COLUMN_CATEGORY,
                COLUMN_MONEY,
        };

        String selection = COLUMN_TYPE + " = ? AND SUBSTR(" + COLUMN_DATE + ", 1, 7) = ?";
        String[] selectionArgs = { "Expenses", String.format(Locale.US, "%04d-%02d", year, month) };


        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null, // Order by money
                null,
                COLUMN_MONEY + " DESC"
        );

        int index_TYPE = cursor.getColumnIndex(COLUMN_TYPE);
        int index_CATEGORY = cursor.getColumnIndex(COLUMN_CATEGORY);
        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);

        cursor.moveToFirst();
        int count = 0; // Counter for the top 5 categories
        topCategory.removeAllViews();
        while (!cursor.isAfterLast() && count < 5) {
            String type = cursor.getString(index_TYPE);
            String category = cursor.getString(index_CATEGORY);
            double money = cursor.getDouble(index_MONEY);

            if ("Expenses".equals(type)) {
                // Create a separator line
                View separator = new View(context);
                separator.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1)); // Adjust the height as needed
                separator.setBackgroundColor(Color.GRAY); // Set the separator color

                // Add the separator to the dataLayout
                topCategory.addView(separator);

                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout ll_left = new LinearLayout(context);
                ll_left.setOrientation(LinearLayout.VERTICAL);
                ll_left.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

                LinearLayout ll_right = new LinearLayout(context);
                ll_right.setOrientation(LinearLayout.VERTICAL);
                ll_right.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

                TextView tv_category = new TextView(context);
                TextView tv_money = new TextView(context);
                tv_money.setTypeface(null, Typeface.BOLD);
                tv_category.setTextSize(18);
                tv_money.setTextSize(18);
                tv_category.setText(category);
                tv_category.setTextColor(Color.BLACK);
                tv_money.setTextColor(Color.RED);
                tv_money.setText("-" + String.format("%.2f", money));

                tv_money.setGravity(Gravity.END);


                // Create layout parameters for TextViews with margins
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // Width
                        LinearLayout.LayoutParams.WRAP_CONTENT  // Height
                );
                layoutParams.setMargins(0, 16, 0, 8); // Left, Top, Right, Bottom margins (adjust values as needed)

                // Apply layout parameters to TextViews
                tv_category.setLayoutParams(layoutParams);
                tv_money.setLayoutParams(layoutParams);

                ll_left.addView(tv_category);
                ll_right.addView(tv_money);

                ll.addView(ll_left);
                ll.addView(ll_right);
                topCategory.addView(ll);
            }

            cursor.moveToNext();
        }

        cursor.close();
    }


    public double incomeAll() {
        String[] columns = new String[] {
                COLUMN_MONEY,
                COLUMN_TYPE,
        };
        String selection = COLUMN_TYPE + " = ?";
        String[] selectionArgs = new String[] { "Incomes" };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);
        double money = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            money += cursor.getDouble(index_MONEY);
            cursor.moveToNext();
        }
        cursor.close();
        return money;
    }

    public double expensesAll() {
        String[] columns = new String[] {
                COLUMN_MONEY,
                COLUMN_TYPE,
        };
        String selection = COLUMN_TYPE + " = ?";
        String[] selectionArgs = new String[] { "Expenses" };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);
        double money = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            money += cursor.getDouble(index_MONEY);
            cursor.moveToNext();
        }
        cursor.close();
        return money;
    }

    public float queryTotalIncomeForMonthAndYear(int year, int month) {
        float totalIncome = 0;

        // Define the selection clause to filter by month and year
        String selection = COLUMN_TYPE + " = ? AND SUBSTR(" + COLUMN_DATE + ", 1, 7) = ?";
        String[] selectionArgs = { "Incomes", String.format(Locale.US, "%04d-%02d", year, month) };

        String[] columns = new String[] {
                "SUM(" + COLUMN_MONEY + ")"
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                totalIncome = cursor.getFloat(0);;
            }
            cursor.close();
        }

        return totalIncome;
    }
    public float queryTotalExpenseForMonthAndYear(int year, int month) {
        float totalExpenses = 0;

        // Define the selection clause to filter by month and year
        String selection = COLUMN_TYPE + " = ? AND SUBSTR(" + COLUMN_DATE + ", 1, 7) = ?";
        String[] selectionArgs = { "Expenses", String.format(Locale.US, "%04d-%02d", year, month) };

        String[] columns = new String[] {
                "SUM(" + COLUMN_MONEY + ")"
        };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                totalExpenses = cursor.getFloat(0);
            }
            cursor.close();
        }
        return totalExpenses;
    }


    public long insertBudget(String category, double amount, double left) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BUDGET_CATEGORY, category);
        contentValues.put(COLUMN_AMOUNT, amount);
        contentValues.put(COLUMN_LEFT, left);

        // Insert the budget
        return sqLiteDatabase.insert(BUDGET_TABLE_NAME, null, contentValues);
    }

    public List<Budget> getBudgets() {
        List<Budget> budgetList = new ArrayList<>();

        // Ensure that the database is open for reading
        openToRead();

        String[] columns = new String[] {
                COLUMN_BUDGET_CATEGORY,
                COLUMN_AMOUNT,
                COLUMN_LEFT
        };

        // Query the database to fetch budget data
        Cursor cursor = sqLiteDatabase.query(
                BUDGET_TABLE_NAME,  // Replace with your actual table name
                columns,
                null,  // You can add a WHERE clause here if needed
                null,
                null,
                null,
                null
        );

        int index_MONEY = cursor.getColumnIndex(COLUMN_AMOUNT);
        int index_CATEGORY = cursor.getColumnIndex(COLUMN_BUDGET_CATEGORY);
        int index_LEFT = cursor.getColumnIndex(COLUMN_LEFT);
        double amount = 0;
        String category = "";
        double left = 0;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            amount = cursor.getDouble(index_MONEY);
            category = cursor.getString(index_CATEGORY);
            left = cursor.getDouble(index_LEFT);

            Budget budget = new Budget(category, amount, left);

            // Add the Budget object to the list
            budgetList.add(budget);
            cursor.moveToNext();
        }
        cursor.close();

        // Close the database connection
        close();

        return budgetList;
    }

    public List<TransactionModel> getTransactionsByCategory(String category) {

        openToRead();

        String[] columns = new String[] {
                COLUMN_MONEY,
                COLUMN_TYPE,
                COLUMN_CATEGORY
        };

        String selection = COLUMN_CATEGORY + " = ? AND " + COLUMN_TYPE + " = ?";
        String[] selectionArgs = new String[] { category, "Expenses" };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<TransactionModel> transactionList = new ArrayList<>();
        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);
        int index_TYPE = cursor.getColumnIndex(COLUMN_TYPE);
        double money = 0;
        String type = "";

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            money = cursor.getDouble(index_MONEY);
            type = cursor.getString(index_TYPE);

            Log.d("TransactionData", "Category: " + category + ", Type: " + type + ", Money: " + money);

            TransactionModel transaction = new TransactionModel(type, category, money);

            // Add the TransactionModel object to the list
            transactionList.add(transaction);
            cursor.moveToNext();
        }
        cursor.close();

        return transactionList;
    }

    public void updateBudgetLeft(String category, double expenseAmount) {
        //openToWrite();

        // Retrieve the current budget left for the category
        String[] budgetColumns = new String[] {
                COLUMN_AMOUNT,
                COLUMN_LEFT
        };
        String budgetSelection = COLUMN_BUDGET_CATEGORY + " = ?";
        String[] budgetSelectionArgs = new String[] { category };

        Cursor budgetCursor = sqLiteDatabase.query(
                BUDGET_TABLE_NAME,
                budgetColumns,
                budgetSelection,
                budgetSelectionArgs,
                null,
                null,
                null
        );

        if (budgetCursor != null) {
            if (budgetCursor.moveToFirst()) {
                double currentBudgetAmount = budgetCursor.getDouble(0);
                double currentBudgetLeft = budgetCursor.getDouble(1);

                // Calculate the new budget left after subtracting the expense amount
                double newBudgetLeft = currentBudgetLeft - expenseAmount;

                // Update the budget left value in the DB_BUDGET table
                ContentValues budgetValues = new ContentValues();
                budgetValues.put(COLUMN_LEFT, newBudgetLeft);

                sqLiteDatabase.update(
                        BUDGET_TABLE_NAME,
                        budgetValues,
                        budgetSelection,
                        budgetSelectionArgs
                );
            }
            budgetCursor.close();
        }

        // Close the database connection
        //close();
    }
    public List<PieEntry> getExpenseSumByCategory(int year, int month) {
        List<PieEntry> entries = new ArrayList<>();

        String[] columns = new String[] {
                COLUMN_CATEGORY,
                "SUM(" + COLUMN_MONEY + ") AS " + COLUMN_MONEY
        };

        String selection = COLUMN_TYPE + " = ? AND SUBSTR(" + COLUMN_DATE + ", 1, 7) = ?";
        String[] selectionArgs = { "Expenses", String.format(Locale.US, "%04d-%02d", year, month) };

        Cursor cursor = sqLiteDatabase.query(
                MYDATABASE_TABLE,
                columns,
                selection,
                selectionArgs,
                COLUMN_CATEGORY,
                null,
                null
        );

        int index_CATEGORY = cursor.getColumnIndex(COLUMN_CATEGORY);
        int index_MONEY = cursor.getColumnIndex(COLUMN_MONEY);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String category = cursor.getString(index_CATEGORY);
            float money = cursor.getFloat(index_MONEY);

            entries.add(new PieEntry(money, category));

            cursor.moveToNext();
        }

        cursor.close();

        return entries;
    }

    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SCRIPT_CREATE_DATABASE);
            try {
                // Execute the SQL statement to create the table
                db.execSQL(SCRIPT_CREATE_BUDGET_TABLE);
            } catch (SQLException e) {
                // Handle the exception (e.g., log the error or perform recovery actions)
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}