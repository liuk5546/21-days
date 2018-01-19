package com.example.twentyone.twenty_one.DBControl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.twentyone.twenty_one.Model.CollectionWord;
import com.example.twentyone.twenty_one.Model.SearchResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuk on 2018/1/12.
 */

public class CollectionWordManager {
    //加入数据
    public static void insert(SearchResult searchResult, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("word",searchResult.getKey());
        values.put("chineseWithPart",searchResult.getAcceptation().get(0));
        values.put("times",1);
        values.put("last_review_date",new Timestamp(System.currentTimeMillis()).toString());
        //tiems 是次数
        //word 是单词
        //Document_manage 是最近一次复习时间
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

    /**
     * 删除指定单词
     * @param Word
     * @param db
     */
    public static void deleteByWord(String Word,SQLiteDatabase db){
        db.delete("collect","word = ?",new String[]{Word});
    }
    /**
     *
     */
    public static List<CollectionWord> loadAllCollection(SQLiteDatabase db){
        List<CollectionWord> collectionWordList = new ArrayList<>();
        CollectionWord collectionWord;
        Cursor cursor = db.query("collect",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                collectionWord = new CollectionWord(cursor.getString(cursor.getColumnIndex("word"))
                        ,cursor.getString(cursor.getColumnIndex("chineseWithPart")));
                collectionWord.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                collectionWord.setTimes(cursor.getInt(cursor.getColumnIndex("times")));
                collectionWord.setLastReviewDate(cursor.getString(cursor.getColumnIndex("last_review_date")));
                collectionWordList.add(collectionWord);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  collectionWordList;
    }

    /**
     * 需要给一个使用findByWord查找的CollectionWord
     * db是可写的。
     * @param cw
     * @param db
     */
    public static void review(CollectionWord cw,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("times",cw.getTimes()+1);
        contentValues.put("last_review_date",new Timestamp(System.currentTimeMillis()).toString());
        db.update("collect",contentValues,"Id = ?",new String[]{String.valueOf(cw.getId())});
    }
}
