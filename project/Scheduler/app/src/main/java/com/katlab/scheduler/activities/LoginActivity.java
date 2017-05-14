package com.katlab.scheduler.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.katlab.scheduler.presenter.DatabaseHandler;
import com.katlab.scheduler.presenter.DataProvider;
import com.katlab.scheduler.presenter.LoginProcessor;
import com.katlab.scheduler.scheduler.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tryLogin(View view){

        EditText etLogin = (EditText) findViewById(R.id.email);
        EditText etPassword = (EditText) findViewById(R.id.password);

        String login = etLogin.getText().toString();
        String pass = etPassword.getText().toString();

        if(LoginProcessor.canLogin(this, login, pass)){
            DatabaseHandler.openDB(this);
            DatabaseHandler.initializeDatabaseDataFromJSON(DataProvider.getAllLessonsFromJSON(this), DataProvider.getAllMaterials(this));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
