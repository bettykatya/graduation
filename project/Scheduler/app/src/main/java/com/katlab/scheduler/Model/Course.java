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

    @Override
    public String toString() {
        String result = "";

        String course = " CourseNumber: " + getCourseNumber();
        for (int i = 0; i < groupSchedules.size(); i++) {
            GroupSchedule groupSchedule = groupSchedules.get(i);
            String group = " group " + groupSchedule.getGroup() +
                    " subgroup "+ groupSchedule.getSubGroup();
            for (int j = 0; j < groupSchedule.getDaySchedules().size(); j++) {
                DaySchedule daySchedule = groupSchedule.getDaySchedules().get(j);
                String day = String.valueOf(daySchedule.getWeekDay());
                for (int k = 0; k < daySchedule.getLessons().size(); k++) {
                    Lesson lesson = daySchedule.getLessons().get(k);
                    result += course + group + lesson + "\n";
                }
            }
        }

        return result;
    }
}
