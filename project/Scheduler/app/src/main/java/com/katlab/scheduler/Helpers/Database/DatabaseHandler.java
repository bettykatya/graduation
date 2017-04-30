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
import com.katlab.scheduler.Presenter.DataProvider;

import java.util.ArrayList;

public class DatabaseHandler implements DatabaseConstants {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private static int numb = 0;

    public static void openDB(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        initializeDatabaseDataFromJSON(DataProvider.getCourses(context));
    }
    public static void closeDB(){
        dbHelper.close();
    }

    private static void initializeDatabaseDataFromJSON(ArrayList <Course> courses){
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
        values.put(COLUMN_NAME_LESSON_ID, numb);
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

        Log.i("INFO", "lesson was added: " + lesson);

        db.insert(TABLE_LESSONS_NAME, null, values);

        numb++;
    }


    public static ArrayList <Course> getAllCourses(){
        ArrayList <Course> courses = new ArrayList<>();
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                int currentCourse = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_COURSE));
                String currentGroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUP));
                String currentSubgroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_SUBGROUP));

                Course tmpCourse = new Course(currentCourse);
                GroupSchedule tmpGroupSchedule = new GroupSchedule(currentGroup, currentSubgroup);
                ArrayList <Lesson> lessons = new ArrayList<>();
                do{
                    int lessonCourse = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_COURSE));
                    String lessonGroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUP));
                    String lessonSubgroup = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_SUBGROUP));

                    if(lessonCourse == currentCourse && lessonGroup.equals(currentGroup) && lessonSubgroup.equals(currentSubgroup)){
                        continue;
                    } else if( !lessonSubgroup.equals(currentSubgroup) || !lessonGroup.equals(currentGroup) ){
                        tmpCourse.addGroupSchedule(tmpGroupSchedule);
                        currentSubgroup = lessonSubgroup;
                        currentGroup = lessonGroup;
                        tmpGroupSchedule = new GroupSchedule(currentGroup, currentSubgroup);
                    } else if( lessonCourse != currentCourse ){
                        courses.add(tmpCourse);
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

                    Lesson currentLesson = new Lesson(lessonID, lessonName, lessonTeacherID, lessonBuilding, lessonRoom, lessonStartTime, lessonEndTime, hasTask);

                    String lessonWeekday = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAY));
                    DaySchedule tmpDaySchedule = new DaySchedule(Integer.parseInt(lessonWeekday));
                    tmpDaySchedule.addLesson(currentLesson);
                    tmpGroupSchedule.addDaySchedule(tmpDaySchedule);

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
}
