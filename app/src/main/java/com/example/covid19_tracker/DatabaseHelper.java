package com.example.covid19_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Table_name = "symptom_log";
    private static final String col1 = "date";
    private static final String col2 = "symptoms";
    public DatabaseHelper(Context context){
        super(context, Table_name, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + col2 + "TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(col2, item);
        Log.d("DatabaseHelper", "AddData: Adding" + item + "to" + Table_name);
        long result = db.insert(Table_name, null, CV);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
}
