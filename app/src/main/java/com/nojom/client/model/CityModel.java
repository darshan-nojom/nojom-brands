package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("cityNameAr")
        @Expose
        public String cityNameAr;

        public boolean isSelected;

        public String getCityName(String lang) {
            if (lang.equals("ar")) {
                return cityNameAr == null ? cityName : cityNameAr;
            } else {
                return cityName;
            }
        }

    }

    public static CityModel getCityList(String responseBody) {
        return new Gson().fromJson(responseBody, CityModel.class);
    }
}
