package com.katlab.scheduler.Model;


import android.content.Context;
import android.util.Log;

import com.katlab.scheduler.Helpers.Database.DatabaseHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Material implements Serializable {
    private int id;
    private String lessonID;
    private String name;
    private String file;

    private static int number;

    public Material(String lessonID, String name, String file){
        this.lessonID = lessonID;
        this.name = name;
        this.file = file;
        this.id = number;
        number++;
    }
    public Material(int id, String lessonID, String name, String file){
        this.lessonID = lessonID;
        this.name = name;
        this.file = file;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getLessonID() {
        return lessonID;
    }
    public String getName() {
        return name;
    }
    public String getFile() {
        return file;
    }

    public Lesson getLesson(){
        return DatabaseHandler.getLessonById(this.lessonID);
    }

    public String getTextContent(Context context){
        String result = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }
}
