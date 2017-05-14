package com.katlab.scheduler.presenter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.katlab.scheduler.model.objects.Lesson;
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
            view = layoutInflater.inflate(R.layout.item_list_lesson, parent, false);
        }

        Lesson lesson = getLesson(position);

        ((TextView) view.findViewById(R.id.textViewLessonName)).setText(lesson.getName());
        ((TextView) view.findViewById(R.id.textViewLessonPlace)).setText(lesson.getPlace());

        ((TextView) view.findViewById(R.id.textViewLessonStartTime)).setText(lesson.getStartTime());
        ((TextView) view.findViewById(R.id.textViewLessonEndTime)).setText(lesson.getEndTime());

        return view;
    }

    Lesson getLesson(int position) {
        return ((Lesson) getItem(position));
    }
}
