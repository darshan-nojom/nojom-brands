package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceAgents implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("first_name")
    @Expose
    public String first_name;
    @SerializedName("last_name")
    @Expose
    public String last_name;
    @SerializedName("image")
    @Expose
    public ImagePath image;
    @Expose
    @SerializedName("services")
    public List<AgentServices> services;
    @Expose
    @SerializedName("categories")
    public List<AgentCategory> categories;

    public boolean isShowProgress;
}

