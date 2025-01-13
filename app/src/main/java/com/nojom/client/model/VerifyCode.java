package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

public class VerifyCode {

    @SerializedName("phone")
    private String phone;
    @SerializedName("otp")
    private String otp;


    public VerifyCode(String title, String otp) {
        this.phone = title;
        this.otp = otp;
    }

}
