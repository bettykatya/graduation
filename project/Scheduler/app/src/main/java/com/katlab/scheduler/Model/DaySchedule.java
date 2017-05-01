package com.katlab.scheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class DaySchedule {

    private int weekDay;
    private ArrayList <Lesson> lessons;

    public DaySchedule(int weekDay){
        this.weekDay = weekDay;
        this.lessons = new ArrayList<>();
    }

    public DaySchedule(int weekDay, ArrayList <Lesson> lessons){
        this.weekDay = weekDay;
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson){
        this.lessons.add(lesson);
    }

    public void addLessons(ArrayList<Lesson> newLessons){
        for (int i = 0; i < newLessons.size(); i++) {
            this.lessons.add(newLessons.get(i));
        }
    }

    public int getWeekDay() {
        return weekDay;
    }
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
}
