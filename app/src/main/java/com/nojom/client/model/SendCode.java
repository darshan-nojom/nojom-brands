package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

public class SendCode {

    @SerializedName("phone")
    private String phone;


    public SendCode(String title) {
        this.phone = title;
    }

}
