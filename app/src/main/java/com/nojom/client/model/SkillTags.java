package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

public class SkillTags extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data implements Serializable {
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
//
//    public static SkillTags getSkillTags(String responseBody) {
//        return new Gson().fromJson(responseBody, SkillTags.class);
//    }

    public static List<SkillTags.Data> getSkillTags(String jsonData) {
        return new Gson().fromJson(jsonData, new TypeToken<List<SkillTags.Data>>() {
        }.getType());
    }
}
