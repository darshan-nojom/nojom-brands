package com.nojom.client.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username,campaign_id;
    public int id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, int id, String campId) {
        this.username = username;
        this.id = id;
        this.campaign_id = campId;
    }

}