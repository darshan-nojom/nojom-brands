package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServicesSellersModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static ServicesSellersModel getServiceData(String responseBody) {
        return new Gson().fromJson(responseBody, ServicesSellersModel.class);
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("services")
        public List<Services> services;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("service_category_id")
        public int serviceCategoryId;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("name_app")
        public String name_app;
        @Expose
        @SerializedName("appMenuName")
        public String appMenuName;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("isMainMenu")
        public int isMainMenu;
        @Expose
        @SerializedName("isTop")
        public int isTop;
        @Expose
        @SerializedName("colourHex")
        public String colourHex;
        @Expose
        @SerializedName("isJobPostFree")
        public String isJobPostFree;
        @SerializedName("isNew")
        @Expose
        public String isNew = "0";

        public String experience;

        public int experienceId;

        public boolean isSelected;
    }

    public static class Services {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("service_category_id")
        public String serviceCategoryId;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("isTop")
        public int isTop;

    }
}
