package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Language extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName(value = "name_ar", alternate = {"nameAr"})
        public String name_ar;
        @Expose
        @SerializedName("id")
        public int id;

        public String getServNameByLang(String lang) {
            if (lang.equals("ar")) {
                return name_ar == null ? name : name_ar;
            } else {
                return name;
            }
        }

        public String level;

        public int levelId;

        public boolean isSelected;

        public Data(String name, String languageCode) {
            this.name = name;
            this.level = languageCode;
        }
    }

    public static Language getLanguages(String responseBody) {
        return new Gson().fromJson(responseBody, Language.class);
    }
}
