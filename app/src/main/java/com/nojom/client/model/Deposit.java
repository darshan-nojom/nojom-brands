package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Deposit extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static Deposit getDeposits(String responseBody) {
        return new Gson().fromJson(responseBody, Deposit.class);
    }

    public static class Data {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("amount")
        public double amount;
        @Expose
        @SerializedName("job_post_id")
        public int jobPostId;
        @Expose
        @SerializedName("profile_id")
        public int profileId;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("deposit_charges")
        public Double depositCharges;
        @Expose
        @SerializedName("type")
        public String type;
        @Expose
        @SerializedName("date_paid")
        public String datePaid;
        @Expose
        @SerializedName("job_post")
        public JobPost jobPost;
        @Expose
        @SerializedName("deposit_status")
        public String depositStatus;
        @Expose
        @SerializedName("job")
        public String job;
        @Expose
        @SerializedName("income_status")
        public int incomeStatus;
        @Expose
        @SerializedName("redeem_amount")
        public double redeemAmount;

    }

    public static class JobPost {
        @Expose
        @SerializedName("job_post_state_id")
        public int jobPostStateId;
    }
}
