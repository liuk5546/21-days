package com.example.twentyone.twenty_one.DBControl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.twentyone.twenty_one.Model.CollectionWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuk on 2018/1/11.
 */

public class HistoryWordManager {
    public static void insert(String word , SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("word",word);
        //values.put("chineseWithPart",searchResult.getAcceptation());
        //values.put("times",1);
        //values.put("last_review_date",new Timestamp(System.currentTimeMillis()).toString());
        db.insert(DBHelper.TABLE_NAMES[0],null,values);
    }

    public static List<CollectionWord> loadAllCollection(SQLiteDatabase db){
        List<CollectionWord> collectionWordList = new ArrayList<>();
        CollectionWord collectionWord;
        Cursor cursor = db.query(DBHelper.TABLE_NAMES[0],null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                collectionWord = new CollectionWord(cursor.getString(cursor.getColumnIndex("word"))
                        ," ");
                collectionWordList.add(collectionWord);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  collectionWordList;
    }
}
