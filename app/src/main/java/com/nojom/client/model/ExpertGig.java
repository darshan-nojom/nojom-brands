package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpertGig extends GeneralModel implements Serializable {

    @SerializedName("gigData")
    @Expose
    public List<Data> data = null;
    @SerializedName("gigImagesPath")
    @Expose
    public String gigImagesPath;
    @SerializedName("isFirstOrder")
    @Expose
    public Integer isFirstOrder = 0;
    @SerializedName("couponData")
    @Expose
    public CouponData couponData;


    public static ExpertGig getTop3Gig(String responseBody) {
        try {
            return new Gson().fromJson(responseBody, ExpertGig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Data implements Serializable {
        @SerializedName("gigID")
        @Expose
        public Integer gigID;
        @SerializedName("gigTitle")
        @Expose
        public String gigTitle;
        @Expose
        @SerializedName("rate")
        public Float rate;
        @Expose
        @SerializedName("saved")
        public int saved;
        @Expose
        @SerializedName("count_rating")
        public Float countRating;
        @SerializedName("sum_rating")
        @Expose
        public Double sumRating;
        @SerializedName("starpoints")
        @Expose
        public Float starpoints;
        @SerializedName("real_count")
        @Expose
        public Integer realCount;
        @SerializedName("gigImages")
        @Expose
        public List<GigImage> gigImages = null;
        @SerializedName("gigType")
        @Expose
        public String gigType;
        @SerializedName("minPrice")
        @Expose
        public Double minPrice = 0.0;

        public boolean isShowFavProgress;
        public boolean isShowProgress;
    }

    public static class GigImage implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("imageName")
        @Expose
        public String imageName;
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
