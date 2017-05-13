package com.katlab.scheduler.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Model.App;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.Material;
import com.katlab.scheduler.Model.Roles;
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
        TextView tvLessonGroups = (TextView) findViewById(R.id.textViewGroups);
        Button btnOpenTask = (Button) findViewById(R.id.buttonOpenHomeTask);
        Button btnEditLesson = (Button) findViewById(R.id.buttonEditLessonDetails);

        tvLessonName.setText(lesson.getName());
        tvLessonTeacher.setText(lesson.getTeacherName());
        tvLessonPlace.setText(lesson.getPlace());
        tvLessonGroups.setText("\n Группы: \n" + lesson.getAllGroupsFullString());
        tvLessonStartTime.setText("Время начала занятия: "+ lesson.getStartTime());
        tvLessonEndTime.setText("Время окончания занятия: "+ lesson.getEndTime());

        if (DatabaseHandler.getMaterialsForLesson(lesson.getId()).size() != 0){
            btnOpenTask.setVisibility(View.VISIBLE);
        }
        if(!App.getCurrentUser().getRole().equals(Roles.STUDENT)){
            btnEditLesson.setVisibility(View.VISIBLE);
        }
    }

    public void openEditLessonActivity(View view){
        Intent intent = new Intent(this, EditLessonActivity.class);
        intent.putExtra("Lesson", lesson);
        startActivity(intent);
    }
    public void openMaterialActivity(View view){
        Intent intent = new Intent(this, MaterialDetailsActivity.class);
        Material material = DatabaseHandler.getMaterialsForLesson(lesson.getId()).get(0);
        intent.putExtra("Material", material);
        startActivity(intent);
    }
}
