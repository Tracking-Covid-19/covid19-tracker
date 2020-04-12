package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogSymptomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_symptoms);

        //create a date string.
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview.
        TextView date  = findViewById(R.id.dateText);
        //set it as current date.
        date.setText(date_n);
    }

    Symptoms readSeekBars(){
        SeekBar coughBar = findViewById(R.id.seekBar1);
        SeekBar sniffleBar = findViewById(R.id.seekBar2);
        SeekBar soreBar = findViewById(R.id.seekBar3);
        SeekBar muscleBar = findViewById(R.id.seekBar4);
        SeekBar feverBar = findViewById(R.id.seekBar5);
        SeekBar breathBar = findViewById(R.id.seekBar6);

        Symptoms todaySymptom = new Symptoms(coughBar.getProgress(), sniffleBar.getProgress(), soreBar.getProgress(),
                muscleBar.getProgress(), feverBar.getProgress(), breathBar.getProgress());
        return todaySymptom;
    }

    void saveRecords (Symptoms symptom){
        FileOutputStream fos = null;
        try {
            StringBuilder symptomString = new StringBuilder();
            fos = openFileOutput("symptomLog.txt", MODE_APPEND);
            symptomString.append(symptom.sniffles + " ");
            symptomString.append(symptom.muscleAches + " ");
            symptomString.append(symptom.cough + " ");
            symptomString.append(symptom.soreThroat + " ");
            symptomString.append(symptom.fever + " ");
            symptomString.append(symptom.difficultyBreathing + " ");
            symptomString.append(symptom.date);
            symptomString.append("\n");
            String toWrite = symptomString.toString();
            fos.write(toWrite.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/symptomLog.txt", Toast.LENGTH_LONG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void submitLog(View view){
        saveRecords(readSeekBars());
    }


}
