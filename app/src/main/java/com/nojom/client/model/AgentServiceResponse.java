package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgentServiceResponse extends Wrapper implements Serializable {
    @Expose
    @SerializedName("data")
    public CampListData data;

    public static AgentServiceResponse getCampaignDataList(String responseBody) {
        return new Gson().fromJson(responseBody, AgentServiceResponse.class);
    }
}