package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgentServices implements Serializable {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public double price;
    @SerializedName("name_ar")
    @Expose
    public String name_ar;
    @SerializedName("filename")
    @Expose
    public String filename;
    @SerializedName("followers")
    @Expose
    public Integer followers;
    @SerializedName("service_id")
    @Expose
    public Integer service_id;
    @SerializedName("social_platform_id")
    @Expose
    public int social_platform_id;
}
