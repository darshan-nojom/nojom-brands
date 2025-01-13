package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampaignPay {

    @SerializedName("amount")
    private double amount;

    @SerializedName("currency")
    private String currency;

    public CampaignPay(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
