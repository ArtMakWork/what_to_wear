package com.artmakdeveloper.whattoweartoday.DBUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //final String LOG_TAG = "myLogs";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WhatTo";
    private static final String TABLE_NAME = "MyTable";
    private static final String KEY_DATE = "date";
    private static final String KEY_STRING = "string";

    ContentValues cv = new ContentValues();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d(LOG_TAG, "---OnCreate "+DATABASE_NAME+" ---");

        String CREATE_DAY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + "id integer primary key autoincrement,"
                + KEY_DATE +" text,"
                + KEY_STRING +" text"
                + ");";

        db.execSQL(CREATE_DAY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addLine(SQLiteDatabase db, String date, String string){
        //Log.d(LOG_TAG, "---Insert in " + TABLE_NAME + "---");
        cv.put(KEY_DATE, date);
        cv.put(KEY_STRING, string);
        long rowID = db.insert(TABLE_NAME, null, cv);
        //Log.d(LOG_TAG, "row inserted, ID= " + rowID);
    }

    public void showFullTable(SQLiteDatabase db) {
        //Log.d(LOG_TAG, "---ROWs in " + TABLE_NAME + "---");
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int dateColIndex = c.getColumnIndex(KEY_DATE);
            int stringColIndex = c.getColumnIndex(KEY_STRING);
            do {
                //Log.d(LOG_TAG, "ID=" + c.getInt(idColIndex) +
                //        " /Date=" + c.getString(dateColIndex) +
                //        " /String=" + c.getString(stringColIndex));
            } while (c.moveToNext());
        } else
            //Log.d(LOG_TAG, "0 rows");
        c.close();
    }


    public void clearAllTable(SQLiteDatabase db) {
        //Log.d(LOG_TAG, "---Clear "+TABLE_NAME+"---");
        int clearCount = db.delete(TABLE_NAME, null, null);
        //Log.d(LOG_TAG, "Clear rows =" + clearCount);
    }

    public String getLastLine(SQLiteDatabase db) {

        //Log.d(LOG_TAG, "---Last row in " + TABLE_NAME + "---");
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        int idColIndex = c.getColumnIndex("id");
        int dateColIndex = c.getColumnIndex(KEY_DATE);
        int stringColIndex = c.getColumnIndex(KEY_STRING);
        String res ;

        if (c.moveToLast()) {
            //Log.d(LOG_TAG, "ID=" + c.getInt(idColIndex) +
             //           " /Date=" + c.getString(dateColIndex) +
             //           " /String=" + c.getString(stringColIndex));
            res = c.getString(dateColIndex);
        } else {
           // Log.d(LOG_TAG, "0 rows");
            res = "nothing";
        }
        c.close();
    return res;
    }

    public String getLastText(SQLiteDatabase db) {

        //Log.d(LOG_TAG, "---Last row in " + TABLE_NAME + "---");
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        int idColIndex = c.getColumnIndex("id");
        int dateColIndex = c.getColumnIndex(KEY_DATE);
        int stringColIndex = c.getColumnIndex(KEY_STRING);
        String res ;

        if (c.moveToLast()) {
            //Log.d(LOG_TAG, "ID=" + c.getInt(idColIndex) +
            //        " /Date=" + c.getString(dateColIndex) +
            //        " /String=" + c.getString(stringColIndex));
            res = c.getString(stringColIndex);
        } else {
           //Log.d(LOG_TAG, "0 rows");
            res = "nothing";
        }
        c.close();
        return res;
    }

    public  void delLastLine(SQLiteDatabase db) {

        //Log.d(LOG_TAG, "---Last row in " + TABLE_NAME + "---");
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        int idColIndex = c.getColumnIndex("id");
        if (c.moveToLast()) {
            db.delete(TABLE_NAME, String.valueOf(c.getInt(idColIndex)),null);
        } else {
            //Log.d(LOG_TAG, "0 rows");
        }
        c.close();
    }

}
