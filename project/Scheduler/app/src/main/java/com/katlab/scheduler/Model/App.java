package com.katlab.scheduler.Model;


public class App {
    private static User current_user;

    public static void setCurrentUser(User user){
        current_user = user;
    }

    public static User getCurrentUser(){
        return current_user;
    }
}
