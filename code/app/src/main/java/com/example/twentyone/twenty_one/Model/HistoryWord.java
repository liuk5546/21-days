package com.example.twentyone.twenty_one.Model;

/**
 * Created by liuk on 2018/1/5.
 */

public class HistoryWord {
    private String word;
    private String chineseWithPart;
    //这个叫词性

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public void setChineseWithPart(String chineseWithPart) {
        this.chineseWithPart = chineseWithPart;
    }
}
