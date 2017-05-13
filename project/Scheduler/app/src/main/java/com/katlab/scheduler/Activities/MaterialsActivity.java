package com.katlab.scheduler.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.katlab.scheduler.Adapters.MaterialsAdapter;
import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Model.App;
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

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
/*
                Intent intent = new Intent(parent.getContext(), MaterialDetailsActivity.class);
                Material material;

                Lesson lesson = DatabaseHandler.getLessonById()
                ArrayList <Material> materials = DatabaseHandler.getMaterialsForLesson()

                intent.putExtra("Material", material);
                startActivity(intent);
*/
                return false;
            }
        });
    }

    private void setDataForList(){
        ArrayList<Lesson> lessons = DatabaseHandler.getAllUniqueLessons(App.getCurrentUser());
        for (int i = 0; i < lessons.size(); i++) {
            Log.i("INFO", "lesson = " + lessons.get(i));
        }

        lessonNames = new ArrayList<String>();
        materialsLesson = new HashMap<String, List<String>>();

        for (int i = 0; i < lessons.size(); i++) {
            Lesson tempLesson = lessons.get(i);
            Log.i("INFO", "lesson name = " + tempLesson.getName());
            lessonNames.add(tempLesson.getName());
            ArrayList<Material> tempMaterials = DatabaseHandler.getMaterialsForLesson(tempLesson.getId());
            List<String> temp = new ArrayList<String>();
            for (int j = 0; j < tempMaterials.size(); j++) {
                Log.i("INFO", "material name" + tempMaterials.get(j).getName());
                temp.add(tempMaterials.get(j).getName());
            }
            materialsLesson.put(lessonNames.get(i), temp);
        }
    }


}
