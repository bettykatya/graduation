package com.katlab.scheduler.Model;

import android.content.Context;

import com.katlab.scheduler.Presenter.DataProvider;

import java.util.ArrayList;

public class FullSchedule {

    private static  ArrayList <Course> courses;

    public static ArrayList<Course> getFullScheduleCourses(Context context){
        if(courses == null){
            courses = DataProvider.getCourses(context);
        }
        return courses;
    }

}
