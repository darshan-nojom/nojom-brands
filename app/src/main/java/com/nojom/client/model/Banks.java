package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Banks extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static Banks getBanks(String responseBody) {
        return new Gson().fromJson(responseBody, Banks.class);
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("nameAr")
        public String nameAr;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return nameAr != null ? nameAr : name;
            } else {
                return name;
            }
        }
    }

    /*public static class Services {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("service_category_id")
        public String serviceCategoryId;
        @Expose
        @SerializedName("name")
        public String name;
    }*/
}
