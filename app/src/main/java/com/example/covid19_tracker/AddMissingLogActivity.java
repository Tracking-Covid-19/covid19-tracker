package com.example.covid19_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddMissingLogActivity extends AppCompatActivity {
    TextView coughText;
    TextView snifflesText;
    TextView sorethroatText;
    TextView muscleacheText;
    TextView feverText;
    TextView diffyBreathText;
    TextView remarksText;
    symptomLogDatabase mSymptomLogDatabase;
    Spinner daySpinner;
    Spinner monthSpinner;
    int day;
    int month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_log);
        coughText = findViewById(R.id.coughText);
        snifflesText = findViewById(R.id.snifflesText);
        sorethroatText = findViewById(R.id.sorethroatText);
        muscleacheText = findViewById(R.id.muscleacheText);
        feverText = findViewById(R.id.feverText);
        diffyBreathText = findViewById(R.id.DBText);
        remarksText = findViewById(R.id.RemarksText);
        daySpinner = findViewById(R.id.daySpinner);
        monthSpinner = findViewById(R.id.MonthSpinner);

        List<String> days = new ArrayList<>();
        for (int i = 0; i < 31; i++){
            days.add(Integer.toString(i+1));
        }

        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            months.add(Integer.toString(i+1));
        }

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                day = Integer.parseInt(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                day = -1;
            }
        });
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                month = Integer.parseInt(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                month = -1;
            }
        });

    }

    public void submitChange(View view){
        mSymptomLogDatabase = new symptomLogDatabase(this);
        List<Symptoms> symptomsList = new ArrayList<>();
        boolean validInput = true;
        String text;
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        try {

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
        } catch (NumberFormatException e){
            validInput = false;
        }
        if (validInput){
            int dayOfYearToAdd;
            GregorianCalendar GC = new GregorianCalendar(2020, month, day);
            dayOfYearToAdd = GC.get(Calendar.DAY_OF_YEAR) - 30;
            if(mSymptomLogDatabase.replaceData(symptomsList, " ", dayOfYearToAdd)){
                text = "Submitted!";
            } else {
                text = "Failed to submit";
            }
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            mSymptomLogDatabase.close();
        } else {
            text = "Invalid Input";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            mSymptomLogDatabase.close();
        }


    }
    public void goBackToList(View view){
        Intent intent = new Intent(this, ViewLogActivity.class);
        startActivity(intent);

    }
}
