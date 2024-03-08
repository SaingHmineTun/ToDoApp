package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskDataBaseHelper extends SQLiteOpenHelper {
    public static  final  int DB_VERSION = 1;
    public static  final String DB_NAME = "Tododata.db";
    public static  final String DB_TABLE = "tasks";
    public static  final String COLUMN_ID = "taskID";
    public static  final String COLUMN_TITLE = "tasksTitle";
    public static  final String COLUMN_DETAILS = "tasksDetail";
    public static  final String COLUMN_DATE = "tasksDate";
    public static  final String COLUMN_TIME = "tasksTime";

    public TaskDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_DETAILS + " TEXT, " +
                COLUMN_DATE + " TEXT, " + COLUMN_TIME + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i>i1)return;
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }

    public  long addTasks(TaskModel taskModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,taskModel.getTaskTitle());
        contentValues.put(COLUMN_DETAILS,taskModel.getTaskDetail());
        contentValues.put(COLUMN_DATE,taskModel.getTaskDate());
        contentValues.put(COLUMN_TIME,taskModel.getTaskTime());
        long ID = db.insert(DB_TABLE,null,contentValues);
        return  ID;
    }

    public List<TaskModel> getTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TaskModel> allTasks = new ArrayList<>();
        String query = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(cursor.getInt(0));
                taskModel.setTaskTitle(cursor.getString(1));
                taskModel.setTaskDetail(cursor.getString(2));
                taskModel.setTaskDate(cursor.getString(3));
                taskModel.setTaskTime(cursor.getString(4));
                allTasks.add(taskModel);
            }while (cursor.moveToNext());
        }
        return  allTasks;
    }
    public  TaskModel getTasksByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String [] query = new String[] {COLUMN_ID,COLUMN_TITLE,COLUMN_DETAILS,COLUMN_DATE,COLUMN_TIME};
        Cursor cursor = db.query(DB_TABLE,query,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if ((cursor!=null))cursor.moveToFirst();
        return  new TaskModel(
                Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
    }
    void  deleteNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateTask(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, taskModel.getTaskTitle());
        values.put(COLUMN_DETAILS, taskModel.getTaskDetail());
        values.put(COLUMN_DATE, taskModel.getTaskDate());
        values.put(COLUMN_TIME, taskModel.getTaskTime());

        // Updating row
        return db.update(DB_TABLE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(taskModel.getId())});
    }

}
