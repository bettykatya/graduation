package com.katlab.scheduler.Presenter;

import android.content.Context;

import com.katlab.scheduler.Model.Course;
import com.katlab.scheduler.Model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataProvider {

    private static ArrayList<Lesson> getLessons(Context context){
        ArrayList <Lesson> lessons = new ArrayList<Lesson>();

        try {
            String scheduleJSONString= getJsonStringSchedule(context);
            JSONObject jsonObject = new JSONObject(scheduleJSONString);
            JSONArray coursesJSON = jsonObject.getJSONArray("fullSchedule");
            ArrayList <Course> courses = new ArrayList<>();
            for (int i = 0; i < coursesJSON.length(); i++) {
                JSONObject courseJSON = coursesJSON.getJSONObject(i);
                int courseNumber = courseJSON.getInt("course");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lessons;
    }

    //TODO set to private
    public static String getJsonStringSchedule(Context context){
        //File file = new File("src/main/res/jsons/schedule.json");
        String json = null;
        try {

            InputStream is = context.getAssets().open("jsons/schedule.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
