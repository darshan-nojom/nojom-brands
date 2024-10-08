package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserLevel extends GeneralModel {

    @Expose
    @SerializedName("profile_id")
    public String profileId;
    @Expose
    @SerializedName("total_amount")
    public String totalAmount;
    @Expose
    @SerializedName("total_point")
    public double totalPoint;
    @Expose
    @SerializedName("level")
    public int level;
    @SerializedName("level_data")
    @Expose
    public List<LevelDatum> levelData = null;

    public static GetUserLevel getUserLevel(String responseBody) {
        return new Gson().fromJson(responseBody, GetUserLevel.class);
    }

    public class LevelDatum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("status")
        @Expose
        public Integer status;

    }

}
