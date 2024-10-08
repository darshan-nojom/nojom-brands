package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAgentPartners extends GeneralModel {

    @Expose
    @SerializedName("data")
    public ArrayList<Data> data;
    @Expose
    @SerializedName("path")
    public String path;
    @Expose
    @SerializedName("count")
    public int count;

    public static class Data implements Serializable {
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("link")
        public String link;
        @Expose
        @SerializedName("code")
        public String code;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("name_ar")
        public String name_ar;
        @Expose
        @SerializedName("company_link")
        public String company_link;
        @Expose
        @SerializedName("filename")
        public String filename;
        @Expose
        @SerializedName("profile_id")
        public int profile_id;
        @Expose
        @SerializedName("company_id")
        public int company_id;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("display_order")
        public int display_order;
        @Expose
        @SerializedName("public_status")
        public int public_status;


        public boolean isSelected;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return name_ar != null ? name_ar : name;
            }
            return name;
        }
    }


    public static GetAgentPartners getStores(String responseBody) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(responseBody,
                    GetAgentPartners.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
