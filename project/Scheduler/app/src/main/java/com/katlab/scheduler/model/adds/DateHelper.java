package com.katlab.scheduler.model.adds;

import android.util.Log;

import com.katlab.scheduler.model.app.Settings;

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

        if((startWeek%2 == 0 && currentWeek%2 == 0) || (startWeek%2 == 1 && currentWeek%2 == 1)){
            today = currentWeekday;
        } else {
            today = currentWeekday + 7;
        }

        return today;
    }

}
