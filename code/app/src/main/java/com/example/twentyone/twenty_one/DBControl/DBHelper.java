package com.example.twentyone.twenty_one.DBControl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by liuk on 2018/1/11.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "english.db";
    public static final String [] TABLE_NAMES = {"Histroy","collect"};
    public static final String [] CREATE = {
            "create table if not exists "
                    + TABLE_NAMES[0]
                    +"(Id integer primary key autoincrement," +
                    "word text unique," +
                    "chineseWithPart text)",
            "create table if not exists "
                    + TABLE_NAMES[1]
                    +"(Id integer primary key autoincrement," +
                    "word text unique," +
                    "times integer," +
                    "last_review_date text)"
    };
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE[0]);
        db.execSQL(CREATE[1]);
        Log.d("DATABASE","Database Create!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
