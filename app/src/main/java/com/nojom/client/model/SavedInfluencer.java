package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedInfluencer extends GeneralModel {

    @Expose
    @SerializedName("saved")
    public int saved;

    public static SavedInfluencer getData(String responseBody) {
        return new Gson().fromJson(responseBody, SavedInfluencer.class);
    }
}
