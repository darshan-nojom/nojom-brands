package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CampAttachResponse extends Wrapper implements Serializable {
    @Expose
    @SerializedName("data")
    public CampAttachUrl data;

    public CampAttachUrl getData() {
        return data;
    }

    public void setData(CampAttachUrl data) {
        this.data = data;
    }

}