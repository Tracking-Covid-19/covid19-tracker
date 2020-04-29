package com.example.covid19_tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ViewLogActivity extends AppCompatActivity {
    symptomLogDatabase mSymptomLogDatabase;
    List<String[]> data;
    private String[] myDataset;
    public String[] dateIndex;
    ListView listView;
    static StableArrayAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);
        data = getData();
        myDataset = new String[data.size()];
        String[] temp;
        dateIndex = new String[data.size()];
        for (int i = 0; i < data.size(); i++){
            temp = data.get(i);
            dateIndex[i] = temp[0];
        }
        int dayOfYear;
        for (int i = 0; i < dateIndex.length; i++){
            dayOfYear = Integer.parseInt(dateIndex[i]);
            Year y = Year.of(2020);
            LocalDate lD = y.atDay(dayOfYear);
            myDataset[i] = lD.toString();
        }

        listView = findViewById(R.id.logListView);
        final ArrayList<String> list = new ArrayList<String>();
        for (String s: myDataset){
            list.add(s);
        }
        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dataString = listView.getItemAtPosition(position).toString();
                String[] dateParts = dataString.split("-");
                String year = dateParts[0];
                String month = dateParts[1];
                String day = dateParts[2];
                GregorianCalendar GC = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                int dayOfYearIndex = GC.get(Calendar.DAY_OF_YEAR) - 30;
                Intent intent = new Intent(getApplicationContext(), SingleLogViewActivity.class);
                intent.putExtra("LogID", dayOfYearIndex);
                intent.putExtra("date", dataString);

                startActivity(intent);
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(getApplicationContext(), dayOfYearIndex, duration);
//                toast.show();

            }
        });

    }
    static public void refresh(){
        adapter.notifyDataSetChanged();


    }
    public void addMissingLog(View view){
        Intent intent = new Intent(this, AddMissingLogActivity.class);
        startActivity(intent);
    }
    private List<String[]> getData(){
        List<String[]> result = new ArrayList<>();
        mSymptomLogDatabase = new symptomLogDatabase(this);
        Cursor cor = mSymptomLogDatabase.getAllData();
        if (cor.getCount() == 0){
            return result;
        }
        String[] temp;
        while (cor.moveToNext()){
            temp = new String[8];
            for (int j = 0; j < 7; j++){
                temp[j] = Integer.toString(cor.getInt(j));
            }
            temp[7] = cor.getString(7);
            result.add(temp);
        }
        return result;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void goToSymptomDetails(){

    }




}

