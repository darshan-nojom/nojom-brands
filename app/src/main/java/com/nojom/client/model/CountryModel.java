package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("countryNameAr")
        @Expose
        public String countryNameAr;
        @SerializedName("countryCode")
        @Expose
        public String countryCode;

        public boolean isSelected;

        public String getCountryName(String lang){
            if (lang.equals("ar")) {
                return countryNameAr == null ? countryName : countryNameAr;
            } else {
                return countryName;
            }
        }

    }

    public static CountryModel getCountryList(String responseBody) {
        return new Gson().fromJson(responseBody, CountryModel.class);
    }

}
