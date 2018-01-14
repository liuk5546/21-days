package com.example.twentyone.twenty_one.DBControl;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.twentyone.twenty_one.Model.SearchResult;

import java.sql.Timestamp;

/**
 * Created by liuk on 2018/1/12.
 */

public class CollectionWordManager {
    //加入数据
    public static void insert(SearchResult searchResult, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("word",searchResult.getKey());
        values.put("chineseWithPart",searchResult.getAcceptation());
        values.put("times",1);
        values.put("last_review_date",new Timestamp(System.currentTimeMillis()).toString());
        db.insert(DBHelper.DB_NAME,null,values);
    }

    /**
     *
     * @param reviewWord
     * 给出你复习的单词
     * @param db
     * 给出数据库
     */
    public static void review(String reviewWord,SQLiteDatabase db) {

    }
}
