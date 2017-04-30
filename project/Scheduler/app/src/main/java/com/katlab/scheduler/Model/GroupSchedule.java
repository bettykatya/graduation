package com.katlab.scheduler.Model;

import java.util.ArrayList;

public class GroupSchedule {

    private String group;
    private String subgroup;
    private ArrayList <DaySchedule> daySchedules;

    public GroupSchedule(String group, String subgroup, ArrayList <DaySchedule> daySchedule){
        this.group = group;
        this.subgroup = subgroup;
        this.daySchedules = daySchedule;
    }

    public GroupSchedule(String group, String subgroup){
        this.group = group;
        this.subgroup = subgroup;
        this.daySchedules = new ArrayList<>();
    }

    public ArrayList<DaySchedule> getDaySchedules() {
        return daySchedules;
    }

    public String getGroup() {
        return group;
    }
    public String getSubGroup() {
        return subgroup;
    }

    public DaySchedule getDaySchedule(int day){
        for (int i = 0; i < daySchedules.size(); i++) {
            if(daySchedules.get(i).getWeekDay() == day){
                return daySchedules.get(i);
            }
        }
        return new DaySchedule(day, new ArrayList<Lesson>());
    }

    public void addDaySchedule(DaySchedule daySchedule){
        //if day already added, add lessons to existing day, otherwise add whole day
        boolean hasDay = false;
        for (int i = 0; i < daySchedules.size(); i++) {
            if(daySchedule.getWeekDay() == daySchedules.get(i).getWeekDay()){
                daySchedules.get(i).addLessons(daySchedule.getLessons());
                hasDay = true;
            }
        }
        if(!hasDay){
            daySchedules.add(daySchedule);
        }
    }
}
