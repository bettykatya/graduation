package com.katlab.scheduler.presenter;

import android.content.Context;
import android.util.Log;

import com.katlab.scheduler.model.app.App;
import com.katlab.scheduler.model.objects.User;

import java.util.ArrayList;

public class LoginProcessor {
    private static ArrayList <User> users;

    private static void getUsers(Context context){
        users = DataProvider.getRegisteredUsers(context);
    }
    public static boolean canLogin(Context context, String login, String password){
        getUsers(context);
        boolean flag = false;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Log.i("INFO", "user = " + user.getName());
            if(user.getLogin().equals(login) && user.getPassword().equals(password)){
                flag = true;
                App.setCurrentUser(user);
                break;
            }
        }
        return flag;
    }
    public static User getUserById(int id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id){
                return users.get(i);
            }
        }
        return new User();
    }
}
