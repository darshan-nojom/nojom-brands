package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Requirement implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
}