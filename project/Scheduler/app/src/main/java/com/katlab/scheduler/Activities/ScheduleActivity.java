package com.katlab.scheduler.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "started activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //ArrayList <Lesson> lessons = getLessons();

        TextView textViewTemp = (TextView) findViewById(R.id.tempTextView);
        textViewTemp.setText(getJsonStringSchedule());


        //ListView listSchedule = (ListView) findViewById(R.id.listSchedule);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.list_item_lesson, lessons);
        //listSchedule.setAdapter(adapter);


    }

    private ArrayList <Lesson> getLessons(){
        ArrayList <Lesson> lessons = new ArrayList<Lesson>();

        try {
            String scheduleJSONString= getJsonStringSchedule();
            JSONObject jsonObject = new JSONObject(scheduleJSONString);
            Log.i("scheduleJSONString", scheduleJSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lessons;
    }

    private String getJsonStringSchedule(){
        //File file = new File("src/main/res/jsons/schedule.json");
        String json = null;
        try {

            InputStream is = getAssets().open("jsons/schedule.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
