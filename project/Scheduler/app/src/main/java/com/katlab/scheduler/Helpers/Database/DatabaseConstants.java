package com.katlab.scheduler.Helpers.Database;

public interface DatabaseConstants {
    String DATABASE_NAME = "Scheduler.db";

    String ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
    String TEXT_TYPE = " TEXT";
    String INTEGER_TYPE = " INTEGER";
    String COMMA_SEP = ",";

    String AND = " AND ";
    String ASC = " ASC";
    String DESC = " DESC";

    String TABLE_LESSONS_NAME = "lessons";
    String COLUMN_NAME_LESSON_ID = "lessonID";
    String COLUMN_NAME_LESSON_NAME = "lessonName";
    String COLUMN_NAME_LESSON_TEACHER_ID = "teacherID";
    String COLUMN_NAME_LESSON_BUILDING = "lessonBuilding";
    String COLUMN_NAME_LESSON_ROOM = "lessonRoom";
    String COLUMN_NAME_LESSON_START_TIME = "lessonStartTime";
    String COLUMN_NAME_LESSON_END_TIME = "lessonEndTime";
    String COLUMN_NAME_LESSON_HAS_TASK = "lessonHasTask";

    String COLUMN_NAME_LESSON_WEEKDAY= "lessonWeekday";
    String COLUMN_NAME_LESSON_COURSE = "lessonCourse";
    String COLUMN_NAME_LESSON_GROUP = "lessonGroup";
    String COLUMN_NAME_LESSON_SUBGROUP = "lessonSubgroup";

    String TABLE_USERS_NAME = "users";
    String COLUMN_NAME_USER_ID = "userID";
    String COLUMN_NAME_USER_NAME = "userName";
    String COLUMN_NAME_USER_SURNAME = "userSurname";
    String COLUMN_NAME_USER_LOGIN = "userLogin";
    String COLUMN_NAME_USER_PASSWORD = "userPassword";

    //variables for queries
    String [] COLUMNS_LESSONS = {
            COLUMN_NAME_LESSON_ID,COLUMN_NAME_LESSON_NAME,COLUMN_NAME_LESSON_TEACHER_ID,
            COLUMN_NAME_LESSON_BUILDING,COLUMN_NAME_LESSON_ROOM,COLUMN_NAME_LESSON_START_TIME,
            COLUMN_NAME_LESSON_END_TIME, COLUMN_NAME_LESSON_HAS_TASK,
            COLUMN_NAME_LESSON_WEEKDAY, COLUMN_NAME_LESSON_COURSE,
            COLUMN_NAME_LESSON_GROUP, COLUMN_NAME_LESSON_SUBGROUP};
    String SELECTION = null;
    String[] SELECTION_ARGS = null;
    String GROUP_BY = null;
    String HAVING = null;
    String ORDER_BY = COLUMN_NAME_LESSON_COURSE + ASC + COMMA_SEP +
            COLUMN_NAME_LESSON_GROUP + ASC + COMMA_SEP +
            COLUMN_NAME_LESSON_SUBGROUP + ASC + COMMA_SEP +
            COLUMN_NAME_LESSON_WEEKDAY + ASC;
}
