package com.example.twentyone.twenty_one.url_manage;

/**
 * Created by Annz_2 on 2018/1/18.
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Annz_2
 * url需要单独处理类[外部xml处理]
 */

public class url{
    public static String getURLContent(String url, String encoding) {
        StringBuffer content = new StringBuffer();
        try {
            URL u = new URL(url);
            InputStream in = new BufferedInputStream(u.openStream());
            InputStreamReader theHTML = new InputStreamReader(in, encoding);
            int c;
            while ((c = theHTML.read()) != -1) {
                content.append((char) c);
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return content.toString();
    }
}