package com.katlab.scheduler.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.katlab.scheduler.Adapters.ScheduleAdapter;
import com.katlab.scheduler.Model.Course;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Presenter.DataProvider;
import com.katlab.scheduler.scheduler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends Activity {

    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("INFO", "started ScheduleActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        /*
        TextView textViewTemp = (TextView) findViewById(R.id.textView4);
        textViewTemp.setText(DataProvider.getJsonStringSchedule(ScheduleActivity.this));
        */

        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText("temp text");

        ArrayList <Lesson> lessons = new ArrayList<>();
        lessons.add(0, new Lesson("Name1", "Building1", "Room1"));
        lessons.add(1, new Lesson("Name2", "Building2", "Room2"));
        lessons.add(2, new Lesson("Name3", "Building3", "Room3"));
        lessons.add(3, new Lesson("Name4", "Building4", "Room4"));

        Log.i("INFO", "lessons:");
        for (int i = 0; i < lessons.size(); i++) {
            Log.i("INFO", "name: " + lessons.get(i).getName());
            Log.i("INFO", "place: " + lessons.get(i).getPlace());
        }

        ListView listSchedule = (ListView) findViewById(R.id.listSchedule);
        adapter = new ScheduleAdapter(this, lessons);
        listSchedule.setAdapter(adapter);

    }

    public final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Lesson current_lesson = (Lesson)adapter.getItem(position);
            openLessonDetailsActivity(current_lesson);
        }
    };

    public void openLessonDetailsActivity(Lesson lesson){
        Intent intent = new Intent(this, LessonDetailsActivity.class);
        intent.putExtra("Lesson", lesson);
        startActivity(intent);
    }

}
