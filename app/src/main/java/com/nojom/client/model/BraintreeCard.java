package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BraintreeCard extends GeneralModel implements Serializable {

    @Expose
    @SerializedName("data")
    public ArrayList<Data> data;

    public static BraintreeCard getCardList(String responseBody) {
        return new Gson().fromJson(responseBody, BraintreeCard.class);
    }

    public static class Data implements Serializable {
        @Expose
        @SerializedName("last_digit")
        public String lastDigit;
        @Expose
        @SerializedName("card_number")
        public String card_number;
        @Expose
        @SerializedName("card_type")
        public String cardType;
        @Expose
        @SerializedName("token")
        public String token;
        @Expose
        @SerializedName("exp_date")
        public String expDate;
        @Expose
        @SerializedName("default")
        public boolean primary;
        @Expose
        @SerializedName("is_primary")
        public int isPrimary;
        @Expose
        @SerializedName("billingAddress")
        public BillingAddress billingAddress;
        @SerializedName("exp_month")
        @Expose
        public Integer expMonth = 0;
        @SerializedName("exp_year")
        @Expose
        public Integer expYear = 0;
        @Expose
        @SerializedName("paypal")
        public Cardlist.Paypal paypal;
        @SerializedName("card_id")
        @Expose
        public String cardId = "";
        @SerializedName("id")
        @Expose
        public String id = "";

        public boolean localPrimary = false;
    }

    public static class BillingAddress implements Serializable {
        @Expose
        @SerializedName("cardholder_name")
        public String cardholderName;
        @Expose
        @SerializedName("street_address")
        public String streetAddress;
        @Expose
        @SerializedName("extended_address")
        public String extendedAddress;
        @Expose
        @SerializedName("city")
        public String city;
        @Expose
        @SerializedName("state")
        public String state;
        @Expose
        @SerializedName("postal_code")
        public String postalCode;
        @Expose
        @SerializedName("country")
        public String country;
    }
}
