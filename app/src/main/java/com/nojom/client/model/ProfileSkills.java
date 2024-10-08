package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileSkills {

    @Expose
    @SerializedName("data")
    public List<Skill> data;

    public static class Skill {
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
        @SerializedName("rating")
        public int rating;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return name_ar != null ? name_ar : name;
            }
            return name;
        }
    }

    public static ProfileSkills getProfileSkills(String responseBody) {
        return new Gson().fromJson(responseBody, ProfileSkills.class);
    }
}
