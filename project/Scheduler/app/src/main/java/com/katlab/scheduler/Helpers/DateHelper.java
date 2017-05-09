package com.katlab.scheduler.Helpers;

import android.util.Log;

import com.katlab.scheduler.Model.Settings;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {

    private static Calendar calendar = GregorianCalendar.getInstance();

    private static void setup(){
        calendar.setTime(getCurrentDay());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }

    private static Date getCurrentDay(){
        return new Date();
    }

    private static int getCurrentWeekNumber(){
        Log.i("INFO", "getFirstDayOfWeek = " + Calendar.getInstance().getFirstDayOfWeek());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private static int getCurrentWeekdayNumber(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getTodayNumber(){
        setup();
        int today = 0;

        int startWeek = Settings.START_WEEK_NUMBER;
        int currentWeek = getCurrentWeekNumber();
        int currentWeekday = getCurrentWeekdayNumber();
        Log.i("INFO", "startWeek = " + startWeek + " currentWeek = " + currentWeek + " currentWeekday = " + currentWeekday);

        if((startWeek%2 == 0 && currentWeek%2 == 0) || (startWeek%2 == 1 && currentWeek%2 == 1)){
            today = currentWeekday;
        } else {
            today = currentWeekday + 7;
        }

        Log.i("INFO", "today = " + today);
        return today;
    }

}
