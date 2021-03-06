package com.katlab.scheduler.presenter;

import android.content.Context;
import android.util.Log;

import com.katlab.scheduler.model.adds.Utils;
import com.katlab.scheduler.model.objects.Lesson;
import com.katlab.scheduler.model.objects.Material;
import com.katlab.scheduler.model.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataProvider {

    public static ArrayList <User> getRegisteredUsers(Context context){
        ArrayList <User> users = new ArrayList<>();
        try{
            String JSONString= Utils.getJsonString(context, "jsons/users.json");
            JSONObject jsonObject = new JSONObject(JSONString);
            JSONArray usersJSON = jsonObject.getJSONArray("users");
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject tempUserJSON = usersJSON.getJSONObject(i);
                int id = tempUserJSON.getInt("id");
                String login = tempUserJSON.getString("login");
                String password = tempUserJSON.getString("password");
                String name = tempUserJSON.getString("name");
                String surname = tempUserJSON.getString("surname");
                int course = tempUserJSON.optInt("course");
                String group = tempUserJSON.optString("group");
                String subgroup = tempUserJSON.optString("subgroup");
                String role = tempUserJSON.getString("role");

                User user = new User(id, login, password, name, surname, course, group, subgroup, role);
                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("INFO", "number of registered users is " + users.size());
        return users;
    }
    public static ArrayList<Lesson> getAllLessonsFromJSON(Context context){
        ArrayList <Lesson> lessons = new ArrayList<>();
        try{
            String JSONString= Utils.getJsonString(context, "jsons/lessons.json");
            JSONObject jsonObject = new JSONObject(JSONString);
            JSONArray usersJSON = jsonObject.getJSONArray("lessons");
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject tempUserJSON = usersJSON.getJSONObject(i);
                String id = tempUserJSON.getString("id");
                String subjectName = tempUserJSON.getString("subjectName");
                int teacherID = tempUserJSON.getInt("teacherID");

                String building = tempUserJSON.getString("building");
                String room = tempUserJSON.getString("room");

                String startTime = tempUserJSON.getString("startTime");
                String endTime = tempUserJSON.getString("endTime");
                boolean hasHometask = tempUserJSON.getBoolean("hasHometask");

                JSONArray weekdaysJSON = tempUserJSON.getJSONArray("weekdays");
                ArrayList<Integer> weekdays = new ArrayList<>();
                for (int j = 0; j < weekdaysJSON.length(); j++) {
                    Integer day = weekdaysJSON.getInt(j);
                    weekdays.add(day);
                }

                JSONArray groupsJSON = tempUserJSON.getJSONArray("groups");
                ArrayList<String> groups = new ArrayList<>();
                for (int j = 0; j < groupsJSON.length(); j++) {
                    String group = groupsJSON.getString(j);
                    groups.add(group);
                }

                Lesson lesson = new Lesson(id, subjectName, teacherID, building, room,
                        startTime, endTime, hasHometask, weekdays, groups);
                lessons.add(lesson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lessons;
    }
    public static ArrayList <Material> getAllMaterials(Context context){
        ArrayList <Material> materials = new ArrayList<>();
        try{
            String JSONString= Utils.getJsonString(context, "jsons/materials.json");
            JSONObject jsonObject = new JSONObject(JSONString);
            JSONArray usersJSON = jsonObject.getJSONArray("materials");
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject tempUserJSON = usersJSON.getJSONObject(i);
                String lessonID = tempUserJSON.getString("lessonID");
                String name = tempUserJSON.getString("name");
                String file = tempUserJSON.getString("file");

                Material material = new Material(lessonID, name, file);
                materials.add(material);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("INFO", "number of materials is " + materials.size());
        return materials;
    }
}