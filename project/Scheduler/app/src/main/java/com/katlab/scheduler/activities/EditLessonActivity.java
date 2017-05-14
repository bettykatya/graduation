package com.katlab.scheduler.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.katlab.scheduler.presenter.DatabaseHandler;
import com.katlab.scheduler.model.objects.Lesson;
import com.katlab.scheduler.scheduler.R;

public class EditLessonActivity extends AppCompatActivity {

    Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lesson = (Lesson) getIntent().getSerializableExtra("Lesson");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);

        EditText building = (EditText) findViewById(R.id.editTextBuilding);
        EditText room = (EditText) findViewById(R.id.editTextRoom);
        EditText startTime = (EditText) findViewById(R.id.editTextStartTime);
        EditText endTime = (EditText) findViewById(R.id.editTextEndTime);

        building.setText(lesson.getBuilding());
        room.setText(lesson.getRoom());
        startTime.setText(lesson.getStartTime());
        endTime.setText(lesson.getEndTime());

        Log.i("INFO", "lesson = " + lesson);
    }

    public void editLessonListener(View view){

        EditText building = (EditText) findViewById(R.id.editTextBuilding);
        EditText room = (EditText) findViewById(R.id.editTextRoom);
        EditText startTime = (EditText) findViewById(R.id.editTextStartTime);
        EditText endTime = (EditText) findViewById(R.id.editTextEndTime);

        Lesson oldLesson = lesson;
        Lesson newLesson = lesson;
        newLesson.setBuilding(building.getText().toString());
        newLesson.setRoom(room.getText().toString());
        newLesson.setStartTime(startTime.getText().toString());
        newLesson.setEndTime(endTime.getText().toString());

        Log.i("INFO", "oldlesson = " + oldLesson);
        Log.i("INFO", "newLesson = " + newLesson);
        DatabaseHandler.editLesson(oldLesson, newLesson);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
