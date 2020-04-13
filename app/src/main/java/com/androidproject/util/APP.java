package com.androidproject.util;

import android.graphics.drawable.Drawable;

public class APP {

    private String name;
    private Drawable imageId;

    public APP(String name, Drawable imageId){
        this.name = name;
        this.imageId = imageId;

    }

    public String getName() {
        return name;
    }

    public Drawable getImageId() {
        return imageId;
    }
}
