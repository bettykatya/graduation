package com.katlab.scheduler.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.katlab.scheduler.scheduler.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tryLogin(View view){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
