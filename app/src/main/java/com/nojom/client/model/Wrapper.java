package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.io.Serializable;

public class Wrapper implements Serializable {

    @Expose
    @SerializedName("status")
    public boolean status;
    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("messageAr")
    public String messageAr;

    public String getMessage(BaseActivity activity) {
        if (activity.getLanguage().equals("ar")) {
            return messageAr == null ? message : messageAr;
        }
        return message;
    }
}
