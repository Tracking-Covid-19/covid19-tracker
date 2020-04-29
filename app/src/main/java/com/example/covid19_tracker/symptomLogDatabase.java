package com.example.covid19_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class symptomLogDatabase extends SQLiteOpenHelper {
    private static final String Table_name = "symptom_log";
    private static final String col1 = "day_of_the_year";

    SQLiteDatabase db;
    public symptomLogDatabase(Context context){
        super(context, Table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE symptom_log (" +
                "day_of_the_year INTEGER NOT NULL, " +
                "cough INTEGER NOT NULL, " +
                "sniffles INTEGER NOT NULL, " +
                "sore_throat INTEGER NOT NULL, " +
                "muscle_aches INTEGER NOT NULL, " +
                "fever INTERGER NOT NULL,"  +
                "difficulty_breathing INTEGER NOT NULL, " +
                "remarks_of_the_day TEXT NOT NULL " +
                ")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public boolean contains(String id){
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Table_name + " WHERE " + "day_of_the_year" + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[] {id});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }
        cursor.close();
        db.close();
        return hasObject;
    }

    public boolean deleteData(int dayOfYear){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(Table_name, "day_of_the_year" + "=" + dayOfYear, null);
        db.close();
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean replaceData(List<Symptoms> symptomsList, String msg, int dayOfYear){
        db = this.getWritableDatabase();
        db.delete(Table_name, "day_of_the_year" + "=" + dayOfYear, null);
        ContentValues CV = new ContentValues();
        CV.put("day_of_the_year", dayOfYear);
        CV.put("cough", symptomsList.get(0).intensity);
        CV.put("sniffles", symptomsList.get(1).intensity);
        CV.put("sore_throat", symptomsList.get(2).intensity);
        CV.put("muscle_aches", symptomsList.get(3).intensity);
        CV.put("fever", symptomsList.get(4).intensity);
        CV.put("difficulty_breathing", symptomsList.get(5).intensity);
        CV.put("remarks_of_the_day", msg);

        Log.d("DatabaseHelper", "AddData: Adding today's symptoms to" + Table_name);
        long result = db.insert(Table_name, null, CV);
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean addData(List<Symptoms> symptomsList, String msg, int dayOfYear){
        db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put("day_of_the_year", dayOfYear);
        CV.put("cough", symptomsList.get(0).intensity);
        CV.put("sniffles", symptomsList.get(1).intensity);
        CV.put("sore_throat", symptomsList.get(2).intensity);
        CV.put("muscle_aches", symptomsList.get(3).intensity);
        CV.put("fever", symptomsList.get(4).intensity);
        CV.put("difficulty_breathing", symptomsList.get(5).intensity);
        CV.put("remarks_of_the_day", msg);

        Log.d("DatabaseHelper", "AddData: Adding today's symptoms to" + Table_name);
        long result = db.insert(Table_name, null, CV);
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }


    public Cursor getAllData(){
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_name + " ORDER BY " + col1 + " DESC", null);
        return res;
    }



    
//    public boolean addData(int item, String col_name){
//
//        ContentValues CV = new ContentValues();
//        CV.put(col_name, item);
//        Log.d("DatabaseHelper", "AddData: Adding" + item + "to" + Table_name);
//        long result = db.insert(Table_name, null, CV);
//        if (result == -1){
//            return false;
//        } else {
//            return true;
//        }
//    }

//    public boolean addData(String item, String col_name){
//
//        ContentValues CV = new ContentValues();
//        CV.put(col_name, item);
//        Log.d("DatabaseHelper", "AddData: Adding" + item + "to" + Table_name);
//        long result = db.insert(Table_name, null, CV);
//        if (result == -1){
//            return false;
//        } else {
//            return true;
//        }
//
//
//
//
//    }
}
