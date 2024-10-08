package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Cardlist extends GeneralModel implements Serializable {

    @Expose
    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data implements Serializable {
        @Expose
        @SerializedName("countryName")
        public String countryName;
        @Expose
        @SerializedName("id")
        public String id;
        @Expose
        @SerializedName("brand")
        public String brand;
        @Expose
        @SerializedName("customer_id")
        public String customerId;
        @Expose
        @SerializedName("address_city")
        public String addressCity;
        @Expose
        @SerializedName("address_country")
        public String addressCountry;
        @Expose
        @SerializedName("address_line1")
        public String addressLine1;
        @Expose
        @SerializedName("address_line1_check")
        public String addressLine1Check;
        @Expose
        @SerializedName("address_line2")
        public String addressLine2;
        @Expose
        @SerializedName("address_state")
        public String addressState;
        @Expose
        @SerializedName("address_zip")
        public String addressZip;
        @Expose
        @SerializedName("last4")
        public String last4;
        @Expose
        @SerializedName("exp_month")
        public int expMonth;
        @Expose
        @SerializedName("exp_year")
        public int expYear;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("is_primary")
        public int isPrimary;

        @Expose
        @SerializedName("paypal")
        public Paypal paypal;

        public boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    public class PaymentAccount implements Serializable {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("is_primary")
        public String isPrimary;
        @Expose
        @SerializedName("verified")
        public String verified;
        @Expose
        @SerializedName("payment_type_id")
        public int paymentTypeId;
        @Expose
        @SerializedName("account")
        public String account;
        @Expose
        @SerializedName("profile_id")
        public int profileId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public class Paypal implements Serializable {
        @Expose
        @SerializedName("id")
        public Integer id;
        @Expose
        @SerializedName("profile_id")
        public int profileId;
        @Expose
        @SerializedName("payer_id")
        public String payer_id;
        @Expose
        @SerializedName("account")
        public String account;
        @Expose
        @SerializedName("payment_type_id")
        public int paymentTypeId;
        @Expose
        @SerializedName("verified")
        public String verified;
        @Expose
        @SerializedName("is_primary")
        public String isPrimary="0";
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("provider")
        public String provider;
        @Expose
        @SerializedName("token")
        public String token;
    }

}
