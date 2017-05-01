package com.katlab.scheduler.Helpers.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.katlab.scheduler.Helpers.Utils;
import com.katlab.scheduler.Model.Course;
import com.katlab.scheduler.Model.DaySchedule;
import com.katlab.scheduler.Model.FullSchedule;
import com.katlab.scheduler.Model.GroupSchedule;
import com.katlab.scheduler.Model.Lesson;
import com.katlab.scheduler.Model.User;
import com.katlab.scheduler.Presenter.DataProvider;

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

    public static void initializeDatabaseDataFromJSON(ArrayList <Course> courses){
        db.delete(TABLE_LESSONS_NAME, null, null);
        for (int i = 0; i < courses.size(); i++) {
            Course currentCourse = courses.get(i);
            int courseNumber = currentCourse.getCourseNumber();
            ArrayList <GroupSchedule> groupSchedules = currentCourse.getGroupSchedules();
            for (int j = 0; j < groupSchedules.size(); j++) {
                GroupSchedule currentGroupSchedule = groupSchedules.get(j);
                String groupName = currentGroupSchedule.getGroup();
                String subgroupName = currentGroupSchedule.getSubGroup();
                ArrayList <DaySchedule> daySchedules = currentGroupSchedule.getDaySchedules();
                for (int k = 0; k < daySchedules.size(); k++) {
                    DaySchedule currentDaySchedule = daySchedules.get(k);
                    int weekDay = currentDaySchedule.getWeekDay();
                    ArrayList<Lesson> lessons = currentDaySchedule.getLessons();
                    for (int l = 0; l < lessons.size(); l++) {
                        addLesson(lessons.get(l), weekDay, subgroupName, groupName, courseNumber);
                    }
                }
            }
        }
    }

    private static void addLesson(Lesson lesson, int weekDay, String subgroup, String group, int courseNumber){
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
        values.put(COLUMN_NAME_LESSON_WEEKDAY, weekDay);
        values.put(COLUMN_NAME_LESSON_COURSE, courseNumber);
        values.put(COLUMN_NAME_LESSON_GROUP, group);
        values.put(COLUMN_NAME_LESSON_SUBGROUP, subgroup);

        long id = db.insert(TABLE_LESSONS_NAME, null, values);
        Log.i("INFO", "inserted id = " + id + " lesson: " + lesson + " course: " + courseNumber + " group: " + group + " subgroup: " + subgroup);
    }

    public static GroupSchedule getGroupSchedule (int course, String group, String subgroup){
        ArrayList <DaySchedule> daySchedules = getAllEmptyDaySchedules();
        GroupSchedule groupSchedule;

        String selection = COLUMN_NAME_LESSON_COURSE + " = '" + course + "'" + AND +
                COLUMN_NAME_LESSON_GROUP + " = '" + group + "'" + AND +
                COLUMN_NAME_LESSON_SUBGROUP + " = '" + subgroup + "'";
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, selection, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        int lessonID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
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

                        Lesson currentLesson = new Lesson(lessonName, lessonTeacherID, lessonBuilding, lessonRoom, lessonStartTime, lessonEndTime, hasTask);
                        currentLesson.setId(lessonID);

                        int lessonWeekday = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAY));
                        daySchedules.get(lessonWeekday).addLesson(currentLesson);
                        Log.i("INFO", "Added lesson to weekday " + lessonWeekday +
                                " weekdayLessonsSize = " + daySchedules.get(lessonWeekday).getLessons().size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        groupSchedule = new GroupSchedule(group, subgroup, daySchedules);
        return groupSchedule;
    }
    public static ArrayList <Lesson> getAllLessons(int course, String group, String subgroup){
        ArrayList <Lesson> lessons = new ArrayList<>();

        String selection = COLUMN_NAME_LESSON_COURSE + " = '" + course + "'" + AND +
                COLUMN_NAME_LESSON_GROUP + " = '" + group + "'" + AND +
                COLUMN_NAME_LESSON_SUBGROUP + " = '" + subgroup + "'";
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, selection, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        int lessonID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
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

                        Lesson currentLesson = new Lesson(lessonName, lessonTeacherID, lessonBuilding, lessonRoom, lessonStartTime, lessonEndTime, hasTask);
                        currentLesson.setId(lessonID);

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
    public static ArrayList <Course> getAllCourses(){
        ArrayList <Course> courses = new ArrayList<>();
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                int currentCourse = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_COURSE));
                Log.i("INFO", "currentCourse " + currentCourse);
                String currentGroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUP));
                String currentSubgroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_SUBGROUP));
                Log.i("INFO", "currentGroup " + currentGroup);
                Log.i("INFO", "currentSubgroup " + currentSubgroup);

                Course tmpCourse = new Course(currentCourse);
                GroupSchedule tmpGroupSchedule = new GroupSchedule(currentGroup, currentSubgroup);
                ArrayList <Lesson> lessons = new ArrayList<>();
                do{
                    int lessonCourse = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_COURSE));
                    String lessonGroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUP));
                    String lessonSubgroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_SUBGROUP));

                    Log.i("INFO", "lessonGroup " + lessonGroup);
                    Log.i("INFO", "lessonSubgroup " + lessonSubgroup);


                    if(lessonCourse == currentCourse && lessonGroup.equals(currentGroup) && lessonSubgroup.equals(currentSubgroup)){
                        continue;
                    } else if( !lessonSubgroup.equals(currentSubgroup) || !lessonGroup.equals(currentGroup) ){
                        currentSubgroup = lessonSubgroup;
                        currentGroup = lessonGroup;
                        tmpGroupSchedule = new GroupSchedule(currentGroup, currentSubgroup);
                    } else if( lessonCourse != currentCourse ){
                        currentCourse = lessonCourse;
                        tmpCourse = new Course(currentCourse);
                    }

                    int lessonID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
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

                    Lesson currentLesson = new Lesson(lessonName, lessonTeacherID, lessonBuilding, lessonRoom, lessonStartTime, lessonEndTime, hasTask);
                    currentLesson.setId(lessonID);

                    String lessonWeekday = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAY));
                    DaySchedule tmpDaySchedule = new DaySchedule(Integer.parseInt(lessonWeekday));
                    tmpDaySchedule.addLesson(currentLesson);
                    tmpGroupSchedule.addDaySchedule(tmpDaySchedule);
                    tmpCourse.addGroupSchedule(tmpGroupSchedule);
                    courses.add(tmpCourse);

                } while (c.moveToNext());
            }
            c.close();
        }
        return courses;
    }
    private static ArrayList <DaySchedule> getAllEmptyDaySchedules(){
        ArrayList <DaySchedule> allDaySchedules = new ArrayList<>();
        for (int i = 0; i < Utils.SCHEDULE_DAYS_CYCLE; i++) {
            DaySchedule tmp = new DaySchedule(i);
            allDaySchedules.add(tmp);
        }
        return allDaySchedules;
    }

    public static ArrayList <Lesson> getUserLessonsForDay(User user, int weekDay){
        ArrayList <Lesson> lessons = new ArrayList<>();

        String selection = COLUMN_NAME_LESSON_COURSE + " = '" + user.getCourse() + "'" + AND +
                COLUMN_NAME_LESSON_GROUP + " = '" + user.getGroup() + "'" + AND +
                COLUMN_NAME_LESSON_SUBGROUP + " = '" + user.getSubgroup() + "'" + AND +
                COLUMN_NAME_LESSON_WEEKDAY + " = '" + weekDay + "'";
        Log.i("INFO", "SELECTION = " + selection);
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        int numb = 0;
        if(c != null){
            Log.i("INFO", "if c not null = " + numb);
            if(c.moveToFirst()){
                Log.i("INFO", "if c move to next = " + numb);
                do {
                    numb++;
                    Log.i("INFO", "numb = " + numb);
                    try {
                        int lessonCourse = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_COURSE));
                        String lessonGroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUP));
                        String lessonSubgroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_SUBGROUP));
                        int lessonWeekday = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAY));

                        if(lessonCourse != user.getCourse() || !lessonGroup.equals(user.getGroup()) || !lessonSubgroup.equals(user.getSubgroup()) || lessonWeekday != weekDay){
                            continue;
                        }

                        Log.i("INFO", "adding lesson");
                        int lessonID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
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

                        Lesson currentLesson = new Lesson(lessonID, lessonName, lessonTeacherID, lessonBuilding, lessonRoom, lessonStartTime, lessonEndTime, hasTask);
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
