package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllSocialGigs extends GeneralModel implements Serializable {

    @SerializedName("agents")
    @Expose
    public List<AllData> influencer = null;
    @SerializedName("bestInfluencers")
    @Expose
    public List<AllData> bestInfluencers = null;
    @SerializedName("bestServices")
    @Expose
    public List<AllData> bestServices = null;
    @SerializedName("savedInfluencers")
    @Expose
    public List<AllData> savedInfluencers = null;
    //    @SerializedName("YouTube")
//    @Expose
//    public List<AllData> YouTube = null;
//    @SerializedName("Instagram")
//    @Expose
//    public List<AllData> Instagram = null;
    @SerializedName("gigImagesPath")
    @Expose
    public String gigImagesPath;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("isFirstOrder")
    @Expose
    public Integer isFirstOrder = 0;
    @SerializedName("couponData")
    @Expose
    public ExpertGig.CouponData couponData;

    public static AllSocialGigs getAllSocialGigs(String responseBody) {
        try {
            return new Gson().fromJson(responseBody, AllSocialGigs.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class AllData implements Serializable {
        @SerializedName("gigType")
        @Expose
        public String gigType;
        @SerializedName("gigID")
        @Expose
        public Integer gigID;
        @SerializedName("profile_id")
        @Expose
        public Integer profile_id;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("max_price")
        @Expose
        public Double maxPrice;
        @SerializedName("min_price")
        @Expose
        public Double minPrice;
        @SerializedName("is_profile")
        @Expose
        public Integer is_profile;
        @SerializedName("gigTitle")
        @Expose
        public String gigTitle;
        @SerializedName("generalRank")
        @Expose
        public Integer generalRank;
        @SerializedName("categoryRank")
        @Expose
        public Integer categoryRank;
        @SerializedName("sum_rating")
        @Expose
        public Double sumRating;
        @SerializedName("count_rating")
        @Expose
        public Double countRating;
        @SerializedName("gig_count_rating")
        @Expose
        public Integer gigCountRating;
        @SerializedName("starpoints")
        @Expose
        public Float starpoints;
        @SerializedName("real_count")
        @Expose
        public Integer realCount;
        @SerializedName("socialPlatformID")
        @Expose
        public Integer socialPlatformID;
        @SerializedName("saved")
        @Expose
        public Integer saved = 0;
        @SerializedName("socialPlatformName")
        @Expose
        public String socialPlatformName;
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
        @SerializedName("profileimg")
        @Expose
        public String profileimg;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("cityNameAr")
        @Expose
        public String cityNameAr;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("countryNameAr")
        @Expose
        public String countryNameAr;
        @SerializedName("stateName")
        @Expose
        public String stateName;
        @SerializedName("stateNameAr")
        @Expose
        public String stateNameAr;
        @SerializedName("followersCount")
        @Expose
        public Integer followersCount;
        @SerializedName("platform_icon")
        @Expose
        public String platformIcon;
        @SerializedName("gigImages")
        @Expose
        public List<GigImage> gigImages = null;
        @SerializedName("skills")
        @Expose
        public List<Skills> skills = null;
        @SerializedName("social_platforms")
        @Expose
        public List<SocialPlatform> social_platforms = null;

        public boolean isShowFavProgress;
        public boolean isShowProgress;

        public String getCountry(String lang) {
            if (lang.equals("ar")) {
                return countryNameAr == null ? countryName : countryNameAr;
            }
            return countryName;
        }

        public String getCity(String lang) {
            if (lang.equals("ar")) {
                return cityNameAr == null ? cityName : cityNameAr;
            }
            return cityName;
        }

        public String getState(String lang) {
            if (lang.equals("ar")) {
                return stateNameAr == null ? stateName : stateNameAr;
            }
            return stateName;
        }
    }

    public static class SocialPlatform implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("nameAr")
        public String nameAr;

        @Expose
        @SerializedName("web_url")
        public String web_url;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("followers")
        public int followers;
        @Expose
        @SerializedName("display_order")
        public int display_order;
        @Expose
        @SerializedName("filename")
        public String filename;
        @Expose
        @SerializedName("filename_gray")
        public String filename_gray;
        @Expose
        @SerializedName("social_platform_type_id")
        public int social_platform_type_id;
        @Expose
        @SerializedName("social_platform_id")
        public int social_platform_id;

        public boolean isSelected;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return nameAr != null ? nameAr : name;
            }
            return name;
        }
    }

    public class GigImage implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("imageName")
        @Expose
        public String imageName;
    }

    public class Skills implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("rating")
        @Expose
        public Double rating;

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("name_ar")
        @Expose
        public String name_ar;

        public String getSkillName(String lang) {
            if (lang.equals("ar")) {
                return name_ar == null ? name : name_ar;
            }
            return name;
        }
    }
}
