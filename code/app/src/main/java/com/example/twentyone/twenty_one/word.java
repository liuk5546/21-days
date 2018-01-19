package com.example.twentyone.twenty_one;

import cn.bmob.v3.BmobObject;

/**
 * Created by Annz_2 on 2018/1/19.
 */

public class word extends BmobObject {
    private String word;
    private int count;

    public String getWord() {
        return word;
    }
    public void setWord(String name) {
        this.word = name;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
