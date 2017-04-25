package com.katlab.scheduler.Presenter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.katlab.scheduler.Helpers.Utils;
import com.katlab.scheduler.Model.Course;
import com.katlab.scheduler.Model.GroupSchedule;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.DaySchedule;
import com.katlab.scheduler.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataProvider {

    public static ArrayList <Course> getCourses(Context context){
        try {
            String scheduleJSONString= Utils.getJsonString(context, "jsons/schedule.json");
            JSONObject jsonObject = new JSONObject(scheduleJSONString);
            JSONArray coursesJSON = jsonObject.getJSONArray("fullSchedule");
            ArrayList <Course> courses = new ArrayList<>();
            for (int i = 0; i < coursesJSON.length(); i++) {
                JSONObject courseJSON = coursesJSON.getJSONObject(i);
                int courseNumber = courseJSON.getInt("course");

                JSONArray groupsJSON = courseJSON.getJSONArray("groupSchedules");
                ArrayList <GroupSchedule> groupSchedules = new ArrayList<>();
                for (int j = 0; j < groupsJSON.length(); j++) {
                    JSONObject groupJSON = groupsJSON.getJSONObject(j);
                    String groupNumber = groupJSON.getString("group");
                    String subgroupNumber = groupJSON.getString("subgroup");

                    JSONArray schedulesJSON = groupJSON.getJSONArray("schedule");
                    ArrayList <DaySchedule> daySchedules = new ArrayList<>();
                    for (int k = 0; k < schedulesJSON.length(); k++) {
                        JSONObject dayScheduleJSON = schedulesJSON.getJSONObject(k);
                        int weekDayNumber = dayScheduleJSON.getInt("weekDay");

                        JSONArray lessonsJSON = dayScheduleJSON.getJSONArray("lessons");
                        ArrayList <Lesson> lessons = new ArrayList<>();
                        for (int l = 0; l < lessonsJSON.length(); l++) {
                            JSONObject lessonJSON = lessonsJSON.getJSONObject(l);
                            String lessonName = lessonJSON.getString("subjectName");
                            int subjectID = lessonJSON.getInt("subjectID");
                            int teacherID = lessonJSON.getInt("teacherID");
                            String building = lessonJSON.getString("building");
                            String room = lessonJSON.getString("room");
                            String startTime = lessonJSON.getString("startTime");
                            String endTime = lessonJSON.getString("endTime");
                            boolean hasHometask = lessonJSON.getBoolean("hasHometask");

                            Lesson lesson = new Lesson(lessonName, teacherID, building,
                                    room, startTime, endTime, hasHometask);
                            lessons.add(lesson);
                        }
                        DaySchedule daySchedule = new DaySchedule(weekDayNumber, lessons);
                        daySchedules.add(daySchedule);
                    }
                    GroupSchedule groupSchedule = new GroupSchedule(groupNumber, subgroupNumber, daySchedules);
                    groupSchedules.add(groupSchedule);
                }
                Course course = new Course(courseNumber, groupSchedules);
                courses.add(course);
            }
            return courses;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList <User> getRegisteredUsers(Context context){
        ArrayList <User> users = new ArrayList<>();
        try{
            String JSONString= Utils.getJsonString(context, "jsons/users.json");
            JSONObject jsonObject = new JSONObject(JSONString);
            Log.i("INFO", jsonObject.keys().next());
            JSONArray usersJSON = jsonObject.getJSONArray("users");
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject tempUserJSON = usersJSON.getJSONObject(i);
                int id = tempUserJSON.getInt("id");
                String login = tempUserJSON.getString("login");
                String password = tempUserJSON.getString("password");
                String name = tempUserJSON.getString("name");
                String surname = tempUserJSON.getString("surname");
                int course = tempUserJSON.getInt("course");
                String group = tempUserJSON.getString("group");
                String subgroup = tempUserJSON.getString("subgroup");
                String role = tempUserJSON.getString("role");

                User user = new User(id, login, password, name, surname, course, group, subgroup, role);
                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
}
