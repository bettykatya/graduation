package com.katlab.scheduler.Presenter;

import android.content.Context;
import android.util.Log;

import com.katlab.scheduler.Model.App;
import com.katlab.scheduler.Model.User;

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

}
