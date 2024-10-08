package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientBalance extends GeneralModel {

    @Expose
    @SerializedName("available_balance")
    public Double availableBalance;
    @Expose
    @SerializedName("pending_balance")
    public Double pending_balance;

    public static ClientBalance getClientBalance(String responseBody) {
        return new Gson().fromJson(responseBody, ClientBalance.class);
    }
}
