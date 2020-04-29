package com.example.covid19_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class userLoginDatabase extends SQLiteOpenHelper {
    private static final String Table_name = "loginData";
    private static final String col1 = "username";

    SQLiteDatabase db;
    public userLoginDatabase(Context context){
        super(context, Table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE symptom_log (" +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL " +
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
        String selectString = "SELECT * FROM " + Table_name + " WHERE " + col1 + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[] {id});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }
        cursor.close();
        db.close();
        return hasObject;
    }
    public int loginMatch(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + Table_name + " WHERE " + col1 + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[] {username});
        String actualPwd;


        if(cursor.moveToFirst()){
            actualPwd = cursor.getString(1);
            cursor.close();
            db.close();
        } else {
            cursor.close(); // no such user
            db.close();
            return -1;
        }
        char[] pwdChar = password.toCharArray();
        char[] actChar = actualPwd.toCharArray();

        if (pwdChar.length == actChar.length){
            for (int i = 0; i < pwdChar.length; i++){
                if (pwdChar[i] != actChar[i]){
                    return 0; // wrong password
                }
            }
        } else {
            return 0; // wrong password
        }
        return 1; // Login Success

    }

    public boolean deleteUser(String username){
        db = this.getWritableDatabase();
        int result;
        result = db.delete(Table_name, col1 + "=" + username, null);
        db.close();
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public int getDatabaseSize(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_name, null);
        int count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        cursor.close();
        db.close();
        return count;



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

}
