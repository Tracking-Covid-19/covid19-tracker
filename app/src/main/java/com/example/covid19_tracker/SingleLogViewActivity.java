package com.example.covid19_tracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    public void delete(View view){
        Intent intent = getIntent();
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.deleteData(intent.getIntExtra("LogID", 0));
//        Intent intent2 = new Intent(this, ViewLogActivity.class);
//        startActivity(intent2);
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        ViewLogActivity.refresh();
    }



    public void goBackToList(View view){
//        Intent intent = new Intent(this, ViewLogActivity.class);
//        startActivity(intent);
        finish();
    }

    public void submitChange(View view){
        mDatabaseHelper = new DatabaseHelper(this);
        List<Symptoms> symptomsList = new ArrayList<>();


        Symptoms cough = new Symptoms("cough", Integer.parseInt(coughText.getText().toString()));
        Symptoms sniffle = new Symptoms("sniffles", Integer.parseInt(snifflesText.getText().toString()));
        Symptoms sore = new Symptoms("sore_throat", Integer.parseInt(sorethroatText.getText().toString()));
        Symptoms muscle = new Symptoms("muscle_ache", Integer.parseInt(muscleacheText.getText().toString()));
        Symptoms fever = new Symptoms("fever", Integer.parseInt(feverText.getText().toString()));
        Symptoms breath = new Symptoms("difficulty_breathing", Integer.parseInt(diffyBreathText.getText().toString()));

        symptomsList.add(cough);
        symptomsList.add(sniffle);
        symptomsList.add(sore);
        symptomsList.add(muscle);
        symptomsList.add(fever);
        symptomsList.add(breath);

        Intent intent = getIntent();
        int duration = Toast.LENGTH_SHORT;
        String text;
        Context context = getApplicationContext();
        if(mDatabaseHelper.replaceData(symptomsList, " ", intent.getIntExtra("LogID", 0))){
            text = "Submitted!";
        } else {
            text = "Failed to submit";
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        mDatabaseHelper.close();

    }

    private String[] getSingleData(int index){
        String[] result = new String[8];
        mDatabaseHelper = new DatabaseHelper(this);
        Cursor cor = mDatabaseHelper.getAllData();
        if (cor.getCount() == 0){
            return result;
        }
        while (cor.moveToNext()){

            if (cor.getInt(0) == index){
                for (int j = 0; j < 7; j++){
                    result[j] = Integer.toString(cor.getInt(j));
                }
                result[7] = cor.getString(7);
            }
        }
        mDatabaseHelper.close();
        return result;
    }
}
