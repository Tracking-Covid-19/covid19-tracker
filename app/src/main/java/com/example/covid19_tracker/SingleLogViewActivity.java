package com.example.covid19_tracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SingleLogViewActivity extends AppCompatActivity {
    TextView coughText;
    TextView snifflesText;
    TextView sorethroatText;
    TextView muscleacheText;
    TextView feverText;
    TextView diffyBreathText;
    TextView remarksText;
    TextView displayDate;
    DatabaseHelper mDatabaseHelper;
    String[] data;
    String dateToDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_log_view);
        coughText = findViewById(R.id.coughText);
        snifflesText = findViewById(R.id.snifflesText);
        sorethroatText = findViewById(R.id.sorethroatText);
        muscleacheText = findViewById(R.id.muscleacheText);
        feverText = findViewById(R.id.feverText);
        diffyBreathText = findViewById(R.id.DBText);
        remarksText = findViewById(R.id.RemarksText);
        displayDate = findViewById(R.id.singleLogDate);
        Intent intent = getIntent();
        data = getSingleData(intent.getIntExtra("LogID", 0));
        dateToDisplay = intent.getStringExtra("date");

        displayDate.setText(dateToDisplay);
        coughText.setText(data[1]);
        snifflesText.setText(data[2]);
        sorethroatText.setText(data[3]);
        muscleacheText.setText(data[4]);
        feverText.setText(data[5]);
        diffyBreathText.setText(data[6]);
        remarksText.setText(data[7]);


    }
    private String[] getSingleData(int index){
        String[] result = new String[8];
        int cursorIndex;
        mDatabaseHelper = new DatabaseHelper(this);
        Cursor cor = mDatabaseHelper.getAllData();
        if (cor.getCount() == 0){
            return result;
        }
        while (cor.moveToNext()){
            cursorIndex = cor.getInt(0);
            if (cor.getInt(0) == index){
                for (int j = 0; j < 7; j++){
                    result[j] = Integer.toString(cor.getInt(j));
                }
                result[7] = cor.getString(7);
            }
        }
        return result;
    }
}
