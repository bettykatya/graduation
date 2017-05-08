package com.katlab.scheduler.Model;

import com.katlab.scheduler.Model.User;

import java.io.Serializable;
import java.util.ArrayList;

import static com.katlab.scheduler.Helpers.Utils.STRING_DELIMETER;


public class Lesson implements Serializable {
    private String id;
    private String name;
    private User teacher;
    private String building;
    private String room;
    private String startTime;
    private String endTime;
    private boolean hasHometask;

    private ArrayList<Integer> weekdays;
    private ArrayList<String> groups;


    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return "teachername";
        //return teacher.getName();
    }
    public int getTeacherID() {
        return 1;
        //return teacher.getId();
    }

    public String getWeekdaysString() {
        String str = "";
        for (int i = 0; i < weekdays.size(); i++) {
            str += STRING_DELIMETER + weekdays.get(i);
        }
        return str;
    }
    public String getGroupsString() {
        String str = "";
        for (int i = 0; i < groups.size(); i++) {
            str += STRING_DELIMETER + groups.get(i);
        }
        return str;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getBuilding() {
        return building;
    }
    public String getRoom() {
        return room;
    }
    public boolean hasTask(){
        return hasHometask;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getPlace() {
        return building + " room " + room;
    }
    public Lesson(String name, String building, String room){
        this.name = name;
        this.building = building;
        this.room = room;
    }
    public Lesson(String name, int teacherID, String building, String room,
                  String startTime, String endTime, boolean hasHometask){
        this.name = name;
        this.building = building;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hasHometask = hasHometask;
    }
    public Lesson(String id, String name, int teacherID, String building, String room,
                  String startTime, String endTime, boolean hasHometask,
                  ArrayList<Integer> weekdays, ArrayList<String> groups){
        this.setId(id);
        this.name = name;
        this.building = building;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hasHometask = hasHometask;
        this.weekdays = weekdays;
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "id = " + id + "name = " + name + ", teacher = " + getTeacherName() +
                ", place = " + getPlace() + ", startTime = " + startTime +
                ", endTime = " + endTime;
    }
}
