package com.katlab.scheduler.Model;

import java.util.ArrayList;

public class Course {

    private int course;
    private ArrayList <GroupSchedule> groupSchedules;

    public Course(int course, ArrayList<GroupSchedule> groupSchedules){
        this.course = course;
        this.groupSchedules = groupSchedules;
    }

    public Course(int course){
        this.course = course;
        this.groupSchedules = new ArrayList<>();
    }

    public int getCourseNumber() {
        return course;
    }

    public ArrayList<GroupSchedule> getGroupSchedules() {
        return groupSchedules;
    }

    public void addGroupSchedule(GroupSchedule groupSchedule){
        this.groupSchedules.add(groupSchedule);
    }
}
