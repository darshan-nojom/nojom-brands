package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCode extends GeneralModel {


    @Expose
    @SerializedName("is_valid")
    public boolean isValid;
    @Expose
    @SerializedName("discount_amount")
    public double discountAmount;
    @Expose
    @SerializedName("promo_code")
    public String promoCode;


    public static PromoCode getPromoCode(String responseBody) {
        return new Gson().fromJson(responseBody, PromoCode.class);
    }
}
