package com.katlab.scheduler.model.app;


import com.katlab.scheduler.model.objects.User;

public class App {
    private static User current_user;
    private static  int current_day;

    public static void setCurrentUser(User user){
        current_user = user;
    }
    public static User getCurrentUser(){
        return current_user;
    }

}
