package com.example.twentyone.twenty_one.Util;

import java.util.Calendar;

/**
 * Created by Annz_2 on 2018/1/18.
 */

public class Time_contrl {
    public int getSecond() {
        Calendar cal = Calendar.getInstance();
        int second = cal.get(Calendar.SECOND);
        return second;
    }
}
