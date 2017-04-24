package com.katlab.scheduler.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.scheduler.R;

import java.util.ArrayList;


public class ScheduleAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList <Lesson> lessons;

    public ScheduleAdapter(Context context, ArrayList <Lesson> less) {
        this.context = context;
        lessons = less;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_lesson, parent, false);
        }

        Lesson lesson = getLesson(position);

        Log.i("INFO", "getView name: " + lesson.getName());
        Log.i("INFO", "getView name: " + lesson.getPlace());

        ((TextView) view.findViewById(R.id.textViewLessonName)).setText(lesson.getName());
        ((TextView) view.findViewById(R.id.textViewLessonPlace)).setText(lesson.getPlace());

        return view;
    }

    Lesson getLesson(int position) {
        return ((Lesson) getItem(position));
    }
}
