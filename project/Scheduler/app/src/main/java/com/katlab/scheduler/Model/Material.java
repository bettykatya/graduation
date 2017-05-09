package com.katlab.scheduler.Model;


public class Material {
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
}
