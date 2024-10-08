package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notification extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("name_ar")
        public String name_ar;

        public String getName(String lan) {
            if (lan.equals("ar")) {
                return name_ar == null ? name : name_ar;
            }
            return name;
        }

        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("status")
        public String status;
    }

    public static Notification getNotifications(String responseBody) {
        return new Gson().fromJson(responseBody, Notification.class);
    }
}
