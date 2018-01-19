package com.example.twentyone.twenty_one.url_manage;

import org.w3c.dom.Document;

/**
 * Created by Annz_2 on 2018/1/18.
 */

public class Nodecheck {
    public static boolean chaeck(String testString,Document document)
    {
        if(document.getElementsByTagName(testString)==null) {
            return Boolean.parseBoolean(null);
        } else
            return true;
    }
}
