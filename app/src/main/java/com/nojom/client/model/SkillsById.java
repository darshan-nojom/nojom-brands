package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkillsById extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("skills")
        public Skills skills;

        public boolean isSelected;
    }

    public static class Skills {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public String id;
    }
}
