package com.androidproject;

import android.graphics.drawable.Drawable;

public class group {

    private String name;
    private float time;
    private Drawable headProtrait;

    public group(String name, float time, Drawable headProtrait){
        this.name = name;
        this.time = time;
        this.headProtrait=headProtrait;

    }

    public String getName() {
        return name;
    }

    public Drawable getheadProtrait() {
        return headProtrait;
    }

    public float getTime() {
        return time;
    }
}
