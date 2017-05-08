package com.katlab.scheduler.Helpers.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.katlab.scheduler.Helpers.Utils;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.User;

import java.util.ArrayList;

public class DatabaseHandler implements DatabaseConstants {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private static int numb = 0;

    public static void openDB(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public static void closeDB(){
        dbHelper.close();
    }


    public static void initializeDatabaseDataFromJSON(ArrayList<Lesson> lessons){
        db.delete(TABLE_LESSONS_NAME, null, null);
        for (int i = 0; i < lessons.size(); i++) {
            Lesson currentLesson = lessons.get(i);
            addLesson(currentLesson);
        }
    }

    private static void addLesson(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LESSON_NAME, lesson.getName());
        values.put(COLUMN_NAME_LESSON_TEACHER_ID, lesson.getTeacherID());
        values.put(COLUMN_NAME_LESSON_BUILDING, lesson.getBuilding());
        values.put(COLUMN_NAME_LESSON_ROOM, lesson.getRoom());
        values.put(COLUMN_NAME_LESSON_START_TIME, lesson.getStartTime());
        values.put(COLUMN_NAME_LESSON_END_TIME, lesson.getEndTime());
        if(lesson.hasTask()){
            values.put(COLUMN_NAME_LESSON_HAS_TASK, 1);
        } else {
            values.put(COLUMN_NAME_LESSON_HAS_TASK, 0);
        }
        values.put(COLUMN_NAME_LESSON_WEEKDAYS, lesson.getWeekdaysString());
        values.put(COLUMN_NAME_LESSON_GROUPS, lesson.getGroupsString());

        db.insert(TABLE_LESSONS_NAME, null, values);
        Log.i("INFO", "inserted lesson: " + lesson);
    }

    public static ArrayList <Lesson> getUserLessonsForDay(User user, int weekDay){

        ArrayList <Lesson> lessons = new ArrayList<>();
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String groupsString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUPS));
                        ArrayList<String> groups = Utils.getGroupsFromString(groupsString);

                        String weekdaysString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAYS));
                        ArrayList<Integer> weekdays = Utils.getWeekdaysFromString(weekdaysString);
                        if(!weekdays.contains(weekDay) || !groups.contains(user.getGroupsString())){
                            continue;
                        }

                        Log.i("INFO", "adding lesson");
                        String lessonID = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
                        String lessonName = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_NAME));
                        int lessonTeacherID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_TEACHER_ID));
                        String lessonBuilding = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_BUILDING));
                        String lessonRoom = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ROOM));
                        String lessonStartTime = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_START_TIME));
                        String lessonEndTime = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_END_TIME));

                        int lessonHasTask = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_HAS_TASK));
                        boolean hasTask = false;
                        if(lessonHasTask != 0) {
                            hasTask = true;
                        }

                        Lesson currentLesson = new Lesson(lessonID, lessonName, lessonTeacherID, lessonBuilding,
                                lessonRoom, lessonStartTime, lessonEndTime, hasTask, weekdays, groups);
                        lessons.add(currentLesson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return lessons;
    }

}
