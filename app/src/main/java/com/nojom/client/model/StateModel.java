package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("stateName")
        @Expose
        public String stateName;
        @SerializedName("stateNameAr")
        @Expose
        public String stateNameAr;
        @SerializedName("stateCode")
        @Expose
        public String stateCode;

        public boolean isSelected;

        public String getStateName(String lang){
            if (lang.equals("ar")) {
                return stateNameAr == null ? stateName : stateNameAr;
            } else {
                return stateName;
            }
        }

    }

    public static StateModel getStateList(String responseBody) {
        return new Gson().fromJson(responseBody, StateModel.class);
    }
}
