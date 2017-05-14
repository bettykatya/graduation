package com.katlab.scheduler.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.katlab.scheduler.presenter.adapters.MaterialsAdapter;
import com.katlab.scheduler.presenter.DatabaseHandler;
import com.katlab.scheduler.model.app.App;
import com.katlab.scheduler.model.objects.Lesson;
import com.katlab.scheduler.model.objects.Material;
import com.katlab.scheduler.scheduler.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialsActivity extends Activity {

    MaterialsAdapter listAdapter;
    ExpandableListView expListView;

    ArrayList <Lesson> lessons;
    HashMap<Lesson, List<Material>> materialsLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);
        setDataForList();
        listAdapter = new MaterialsAdapter(this, lessons, materialsLesson);
        expListView = (ExpandableListView) findViewById(R.id.listMaterials);
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                /*
                Lesson current_lesson = (Lesson) adapter.getItem(position);
                openLessonDetailsActivity(current_lesson);
                 */


                Material material = (Material) listAdapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(parent.getContext(), MaterialDetailsActivity.class);
                intent.putExtra("Material", material);
                startActivity(intent);

                return false;
            }
        });
    }

    private void setDataForList(){
        lessons = DatabaseHandler.getAllUniqueLessons(App.getCurrentUser());
        materialsLesson = new HashMap <Lesson, List<Material>>();
        for (int i = 0; i < lessons.size(); i++) {
            Lesson tempLesson = lessons.get(i);
            ArrayList<Material> tempMaterials = DatabaseHandler.getMaterialsForLesson(tempLesson.getId());
            materialsLesson.put(tempLesson, tempMaterials);
        }
    }


}
