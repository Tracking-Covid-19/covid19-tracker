package com.example.covid19_tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogSymptomsActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_symptoms);
         mDatabaseHelper = new DatabaseHelper(this);
        //create a date string.
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview.
        TextView date  = findViewById(R.id.dateText);
        //set it as current date.
        date.setText(date_n);
    }

    List<Symptoms> readSeekBars(){
        List<Symptoms> symptomsList = new ArrayList<>();
        SeekBar coughBar = findViewById(R.id.seekBar1);
        SeekBar sniffleBar = findViewById(R.id.seekBar2);
        SeekBar soreBar = findViewById(R.id.seekBar3);
        SeekBar muscleBar = findViewById(R.id.seekBar4);
        SeekBar feverBar = findViewById(R.id.seekBar5);
        SeekBar breathBar = findViewById(R.id.seekBar6);

        Symptoms cough = new Symptoms("cough", coughBar.getProgress());
        Symptoms sniffle = new Symptoms("sniffles", sniffleBar.getProgress());
        Symptoms sore = new Symptoms("sore_throat", soreBar.getProgress());
        Symptoms muscle = new Symptoms("muscle_ache", muscleBar.getProgress());
        Symptoms fever = new Symptoms("fever", feverBar.getProgress());
        Symptoms breath = new Symptoms("difficulty_breathing", breathBar.getProgress());

        symptomsList.add(cough);
        symptomsList.add(sniffle);
        symptomsList.add(sore);
        symptomsList.add(muscle);
        symptomsList.add(fever);
        symptomsList.add(breath);

        return symptomsList;
    }



    public boolean saveRecords (List<Symptoms> symptomsList){

        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        boolean result = mDatabaseHelper.addData(symptomsList, " ", dayOfYear);
        return result;
//        boolean insertData = mDatabaseHelper.addData(dayOfYear, "day_of_the_year");
//        if (!insertData){
//            throw new IllegalArgumentException("failed to save to database");
//        }
//        for (Symptoms s: symptomsList){
//            insertData = mDatabaseHelper.addData(s.intensity, s.name);
//            if (!insertData){
//                throw new IllegalArgumentException("failed to save to database");
//            }
//        }
//        mDatabaseHelper.addData("not yet implemented","remarks_of_the_day");
    }

    public void submitLog(View view){
        if(saveRecords(readSeekBars())){

        } else {

        }
    }


}
