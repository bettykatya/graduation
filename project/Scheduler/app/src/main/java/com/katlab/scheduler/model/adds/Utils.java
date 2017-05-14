package com.katlab.scheduler.model.adds;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public final static int SCHEDULE_DAYS_CYCLE = 14;
    public final static String STRING_DELIMETER = "/";
    public final static String GROUP_DELIMETER = "-";


    public static String getJsonString(Context context, String fileFromAssets){
        String json = null;
        try {

            InputStream is = context.getAssets().open(fileFromAssets);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Integer> getWeekdaysFromString(String weekdaysString){
        ArrayList<Integer> weekdays = new ArrayList<>();
        String [] array = weekdaysString.split(STRING_DELIMETER);
        for (int i = 1; i < array.length; i++) {
            weekdays.add(Integer.parseInt(array[i]));
        }
        return weekdays;
    }

    public static ArrayList<String> getGroupsFromString(String groupsString){
        ArrayList<String> groups = new ArrayList<>();
        String [] array = groupsString.split(STRING_DELIMETER);
        for (int i = 1; i < array.length; i++) {
            groups.add(array[i]);
        }
        return groups;
    }

}
