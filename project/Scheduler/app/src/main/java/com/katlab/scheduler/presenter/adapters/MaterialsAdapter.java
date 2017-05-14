package com.katlab.scheduler.presenter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.katlab.scheduler.model.objects.Lesson;
import com.katlab.scheduler.model.objects.Material;
import com.katlab.scheduler.scheduler.R;

import java.util.HashMap;
import java.util.List;

public class MaterialsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Lesson> lessonNames;
    HashMap<Lesson, List<Material>> materialsLesson;

    public MaterialsAdapter(Context context, List<Lesson> lessons, HashMap<Lesson, List<Material>> materialsLesson) {
        this.context = context;
        this.lessonNames = lessons;
        this.materialsLesson = materialsLesson;
    }

    @Override
    public int getGroupCount() {
        return this.lessonNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.materialsLesson.get(this.lessonNames.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.lessonNames.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.materialsLesson.get(this.lessonNames.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Lesson lesson = (Lesson) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(lesson.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Material material = (Material) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(material.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
