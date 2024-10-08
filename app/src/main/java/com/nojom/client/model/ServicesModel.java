package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

public class ServicesModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static ServicesModel getServiceData(String responseBody) {
        return new Gson().fromJson(responseBody, ServicesModel.class);
    }

    public static List<ServicesModel.Data> getServiceDataCat(String jsonData) {
        return new Gson().fromJson(jsonData, new TypeToken<List<ServicesModel.Data>>() {
        }.getType());
    }

    public static class JobTitle implements Serializable {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("titleAr")
        public String titleAr;

        public String getTitle(String lang) {
            if (lang.equals("ar")) {
                return titleAr != null ? titleAr : title;
            }
            return title;
        }

        public JobTitle(String tit) {
            title = tit;
        }
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("services")
        public List<ServicesModel.Data> services;
        @Expose
        @SerializedName("suggested_job_titles")
        public List<JobTitle> suggestedJobTitles;
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
        @SerializedName("arabic_name")
        public String arabic_name;
        @Expose
        @SerializedName("name_ar")
        public String name_ar;
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

        public Data(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getNameByLang(String lang) {
            if (lang.equals("ar")) {
                return arabic_name;
            } else {
                return name;
            }
        }

        public String getServNameByLang(String lang) {
            if (lang.equals("ar")) {
                return name_ar;
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
