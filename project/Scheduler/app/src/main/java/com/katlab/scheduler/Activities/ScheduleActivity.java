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
import com.katlab.scheduler.Helpers.DateHelper;
import com.katlab.scheduler.Model.App;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;
import com.katlab.scheduler.Helpers.Database.DatabaseHandler;

import java.util.ArrayList;

public class ScheduleActivity extends Activity {

    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ArrayList <Lesson> lessons = DatabaseHandler.getUserLessonsForDay(App.getCurrentUser(), DateHelper.getTodayNumber());

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
