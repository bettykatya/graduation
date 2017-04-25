package com.katlab.scheduler.Model;

import java.util.ArrayList;

public class Course {

    private int course;
    private ArrayList <GroupSchedule> groupSchedules;

    public Course(int course, ArrayList<GroupSchedule> groupSchedules){
        this.course = course;
        this.groupSchedules = groupSchedules;
    }

    public int getCourse() {
        return course;
    }

    public ArrayList<GroupSchedule> getGroupSchedules() {
        return groupSchedules;
    }
}
