package com.katlab.scheduler.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.katlab.scheduler.model.adds.Utils;
import com.katlab.scheduler.model.database.DatabaseConstants;
import com.katlab.scheduler.model.database.DatabaseHelper;
import com.katlab.scheduler.model.objects.Lesson;
import com.katlab.scheduler.model.adds.LessonComparator;
import com.katlab.scheduler.model.objects.Material;
import com.katlab.scheduler.model.adds.Roles;
import com.katlab.scheduler.model.objects.User;

import java.util.ArrayList;
import java.util.Collections;

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

    public static void initializeDatabaseDataFromJSON(ArrayList<Lesson> lessons, ArrayList<Material> materials){
        db.delete(TABLE_LESSONS_NAME, null, null);
        for (int i = 0; i < lessons.size(); i++) {
            Lesson currentLesson = lessons.get(i);
            addLesson(currentLesson);
        }

        db.delete(TABLE_MATERIALS_NAME, null, null);
        for (int i = 0; i < materials.size(); i++) {
            Material currentMaterial = materials.get(i);
            addMaterial(currentMaterial);
        }
    }

    private static void addLesson(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LESSON_ID, lesson.getId());
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
    }
    private static void deleteLesson(Lesson lesson){
        String id = lesson.getId();
        String where =  " " + COLUMN_NAME_LESSON_ID + "='" + id + "'";
        db.delete(TABLE_LESSONS_NAME, where, null );
    }
    public static void editLesson(Lesson oldLesson, Lesson newLesson){
        deleteLesson(oldLesson);
        addLesson(newLesson);
    }

    public static ArrayList <Lesson> getUserLessonsForDay(User user, int weekDay){
        Log.i("INFO", "role = " + user.getRole());
        if(user.getRole().equals(Roles.STUDENT)){
            return getStudentLessonsForDay(user, weekDay);
        } else if(user.getRole().equals(Roles.TEACHER)){
            return getTeacherLessonsForDay(user, weekDay);
        }
        return new ArrayList<>();
    }
    private static ArrayList <Lesson> getStudentLessonsForDay(User user, int weekDay){
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
        Collections.sort(lessons, new LessonComparator());
        return lessons;
    }
    private static ArrayList <Lesson> getTeacherLessonsForDay(User teacher, int weekDay){

        ArrayList <Lesson> lessons = new ArrayList<>();
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        int lessonTeacherID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_TEACHER_ID));

                        String weekdaysString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAYS));
                        ArrayList<Integer> weekdays = Utils.getWeekdaysFromString(weekdaysString);

                        if(!weekdays.contains(weekDay) || lessonTeacherID != teacher.getId()){
                            continue;
                        }


                        String groupsString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUPS));
                        ArrayList<String> groups = Utils.getGroupsFromString(groupsString);

                        String lessonID = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
                        String lessonName = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_NAME));
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
        Collections.sort(lessons, new LessonComparator());
        return lessons;
    }
    public static Lesson getLessonById(String id){
        Lesson lesson = null;
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String lessonID = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
                        if(!lessonID.equals(id)){
                            continue;
                        }

                        String groupsString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUPS));
                        ArrayList<String> groups = Utils.getGroupsFromString(groupsString);

                        String weekdaysString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAYS));
                        ArrayList<Integer> weekdays = Utils.getWeekdaysFromString(weekdaysString);

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

                        lesson = new Lesson(lessonID, lessonName, lessonTeacherID, lessonBuilding,
                                lessonRoom, lessonStartTime, lessonEndTime, hasTask, weekdays, groups);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return lesson;
    }

    public static ArrayList <Lesson> getAllUniqueLessons(User user){
        Log.i("INFO", "role = " + user.getRole());
        if(user.getRole().equals(Roles.STUDENT)){
            return getAllUniqueLessonsForStudent(user);
        } else if(user.getRole().equals(Roles.TEACHER)){
            return getAllUniqueLessonsForTeacher(user);
        }
        return new ArrayList<>();
    }
    private static ArrayList <Lesson> getAllUniqueLessonsForStudent(User user){
        ArrayList <Lesson> lessons = new ArrayList<>();
        Cursor c = db.query( TABLE_LESSONS_NAME, COLUMNS_LESSONS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String groupsString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_GROUPS));
                        ArrayList<String> groups = Utils.getGroupsFromString(groupsString);

                        if(!groups.contains(user.getGroupsString())){
                            continue;
                        }

                        String weekdaysString = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_WEEKDAYS));
                        ArrayList<Integer> weekdays = Utils.getWeekdaysFromString(weekdaysString);

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

                        boolean hasLesson = false;
                        for (int i = 0; i < lessons.size(); i++) {
                            if (lessons.get(i).getId().equals(lessonID)){
                                hasLesson = true;
                                break;
                            }
                        }
                        if(hasLesson){
                            continue;
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
        Collections.sort(lessons, new LessonComparator());
        return lessons;
    }
    private static ArrayList <Lesson> getAllUniqueLessonsForTeacher(User teacher){
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

                        String lessonID = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ID));
                        String lessonName = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_NAME));
                        int lessonTeacherID = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_TEACHER_ID));
                        String lessonBuilding = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_BUILDING));
                        String lessonRoom = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_ROOM));
                        String lessonStartTime = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_START_TIME));
                        String lessonEndTime = c.getString(c.getColumnIndex(COLUMN_NAME_LESSON_END_TIME));

                        if(lessonTeacherID != teacher.getId()){
                            continue;
                        }

                        int lessonHasTask = c.getInt(c.getColumnIndex(COLUMN_NAME_LESSON_HAS_TASK));
                        boolean hasTask = false;
                        if(lessonHasTask != 0) {
                            hasTask = true;
                        }

                        boolean hasLesson = false;
                        for (int i = 0; i < lessons.size(); i++) {
                            if (lessons.get(i).getId().equals(lessonID)){
                                hasLesson = true;
                                break;
                            }
                        }
                        if(hasLesson){
                            continue;
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
        Collections.sort(lessons, new LessonComparator());
        return lessons;
    }

    private static void addMaterial(Material material){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_MATERIAL_ID, material.getId());
        values.put(COLUMN_NAME_MATERIAL_LESSON_ID, material.getLessonID());
        values.put(COLUMN_NAME_MATERIAL_NAME, material.getName());
        values.put(COLUMN_NAME_MATERIAL_FILE, material.getFile());

        db.insert(TABLE_MATERIALS_NAME, null, values);
    }
    public static ArrayList<Material> getAllMaterials(){
        ArrayList <Material> materials = new ArrayList<>();
        Cursor c = db.query( TABLE_MATERIALS_NAME, COLUMNS_MATERIALS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        int materialID = c.getInt(c.getColumnIndex(COLUMN_NAME_MATERIAL_ID));
                        String materialName = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_NAME));
                        String materialLessonID = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_LESSON_ID));
                        String materialFile = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_FILE));

                        Material currentMaterial = new Material(materialID, materialLessonID, materialName, materialFile);
                        materials.add(currentMaterial);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return materials;
    }
    public static ArrayList<Material> getMaterialsForLesson(String lessonId){
        ArrayList <Material> materials = new ArrayList<>();
        Cursor c = db.query( TABLE_MATERIALS_NAME, COLUMNS_MATERIALS, SELECTION, SELECTION_ARGS, GROUP_BY, HAVING, ORDER_BY);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    try {
                        String materialLessonID = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_LESSON_ID));
                        if(!materialLessonID.equals(lessonId)){
                            continue;
                        }
                        int materialID = c.getInt(c.getColumnIndex(COLUMN_NAME_MATERIAL_ID));
                        String materialName = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_NAME));
                        String materialFile = c.getString(c.getColumnIndex(COLUMN_NAME_MATERIAL_FILE));

                        Material currentMaterial = new Material(materialID, materialLessonID, materialName, materialFile);
                        materials.add(currentMaterial);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        return materials;
    }
}
