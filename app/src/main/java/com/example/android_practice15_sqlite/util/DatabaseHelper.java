package com.example.android_practice15_sqlite.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "helper_employee_database";

    private static final String TABLE_NAME = "employee";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOINING_DATE = "joining_Date";
    private static final String COLUMN_SALARY = "salary";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID+ " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME+ " VARCHAR(20) NOT NULL, " +
                COLUMN_DEPT+ " VARCHAR(20) NOT NULL, " +
                COLUMN_JOINING_DATE+ " DATETIME  NOT NULL, " +
                COLUMN_SALARY+ " DOUBLE NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the table if exist
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    // INSERT
    public boolean addEmployee(String name, String department, String joiningDate, double salary){
        // we need writable instance of db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need content values to write into db
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_JOINING_DATE, joiningDate);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        return sqLiteDatabase.insert(TABLE_NAME,null, contentValues) != -1;
    }

    public Cursor getAllEmployees(){
        // we need readable instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getEmployee(int id){
        // we need readable instance of db
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?", new String[]{String.valueOf(id)});
    }

    public boolean updateEmployee(int id, String name, String department, double salary){
        // we need writable instance of db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need content values to write into db
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DEPT, department);
        contentValues.put(COLUMN_SALARY, String.valueOf(salary));

        // UPDATE
        return  sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID+"=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteEmployee(int id){
        // we need writable instance of db
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+ "=?", new String[]{String.valueOf(id)}) > 0;
    }
}
