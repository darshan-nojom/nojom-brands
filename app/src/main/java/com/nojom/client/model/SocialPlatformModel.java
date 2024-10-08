package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SocialPlatformModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static SocialPlatformModel getSocialPlatform(String responseBody) {
        return new Gson().fromJson(responseBody, SocialPlatformModel.class);
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("name_ar")
        public String name_ar;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("platform_icon")
        public String platform_icon;
        @Expose
        @SerializedName("isDisplay")
        public String isDisplay;
        @Expose
        @SerializedName("colorCode")
        public String colorCode;

        public boolean isSelected;

        public Data(String name, int id) {
            this.name = name;
            this.name_ar = name;
            this.id = id;
        }

        public String getServNameByLang(String lang) {
            if (lang.equals("ar")) {
                return name_ar == null ? name : name_ar;
            } else {
                return name;
            }
        }
    }
}
