package com.androidproject.util;

import android.graphics.drawable.Drawable;

public class APP {

    private String name;
    private Drawable image;
    private String address;
    private String user;

    public APP(String name, Drawable imageId, String address, String user) {
        this.name = name;
        this.image = imageId;
        this.address = address;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public Drawable getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getUser() {
        return user;
    }

    public void setName(String str) {
        this.name=str;
    }

    public void setImage(Drawable draw) {
        this.image=draw;
    }

    public void setAddress(String str) {
        this.address=str;
    }

    public void setUser(String str) {
        this.user=str;
    }
}
