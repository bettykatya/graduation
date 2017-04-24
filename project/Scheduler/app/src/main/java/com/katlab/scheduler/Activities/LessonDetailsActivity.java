package com.katlab.scheduler.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;

public class LessonDetailsActivity extends AppCompatActivity {

    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        lesson = (Lesson) getIntent().getSerializableExtra("Lesson");
    }
}
