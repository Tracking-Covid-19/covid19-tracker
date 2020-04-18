package com.example.covid19_tracker;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    public int saveRecords (List<Symptoms> symptomsList){
        Calendar calendar = Calendar.getInstance();
        int result;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        if (mDatabaseHelper.contains(Integer.toString(dayOfYear))){
            result = 1;
            mDatabaseHelper.replaceData(symptomsList, " ", dayOfYear);
        } else {
            boolean saveResult = mDatabaseHelper.addData(symptomsList, " ", dayOfYear);
            if (saveResult){
                result = 2;
            } else {
                result = 0;
            }

        }

        return result;
    }

    public void submitLog(View view){
        int saveResult = saveRecords(readSeekBars());
        Context context = getApplicationContext();
        CharSequence text;

        if(saveResult == 2){
             text = "Symptom data saved!";
        } else if (saveResult == 1){
             text = "Symptom data updated!";
        } else {
             text = "Fail to save symptom data";
        }

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
