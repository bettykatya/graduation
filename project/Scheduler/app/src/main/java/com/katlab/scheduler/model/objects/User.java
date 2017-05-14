package com.katlab.scheduler.model.objects;


import com.katlab.scheduler.model.adds.Utils;
import com.katlab.scheduler.model.adds.Roles;

import java.io.Serializable;

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
            case "STUDENT":
                this.role = Roles.STUDENT;
                break;
            case "TEACHER":
                this.role = Roles.TEACHER;
                break;
            case "ADMIN":
                this.role = Roles.ADMIN;
                break;
            default:
                this.role = Roles.STUDENT;
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
