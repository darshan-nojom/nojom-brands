package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseModel extends GeneralModel {

    @SerializedName("transactionID")
    @Expose
    public String transactionID = "";
    @SerializedName("contractID")
    @Expose
    public Integer contractID = 0;

    public static PurchaseModel getPurchase(String responseBody) {
        return new Gson().fromJson(responseBody, PurchaseModel.class);
    }
}
