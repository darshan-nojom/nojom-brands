package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExpertDetail extends GeneralModel implements Serializable {
    @Expose
    @SerializedName("service_id")
    public int serviceId;
    @Expose
    @SerializedName("service_name")
    public String serviceName;
    @Expose
    @SerializedName("service_name_app")
    public String serviceNameApp;
    @Expose
    @SerializedName("img")
    public String img;
    @Expose
    @SerializedName("email_verified")
    public int emailVerified;
    @Expose
    @SerializedName("last_name")
    public String lastName;
    @Expose
    @SerializedName("first_name")
    public String firstName;
    @Expose
    @SerializedName("username")
    public String username;
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("rate")
    public double rate;
    @Expose
    @SerializedName("sum")
    public double sum;
    @Expose
    @SerializedName("count")
    public int count;
    @Expose
    @SerializedName("profile_id")
    public int profileId;

    public static ExpertDetail getExpertInfo(String responseBody) {
        return new Gson().fromJson(responseBody, ExpertDetail.class);
    }
}
