package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpertLawyers extends GeneralModel implements Serializable {

    @Expose
    @SerializedName("agents")
    public List<Data> data;
    @Expose
    @SerializedName("path")
    public String path;

    public static ExpertLawyers getTop3Agents(String responseBody) {
        try {
            return new Gson().fromJson(responseBody, ExpertLawyers.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("first_name")
        public String first_name;
        @Expose
        @SerializedName("last_name")
        public String last_name;
        @Expose
        @SerializedName("proUsername")
        public String proUsername;
        @Expose
        @SerializedName("pay_rate")
        public Float payRate;
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("img")
        public String img;
        @Expose
        @SerializedName("summary")
        public String summary;
        @Expose
        @SerializedName("stateName")
        public String stateName;
        @Expose
        @SerializedName("cityName")
        public String cityName;
        @Expose
        @SerializedName("countryName")
        public String countryName;
        @Expose
        @SerializedName("sum_rating")
        public Double sumRating;
        @Expose
        @SerializedName("count_rating")
        public Float countRating;
        @Expose
        @SerializedName("saved")
        public int saved;
        @Expose
        @SerializedName("skills")
        public List<Skills> skills;
        @Expose
        @SerializedName("verif_completed")
        public int verifCompleted;
        @Expose
        @SerializedName("starpoints")
        public Float rate;
        public boolean isShowFavProgress;
        public boolean isShowProfileProgress;
        public Data(int id, String username) {
            this.id = id;
            this.username = username;
        }
    }

    public static class Skills implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("rating")
        public int rating;

        public Skills(String name) {
            this.name = name;
        }
    }
}
