package com.androidproject.util;

import android.graphics.drawable.Drawable;

import org.litepal.crud.LitePalSupport;

public class APP  extends LitePalSupport {

    private String name;
    private byte[] image;
    private String address;
    private String user;

    public APP(String name, byte[] image, String address, String user) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
