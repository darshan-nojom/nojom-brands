package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveRemoveGig implements Serializable {
    @SerializedName("gigID")
    @Expose
    public String gigID;

    public static SaveRemoveGig getSaveRemoveGig(String responseBody) {
        return new Gson().fromJson(responseBody, SaveRemoveGig.class);
    }
}
