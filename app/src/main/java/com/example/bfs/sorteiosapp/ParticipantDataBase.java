package com.example.bfs.sorteiosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParticipantDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Participants.db";
    public static final String TABLE_NAME = "Participants_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PROB";
    public static final String COL_4 = "SKILL";

    public ParticipantDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PROB TEXT,SKILL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public int insertData(String name, int prob, int skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, Integer.toString(prob));
        contentValues.put(COL_4,Integer.toString(skill));
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return -1;
        else
            return (int)result;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(int id, String name, int prob, int skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,Integer.toString(prob));
        contentValues.put(COL_4,Integer.toString(skill));
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { Integer.toString(id) });
        return true;
    }

    public Integer deleteData (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {Integer.toString(id)});
    }
}
