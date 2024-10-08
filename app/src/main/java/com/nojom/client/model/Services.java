package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Services extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;

        public boolean isSelected;
    }

    public static Services getServiceCatData(String responseBody) {
        return new Gson().fromJson(responseBody, Services.class);
    }
}
