package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletResponse extends Wrapper implements Serializable {
    @Expose
    @SerializedName("data")
    public WalletData data;

    public static WalletResponse getWalletData(String responseBody) {
        return new Gson().fromJson(responseBody, WalletResponse.class);
    }
}