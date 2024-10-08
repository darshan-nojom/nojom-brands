package com.nojom.client.model;

import android.graphics.drawable.Drawable;

public class ProfilePlatform {

    public String name;
    public Drawable icon;

    public ProfilePlatform(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }
}