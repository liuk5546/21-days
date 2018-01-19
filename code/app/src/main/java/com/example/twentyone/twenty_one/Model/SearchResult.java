package com.example.twentyone.twenty_one.Model;

/****
 * @author Annz_2
 */

import java.util.List;

public class SearchResult {
    private String key;
    private List<String> acceptation;
    private List<example> example;
    private String UK_sound;
    private String us_sound;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public List<String> getAcceptation() {
        return acceptation;
    }
    public void setAcceptation(List<String> acceptation) {
        this.acceptation = acceptation;
    }

    public String getUK_sound() {
        return UK_sound;
    }
    public void setUK_sound(String uK_sound) {
        UK_sound = uK_sound;
    }
    public String getUs_sound() {
        return us_sound;
    }
    public void setUs_sound(String us_sound) {
        this.us_sound = us_sound;
    }
    public List<example> getExample() {
        return example;
    }
    public void setExample(List<example> example) {
        this.example = example;
    }


}
