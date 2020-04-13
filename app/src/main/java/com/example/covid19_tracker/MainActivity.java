package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.covid19tracker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logSymptoms(View view) {
        Intent intent = new Intent(this, LogSymptomsActivity.class);
        startActivity(intent);
    }

    public void findPeople(View view) {
        Intent intent = new Intent(this, FindPeopleActivity.class);
        startActivity(intent);
    }

    public void viewLog(View view) {
        Intent intent = new Intent(this, ViewLogActivity.class);
        startActivity(intent);
    }

    public void viewMap(View view) {
        Intent intent = new Intent(this, ViewMapActivity.class);
        startActivity(intent);
    }

    public void aboutInfo(View view) {
        Intent intent = new Intent(this, AboutInfoActivity.class);
        startActivity(intent);
    }
    public void loginOrCreate(View view) {
        Intent intent = new Intent(this, LoginOrSignUpActivity.class);
        startActivity(intent);
    }


}
