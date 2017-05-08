package com.katlab.scheduler.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;

public class LessonDetailsActivity extends AppCompatActivity {

    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        lesson = (Lesson) getIntent().getSerializableExtra("Lesson");

        TextView tvLessonName = (TextView) findViewById(R.id.textViewLessonName);
        TextView tvLessonTeacher = (TextView) findViewById(R.id.textViewLessonTeacher);
        TextView tvLessonPlace = (TextView) findViewById(R.id.textViewLessonPlace);
        TextView tvLessonStartTime = (TextView) findViewById(R.id.textViewLessonStartTime);
        TextView tvLessonEndTime = (TextView) findViewById(R.id.textViewLessonEndTime);
        TextView tvLessonHasTask = (TextView) findViewById(R.id.textViewLessonHasTask);

        tvLessonName.setText(lesson.getName());
        tvLessonTeacher.setText(lesson.getTeacherName());
        tvLessonPlace.setText(lesson.getPlace());
        tvLessonStartTime.setText(lesson.getStartTime());
        tvLessonEndTime.setText(lesson.getEndTime());

    }
}
