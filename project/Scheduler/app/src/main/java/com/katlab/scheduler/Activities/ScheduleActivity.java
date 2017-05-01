package com.katlab.scheduler.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.katlab.scheduler.Adapters.ScheduleAdapter;
import com.katlab.scheduler.Model.App;
import com.katlab.scheduler.Model.Course;
import com.katlab.scheduler.Model.DaySchedule;
import com.katlab.scheduler.Model.GroupSchedule;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;
import com.katlab.scheduler.Helpers.Database.DatabaseHandler;

import java.util.ArrayList;

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
        textView.setText("current user is " + App.getCurrentUser().getName());

        ArrayList <Lesson> lessons = DatabaseHandler.getUserLessonsForDay(App.getCurrentUser(), 1);
        Log.i("INFO", "lessons size = " + lessons.size());
        for (int i = 0; i < lessons.size(); i++) {
            Log.i("INFO", "lesson from activity" + lessons.get(i));
            //Log.i("INFO", "name: " + lessons.get(i).getName());
            //Log.i("INFO", "place: " + lessons.get(i).getPlace());
        }

        ListView listSchedule = (ListView) findViewById(R.id.listSchedule);
        adapter = new ScheduleAdapter(this, lessons);
        listSchedule.setAdapter(adapter);
        listSchedule.setOnItemClickListener(onItemClickListener);
    }

    public final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Lesson current_lesson = (Lesson) adapter.getItem(position);
            openLessonDetailsActivity(current_lesson);
        }
    };

    public void openLessonDetailsActivity(Lesson lesson){
        Intent intent = new Intent(this, LessonDetailsActivity.class);
        intent.putExtra("Lesson", lesson);
        startActivity(intent);
    }

}
