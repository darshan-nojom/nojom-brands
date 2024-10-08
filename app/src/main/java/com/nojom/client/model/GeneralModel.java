package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.io.Serializable;

public class GeneralModel implements Serializable {

    @Expose
    @SerializedName("msg")
    public String msg;
    @Expose
    @SerializedName("success")
    public String success;
    @Expose
    @SerializedName("flag")
    public int flag;

    @Expose
    @SerializedName("messageAr")
    public String messageAr;

    public String getMessage(BaseActivity activity) {
        if (activity.getLanguage().equals("ar")) {
            return messageAr == null ? msg : messageAr;
        }
        return msg;
    }
}
