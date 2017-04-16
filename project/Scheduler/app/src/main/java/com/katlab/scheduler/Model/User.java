package com.katlab.scheduler.Model;


public class User {

    private int id;
    private String login;
    private String password;
    private String name;
    private String role;

    private Integer group;
    private Integer subgroup;

    public User(){
        group = 0;
        subgroup = 0;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
