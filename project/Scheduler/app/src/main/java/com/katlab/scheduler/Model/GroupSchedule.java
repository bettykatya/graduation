package com.katlab.scheduler.Model;

import java.util.ArrayList;

public class GroupSchedule {

    private String group;
    private String subgroup;
    private ArrayList <DaySchedule> daySchedule;

    public GroupSchedule(String group, String subgroup, ArrayList <DaySchedule> daySchedule){
        this.group = group;
        this.subgroup = subgroup;
        this.daySchedule = daySchedule;
    }

    public String getGroup() {
        return group;
    }

    public String getSubGroup() {
        return subgroup;
    }

}
