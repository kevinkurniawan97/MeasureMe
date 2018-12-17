package com.example.kevin.measureme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Kevin on 5/27/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "history.db";
    public static final String HISTORY_TABLE_NAME = "history";
    public static final String HISTORY_COLUMN_ID = "id";
    public static final String HISTORY_COLUMN_NAME = "name";
    public static final String HISTORY_COLUMN_DATETIME = "datetime";
    public static final String HISTORY_COLUMN_GENDER = "gender";
    public static final String HISTORY_COLUMN_SHOULDER = "shoulder";
    public static final String HISTORY_COLUMN_CHEST = "chest";
    public static final String HISTORY_COLUMN_WAIST = "waist";
    public static final String HISTORY_COLUMN_SIZE = "size";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table history " +
                        "(id integer primary key, name text, datetime default CURRENT_DATE, " +
                        "gender text,shoulder real, chest real, waist real, size text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertHistory (String name, String gender, int shoulder, double chest, double waist, String size) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("datetime", dateFormat.format(date));
        contentValues.put("gender", gender);
        contentValues.put("shoulder", shoulder);
        contentValues.put("chest", chest);
        contentValues.put("waist", waist);
        contentValues.put("size", size);
        db.insert("history", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HISTORY_TABLE_NAME);
        return numRows;
    }

    public boolean updateHistory (Integer id, String name, String gender, int shoulder, double chest, double waist, String size) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("datetime", dateFormat.format(date));
        contentValues.put("gender", gender);
        contentValues.put("shoulder", shoulder);
        contentValues.put("chest", chest);
        contentValues.put("waist", waist);
        contentValues.put("size", size);
        db.update("history", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void deleteAllHistory () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM history");
        db.close();
    }

    public Integer deleteHistory (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("history",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<HistoryData> getAllHistory() {
        ArrayList<HistoryData> array_list = new ArrayList<HistoryData>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String name = res.getString(res.getColumnIndex(HISTORY_COLUMN_NAME));
            int id = res.getInt(res.getColumnIndex(HISTORY_COLUMN_ID));
            String datetime = res.getString(res.getColumnIndex(HISTORY_COLUMN_DATETIME));
            HistoryData historydata = new HistoryData(id, name, datetime);
            array_list.add(historydata);
            res.moveToNext();
        }
        return array_list;
    }
}