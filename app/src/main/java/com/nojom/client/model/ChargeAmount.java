package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

public class ChargeAmount {

    @SerializedName("amount")
    private Double amount;

    public ChargeAmount(Double amount) {
        this.amount = amount;
    }

}
