package com.katlab.scheduler.model.database;

public interface DatabaseConstants {
    /*--------------- COMMON VARIABLES-------------- */
    String DATABASE_NAME = "Scheduler.db";

    String TEXT_TYPE = " TEXT";
    String INTEGER_TYPE = " INTEGER";
    String COMMA_SEP = ",";

    String SELECTION = null;
    String[] SELECTION_ARGS = null;
    String GROUP_BY = null;
    String HAVING = null;
    String ORDER_BY = null;

    String AND = " AND ";
    String ASC = " ASC";
    String DESC = " DESC";

    /*--------------- LESSONS TABLE VARIABLES-------------- */
    String TABLE_LESSONS_NAME = "lessons";
    String COLUMN_NAME_LESSON_ID = "lessonID";
    String COLUMN_NAME_LESSON_NAME = "lessonName";
    String COLUMN_NAME_LESSON_TEACHER_ID = "teacherID";
    String COLUMN_NAME_LESSON_BUILDING = "lessonBuilding";
    String COLUMN_NAME_LESSON_ROOM = "lessonRoom";
    String COLUMN_NAME_LESSON_START_TIME = "lessonStartTime";
    String COLUMN_NAME_LESSON_END_TIME = "lessonEndTime";
    String COLUMN_NAME_LESSON_HAS_TASK = "lessonHasTask";
    String COLUMN_NAME_LESSON_WEEKDAYS= "lessonWeekday";
    String COLUMN_NAME_LESSON_GROUPS = "lessonGroup";

    String [] COLUMNS_LESSONS = {
            COLUMN_NAME_LESSON_ID,COLUMN_NAME_LESSON_NAME,COLUMN_NAME_LESSON_TEACHER_ID,
            COLUMN_NAME_LESSON_BUILDING,COLUMN_NAME_LESSON_ROOM,COLUMN_NAME_LESSON_START_TIME,
            COLUMN_NAME_LESSON_END_TIME, COLUMN_NAME_LESSON_HAS_TASK,
            COLUMN_NAME_LESSON_WEEKDAYS, COLUMN_NAME_LESSON_GROUPS};


    /*--------------- MATERIALS TABLE VARIABLES-------------- */
    String TABLE_MATERIALS_NAME = "materials";
    String COLUMN_NAME_MATERIAL_ID = "materialID";
    String COLUMN_NAME_MATERIAL_NAME = "materialName";
    String COLUMN_NAME_MATERIAL_LESSON_ID = "materialLessonID";
    String COLUMN_NAME_MATERIAL_FILE = "materialFile";

    String [] COLUMNS_MATERIALS = {COLUMN_NAME_MATERIAL_ID, COLUMN_NAME_MATERIAL_NAME,
            COLUMN_NAME_MATERIAL_LESSON_ID, COLUMN_NAME_MATERIAL_FILE};

}
