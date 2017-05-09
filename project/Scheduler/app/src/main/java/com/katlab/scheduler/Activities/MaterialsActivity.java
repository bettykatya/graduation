package com.katlab.scheduler.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.katlab.scheduler.Adapters.MaterialsAdapter;
import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.Material;
import com.katlab.scheduler.scheduler.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialsActivity extends Activity {

    MaterialsAdapter listAdapter;
    ExpandableListView expListView;
    List<String> lessonNames;
    HashMap<String, List<String>> materialsLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);
        setDataForList();
        listAdapter = new MaterialsAdapter(this, lessonNames, materialsLesson);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listMaterials);
        listView.setAdapter(listAdapter);

    }

    private void setDataForList(){
        ArrayList<Lesson> lessons = DatabaseHandler.getAllUniqueLessons();

        lessonNames = new ArrayList<String>();
        materialsLesson = new HashMap<String, List<String>>();

        for (int i = 0; i < lessons.size(); i++) {
            Lesson tempLesson = lessons.get(i);
            lessonNames.add(tempLesson.getName());
            ArrayList<Material> tempMaterials = DatabaseHandler.getMaterialsForLesson(tempLesson.getId());
            List<String> temp = new ArrayList<String>();
            for (int j = 0; j < tempMaterials.size(); j++) {
                Material tempMaterial = tempMaterials.get(j);
                temp.add(tempMaterial.getName());
            }
            materialsLesson.put(lessonNames.get(i), temp);
        }
    }
}
