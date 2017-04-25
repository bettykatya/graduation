package com.katlab.scheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class DaySchedule {
    private int weekDay;
    private ArrayList <Lesson> lessons;

    public DaySchedule(int weekDay, ArrayList <Lesson> lessons){
        this.weekDay = weekDay;
        this.lessons = lessons;
    }
}
