package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InfluencerList extends GeneralModel implements Serializable {

    @SerializedName("user_count")
    @Expose
    public Integer user_count;
    @SerializedName("agents")
    @Expose
    public List<AllData> influencer = null;
    @SerializedName("path")
    @Expose
    public String path;

    public static InfluencerList getAllInfluencers(String responseBody) {
        try {
            return new Gson().fromJson(responseBody, InfluencerList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class AllData implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("first_name")
        @Expose
        public String first_name;
        @SerializedName("last_name")
        @Expose
        public String last_name;
        @SerializedName("gigId")
        @Expose
        public Integer gigId;
        @SerializedName("socialPlatformID")
        @Expose
        public Integer socialPlatformID;
        @SerializedName("followersCount")
        @Expose
        public Integer followersCount;

        @SerializedName("max_price")
        @Expose
        public Double maxPrice;
        @SerializedName("min_price")
        @Expose
        public Double minPrice;
        @SerializedName("platform_icon")
        @Expose
        public String platform_icon;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("cityNameAr")
        @Expose
        public String cityNameAr;
        @SerializedName("stateName")
        @Expose
        public String stateName;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("countryNameAr")
        @Expose
        public String countryNameAr;
        @SerializedName("skills")
        @Expose
        public List<AllSocialGigs.Skills> skills = null;
        @SerializedName("social_platforms")
        @Expose
        public List<AllSocialGigs.SocialPlatform> social_platforms = null;

        public boolean isShowProfileProgress;

        public String getCityName(String lang) {
            if (lang.equals("ar")) {
                return cityNameAr != null ? cityNameAr : cityName;
            }
            return cityName;
        }

        public String getCountryName(String lang) {
            if (lang.equals("ar")) {
                return countryNameAr != null ? countryNameAr : countryName;
            }
            return countryName;
        }

        @SerializedName("generalRank")
        @Expose
        public Integer generalRank;
        @SerializedName("categoryRank")
        @Expose
        public Integer categoryRank = 0;
        @SerializedName("count_rating")
        @Expose
        public Float count_rating;
        @SerializedName("starpoints")
        @Expose
        public Float starpoints;
        @SerializedName("is_profile")
        @Expose
        public Integer is_profile;
        @SerializedName("saved")
        @Expose
        public Integer saved;
        @SerializedName("img")
        @Expose
        public String img;
        @SerializedName("profileimg")
        @Expose
        public String profileimg;

        public boolean isShowFavProgress;
        public boolean isShowProgress;
    }
//
//    public class GigImage implements Serializable {
//        @SerializedName("id")
//        @Expose
//        public Integer id;
//        @SerializedName("imageName")
//        @Expose
//        public String imageName;
//    }
}
