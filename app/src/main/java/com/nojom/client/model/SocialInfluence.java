package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SocialInfluence extends GeneralModel implements Serializable {

    @SerializedName("agents")
    @Expose
    public List<Data> data = null;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("isFirstOrder")
    @Expose
    public Integer isFirstOrder = 0;
    @SerializedName("couponData")
    @Expose
    public CouponData couponData;

    public static SocialInfluence getSocialInfluence(String responseBody) {
        try {
            return new Gson().fromJson(responseBody, SocialInfluence.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Data implements Serializable {
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
        @SerializedName("img")
        @Expose
        public String img;
        @SerializedName("gigId")
        @Expose
        public Integer gigId;
        @SerializedName("socialPlatformID")
        @Expose
        public Integer socialPlatformID;
        @SerializedName("followersCount")
        @Expose
        public Integer followersCount;
        @SerializedName("minPrice")
        @Expose
        public Integer minPrice;
        @SerializedName("platform_icon")
        @Expose
        public String platformIcon;
        @SerializedName("socialPlatformName")
        @Expose
        public String socialPlatformName;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("stateName")
        @Expose
        public String stateName;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("cityNameAr")
        @Expose
        public String cityNameAr;
        @SerializedName("stateNameAr")
        @Expose
        public String stateNameAr;
        @SerializedName("countryNameAr")
        @Expose
        public String countryNameAr;
        @SerializedName("generalRank")
        @Expose
        public Integer generalRank;
        @SerializedName("categoryRank")
        @Expose
        public Integer categoryRank;
        @SerializedName("profile_status")
        @Expose
        public String profileStatus;
        @SerializedName("saved")
        @Expose
        public Integer saved;
        @SerializedName("count_rating")
        @Expose
        public Integer countRating;
        @SerializedName("starpoints")
        @Expose
        public Float starpoints;
        @SerializedName("is_profile")
        @Expose
        public Integer is_profile;//1 = BestInfluencers, 0 = BestServices
        @SerializedName("profile_id")
        @Expose
        public Integer profile_id;

        public boolean isShowFavProgress;
        public boolean isShowProgress;

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
    }

    public class CouponData implements Serializable {
        @SerializedName("coupon_code")
        @Expose
        public String couponCode;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("discount")
        @Expose
        public Integer discount;
    }

}
