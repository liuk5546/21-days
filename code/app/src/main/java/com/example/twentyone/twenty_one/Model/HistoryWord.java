package com.example.twentyone.twenty_one.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuk on 2018/1/5.
 */

public class HistoryWord {
    private String word;
    private List<String> chineseWithPart = new ArrayList<String>();
    //这个叫词性

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setChineseWithPart(String chineseWithPart) {
        this.chineseWithPart.add(chineseWithPart);
    }


    public String getChineseWithPart() {
        String ret = "";
        if (chineseWithPart.size()>=1){
            for (String x:chineseWithPart
                 ) {
                ret+=x;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        HistoryWord test = new HistoryWord();
        test.setChineseWithPart("1");
        test.setChineseWithPart("1");
        test.setChineseWithPart("1");
        test.setChineseWithPart("1");
        System.out.print(test.getChineseWithPart());
    }
}
