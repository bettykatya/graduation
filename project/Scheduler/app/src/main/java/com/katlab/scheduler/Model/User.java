package com.katlab.scheduler.Model;


import android.content.Context;

import java.util.ArrayList;

public class User {

    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String role; // ADMIN, TEACHER, STUDENT

    private int course;
    private String group;
    private String subgroup;

    private GroupSchedule userSchedule;

    public User(int id, String login, String password, String name, String surname, int course,
                String group, String subgroup, String role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.group = group;
        this.subgroup = subgroup;
        setRole(role);
    }


    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    private void setRole(String role) {
        switch (role.toUpperCase()){
            case "USER":
                this.role = "USER";
                break;
            case "TEACHER":
                this.role = "ADMIN";
                break;
            case "ADMIN":
                this.role = "ADMIN";
                break;
            default:
                this.role = "USER";
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    //TODO move to presenter
    public GroupSchedule getUserSchedule(Context context){
        if(userSchedule == null){
            ArrayList <Course> courses = FullSchedule.getFullScheduleCourses(context);
            for (int i = 0; i < courses.size(); i++) {
                Course tempCourse = courses.get(i);
                if(tempCourse.getCourseNumber() == this.course){
                    ArrayList<GroupSchedule> groups = tempCourse.getGroupSchedules();
                    for (int j = 0; j < groups.size(); j++) {
                        GroupSchedule tempGroupSchedule = groups.get(j);
                        if(tempGroupSchedule.getGroup() == this.group && tempGroupSchedule.getSubGroup() == this.subgroup){
                            this.userSchedule = tempGroupSchedule;
                        }
                    }
                }
            }
        }
        return this.userSchedule;
    }

}
