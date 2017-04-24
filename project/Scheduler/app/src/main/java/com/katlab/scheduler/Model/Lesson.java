package com.katlab.scheduler.Model;

import com.katlab.scheduler.Model.User;

import java.io.Serializable;


public class Lesson implements Serializable {
    private String name;
    private User teacher;
    private String building;
    private String room;
    private String startTime;
    private String endTime;
    private boolean hasHometask;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return building + " room " + room;
    }

    public Lesson(String name, String building, String room){
        this.name = name;
        this.building = building;
        this.room = room;
    }

}
