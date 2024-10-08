package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Expert extends GeneralModel {

    @Expose
    @SerializedName("agents")
    public List<Data> data;
    @Expose
    @SerializedName("path")
    public String path;

    public static class Data {
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
        public String rate;
        @Expose
        @SerializedName("count")
        public String count;
        @Expose
        @SerializedName("profile_id")
        public int profileId;

        public boolean isShowProgress;
    }

    public static Expert getExperts(String responseBody) {
        return new Gson().fromJson(responseBody, Expert.class);
    }
}
