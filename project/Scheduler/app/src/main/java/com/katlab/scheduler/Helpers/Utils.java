package com.katlab.scheduler.Helpers;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public final static List <String> DAY_TIME_SCHEDULE = Arrays.asList("8.15-9.35", "9.45-11.05", "11.15-12.35", "13.00-14.20", "14.30-15.50", "16.00-17.20", "17.30-18.50", "19.00-20.20", "20.30-21.50");
    public final static List <String> WEEK_DAY = Arrays.asList();
    public final static List <String> EVEN_WEEK = Arrays.asList("even");


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

}
