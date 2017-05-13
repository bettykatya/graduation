package com.katlab.scheduler.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.Material;
import com.katlab.scheduler.scheduler.R;

public class MaterialDetailsActivity extends AppCompatActivity {

    Material material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_details);
        material = (Material) getIntent().getSerializableExtra("Material");


        displayMaterialContent();
    }

    private void displayMaterialContent(){
        TextView tvName = (TextView) findViewById(R.id.textViewMaterialName);
        TextView tvContent = (TextView) findViewById(R.id.textViewMaterialContent);

        tvName.setText(material.getName());
        tvContent.setText(material.getTextContent(this));
    }
}
