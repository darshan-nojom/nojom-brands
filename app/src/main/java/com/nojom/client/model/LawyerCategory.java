package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LawyerCategory extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("name")
        public String name;

        public boolean isSelected;

        public Data(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static LawyerCategory getLawyerCategory(String responseBody) {
        return new Gson().fromJson(responseBody, LawyerCategory.class);
    }
}
