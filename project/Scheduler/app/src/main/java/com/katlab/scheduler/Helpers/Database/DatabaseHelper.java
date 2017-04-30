package com.katlab.scheduler.Helpers.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper implements DatabaseConstants{
    private static int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_LESSONS_TABLE =
            "CREATE TABLE " + DatabaseConstants.TABLE_LESSONS_NAME + " (" +
                    DatabaseConstants.COLUMN_NAME_LESSON_ID + ID_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_NAME + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_TEACHER_ID + INTEGER_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_BUILDING + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_ROOM + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_START_TIME + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_END_TIME + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_HAS_TASK + INTEGER_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_WEEKDAY + INTEGER_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_COURSE + INTEGER_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_GROUP + TEXT_TYPE + COMMA_SEP +
                    DatabaseConstants.COLUMN_NAME_LESSON_SUBGROUP + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_LESSONS_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_LESSONS_NAME;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LESSONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Clear all data
        db.execSQL(SQL_DELETE_LESSONS_TABLE);

        //recreate tables
        onCreate(db);
    }
}
