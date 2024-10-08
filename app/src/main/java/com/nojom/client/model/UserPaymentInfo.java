package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPaymentInfo extends GeneralModel {

    @Expose
    @SerializedName("data")
    public Data data;

    public static class Data {
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("phone")
        public String phone;
        @Expose
        @SerializedName("countryName")
        public String countryName;
        @Expose
        @SerializedName("stateName")
        public String stateName;
        @Expose
        @SerializedName("cityName")
        public String cityName;
        @Expose
        @SerializedName("username")
        public String username;
    }
}
