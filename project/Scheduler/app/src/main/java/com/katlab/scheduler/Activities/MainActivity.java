package com.katlab.scheduler.Activities;

import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Presenter.DataProvider;
import com.katlab.scheduler.scheduler.R;

public class MainActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler.openDB(this);
        DatabaseHandler.initializeDatabaseDataFromJSON(DataProvider.getAllLessonsFromJSON(this));

        //TODO add switching tabs by swipe gesture
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup(this.getLocalActivityManager());

        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Расписание");
        tabSpec.setContent(new Intent(this, ScheduleActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Материалы");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Настройки");
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);

    }
}
