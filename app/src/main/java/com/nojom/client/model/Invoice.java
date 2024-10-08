package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invoice extends GeneralModel {

    @Expose
    @SerializedName("data")
    public String data;
}
