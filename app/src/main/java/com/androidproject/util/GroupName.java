package com.androidproject.util;

import android.graphics.drawable.Drawable;

public class GroupName {

    private String name;
    private Drawable headProtrait;

    public GroupName(String name, Drawable headProtrait){
        this.name = name;
        this.headProtrait=headProtrait;

    }

    public String getName() {
        return name;
    }

    public Drawable getheadProtrait() {
        return headProtrait;
    }
}
