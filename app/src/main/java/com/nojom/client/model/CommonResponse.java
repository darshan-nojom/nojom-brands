package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommonResponse extends Wrapper implements Serializable {
    @Expose
    @SerializedName("data")
    public String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}