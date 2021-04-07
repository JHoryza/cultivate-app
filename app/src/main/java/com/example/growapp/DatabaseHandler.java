package com.example.growapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "grow.db";

    public static final String TABLE_HABITS = "HABITS_TABLE";
    public static final String COLUMN_ID = "HABIT_ID";
    public static final String COLUMN_NAME = "HABIT_NAME";
    public static final String COLUMN_INTERVAL = "HABIT_INTERVAL";
    public static final String COLUMN_TIME_UNIT = "HABIT_TIME_UNIT";
    public static final String COLUMN_CURRENT_STATE = "HABIT_CURRENT_STATE";
    public static final String COLUMN_TIMESTAMP = "HABIT_TIMESTAMP";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_HABITS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_INTERVAL + " INTEGER, " +
                COLUMN_TIME_UNIT + " TEXT, " +
                COLUMN_CURRENT_STATE + " INTEGER, " +
                COLUMN_TIMESTAMP + " TEXT" + ")";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean create(Habit habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, habit.getName());
        cv.put(COLUMN_INTERVAL, habit.getInterval());
        cv.put(COLUMN_TIME_UNIT, habit.getTimeUnit());
        cv.put(COLUMN_CURRENT_STATE, habit.getCurrentState());
        cv.put(COLUMN_TIMESTAMP, habit.getTimestamp().toString());
        db.insert(TABLE_HABITS, null, cv);
        db.close();
        return true;
    }

    public boolean delete(Habit habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_HABITS + " WHERE " + COLUMN_ID + " = " + habit.getId();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public void update(Habit habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, habit.getName());
        cv.put(COLUMN_INTERVAL, habit.getInterval());
        cv.put(COLUMN_TIME_UNIT, habit.getTimeUnit());
        cv.put(COLUMN_CURRENT_STATE, habit.getCurrentState());
        cv.put(COLUMN_TIMESTAMP, habit.getTimestamp().toString());
        db.update(TABLE_HABITS, cv, COLUMN_ID + " = " + habit.getId(), null);
        db.close();
    }

    public ArrayList<Habit> getAllHabits() {
        ArrayList<Habit> habitsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_HABITS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int habitId = cursor.getInt(0);
                String habitName = cursor.getString(1);
                int habitInterval = cursor.getInt(2);
                String habitTimeUnit = cursor.getString(3);
                int habitCurrentState = cursor.getInt(4);
                String habitTimestamp = cursor.getString(5);

                Habit habit = new Habit(habitId, habitName, habitInterval, habitTimeUnit, habitCurrentState, habitTimestamp);
                habitsList.add(habit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return habitsList;
    }
}
