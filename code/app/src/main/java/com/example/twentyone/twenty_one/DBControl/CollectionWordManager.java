package com.example.twentyone.twenty_one.DBControl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.twentyone.twenty_one.Model.CollectionWord;
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
        db.insert(DBHelper.TABLE_NAMES[1],null,values);
    }
    //通过单词找内容
    public static CollectionWord findByWord(String Word,SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from collect where word = \'"+ Word+"\'",null);
        CollectionWord cw;
        if(cursor.moveToFirst()){
            cw = new CollectionWord(cursor.getString(cursor.getColumnIndex("word"))
                    ,cursor.getString(cursor.getColumnIndex("chineseWithPart")));
            cw.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            cw.setLastReviewDate(cursor.getString(cursor.getColumnIndex("last_review_date")));
            cw.setTimes(cursor.getInt(cursor.getColumnIndex("times")));
        }else{
            cw = new CollectionWord("error","error");
            cw.setId(-1);
        }
         return cw;
    }
    public static void deleteByWord(String Word,SQLiteDatabase db){
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
