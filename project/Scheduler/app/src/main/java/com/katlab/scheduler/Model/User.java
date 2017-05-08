package com.katlab.scheduler.Model;


import android.content.Context;

import com.katlab.scheduler.Helpers.Database.DatabaseHandler;
import com.katlab.scheduler.Helpers.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String role; // ADMIN, TEACHER, STUDENT

    private int course;
    private String group;
    private String subgroup;

    public User(){

    }

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
    public String getSurname() {
        return surname;
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

    public int getCourse() {
        return course;
    }
    public String getGroup() {
        return group;
    }
    public String getSubgroup() {
        return subgroup;
    }

    public String getGroupsString(){
        return getCourse() + Utils.GROUP_DELIMETER + getGroup() + Utils.GROUP_DELIMETER + getSubgroup();
    }
}
