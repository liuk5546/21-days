package com.example.twentyone.twenty_one.Model;

import java.sql.Timestamp;

/**
 * Created by liuk on 2018/1/12.
 */

public class CollectionWord {
    private String word;
    private String chineseWithPart;
    private int times;
    private Timestamp lastReviewDate;

    public CollectionWord(SearchResult sr){
        word = sr.getKey();
        chineseWithPart = sr.getAcceptation();
    }

    public String getLastReviewDate() {
        return lastReviewDate.toString();
    }

    public void setLastReviewDate(String lastReviewDate) {
        this.lastReviewDate = Timestamp.valueOf(lastReviewDate);
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getWord() {
        return word;
    }

    public String getChineseWithPart() {
        String result;
        if(chineseWithPart.length()>9){
            result = chineseWithPart.substring(0,6)+"...";
        }else{
            result = chineseWithPart;
        }
        return result;
    }
}
