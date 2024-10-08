package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveRemoveAgent implements Serializable {
    @Expose
    @SerializedName("agent_profile_id")
    public int agentProfileId;

    public static SaveRemoveAgent getSaveRemoveAgent(String responseBody) {
        return new Gson().fromJson(responseBody, SaveRemoveAgent.class);
    }
}
