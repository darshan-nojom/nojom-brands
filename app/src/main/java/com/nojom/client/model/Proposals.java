package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Proposals extends GeneralModel implements Serializable {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static Proposals getBidList(String responseBody) {
        return new Gson().fromJson(responseBody, Proposals.class);
    }

    public static class Data implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("job_post_id")
        @Expose
        public Integer jobPostId;
        @SerializedName("profile_id")
        @Expose
        public Integer profileId;
        @SerializedName("img")
        @Expose
        public String img;
        @SerializedName("total_reviews")
        @Expose
        public Integer totalReviews;
        @SerializedName("ave_rate")
        @Expose
        public Float aveRate;
        @SerializedName("amount")
        @Expose
        public Double amount = 0.0;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("client_rate_id")
        @Expose
        public Integer clientRateId;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("stateName")
        @Expose
        public String stateName;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("deadline_type")
        @Expose
        public String deadlineType;
        @SerializedName("deadline_type_ar")
        @Expose
        public String deadline_type_ar;
        @SerializedName("deadline_value")
        @Expose
        public Integer deadlineValue;
        @SerializedName("jpb_id")
        @Expose
        public Integer jpbId;
        @SerializedName("job_pay_type")
        @Expose
        public PayTypes jobPayType;
        @SerializedName("bid_charges")
        @Expose
        public Integer bidCharges;
        @SerializedName("bid_percent_charges")
        @Expose
        public Integer bidPercentCharges;
        @SerializedName("deposit_charges")
        @Expose
        public Integer depositCharges = 5;
        @SerializedName("bid_dollar_charges")
        @Expose
        public Integer bidDollarCharges;

        public boolean isShowProfileProgress;

        public String getDeadlineType(String lang) {
            if (lang.equals("ar")) {
                return deadline_type_ar != null ? deadline_type_ar : deadlineType;
            }
            return deadlineType;
        }
    }

    public static class PayTypes implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
