package com.example.financebudgetingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    private static final String SCRIPT_CREATE_DATABASE ="CREATE TABLE " + MYDATABASE_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_MONEY + " REAL, " +
            COLUMN_WALLET + " TEXT, " +
            COLUMN_CATEGORY + " TEXT, " +
            COLUMN_NOTE + " TEXT, " +
            COLUMN_DATE + " TEXT)";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public SQLiteAdapter(Context c){
        context = c;
    }
    public SQLiteAdapter openToRead() throws
            android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }
    public SQLiteAdapter openToWrite() throws
            android.database.SQLException {
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
        return sqLiteDatabase.insert(MYDATABASE_TABLE, null,
                contentValues);
    }
    public int deleteAll() {
        return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);}

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

            tv_category.setTextSize(18);
            tv_money.setTextSize(18);
            tv_note.setTextSize(18);
            tv_date.setTextSize(18);
            tv_category.setText(category);
            if ("Incomes".equals(type)) {
                tv_money.setTextColor(Color.GREEN);
                tv_money.setText(String.format("%.2f", money));
            } else if ("Expenses".equals(type) || "Savings".equals(type)) {
                tv_money.setTextColor(Color.RED);
                tv_money.setText("-"+String.format("%.2f", money));
            }



            tv_money.setGravity(Gravity.END);
            tv_note.setText(note);
            tv_date.setText(date);
            tv_date.setGravity(Gravity.END);


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

    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SCRIPT_CREATE_DATABASE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}