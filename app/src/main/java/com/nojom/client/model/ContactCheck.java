package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactCheck {

    @SerializedName("email")
    private String email;
    @SerializedName("contact")
    private String contact;


    public ContactCheck(String email, String phone) {
        this.email = email;
        this.contact = phone;
    }

}
