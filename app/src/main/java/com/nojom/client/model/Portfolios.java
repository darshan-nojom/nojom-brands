package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Portfolios extends GeneralModel {

    @Expose
    @SerializedName("portfolio")
    public List<Data> data;
    @Expose
    @SerializedName("path")
    public String path;
    @Expose
    @SerializedName("company_path")
    public String company_path;

    public static class Data implements Serializable {
        public List<Data> data;

        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("profile_id")
        public int profileId;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("last_update")
        public String lastUpdate;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("company_id")
        public int company_id;
        @Expose
        @SerializedName("public_status")
        public int public_status;
        @Expose
        @SerializedName("filename")
        public String filename;
        @Expose
        @SerializedName("company_name")
        public String company_name;
        @Expose
        @SerializedName("company_name_ar")
        public String company_name_ar;
        @Expose
        @SerializedName("company_filename")
        public String company_filename;

        public String getCompany_name(String lang) {
            if (lang.equals("ar")) {
                return company_name_ar;
            }
            return company_name;
        }

    }

    public static class PortfolioFiles implements Serializable {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("path")
        public String path;
        @Expose
        @SerializedName("type")
        public int type;
        @Expose
        @SerializedName("sort")
        public int sort;
        @Expose
        @SerializedName("last_update")
        public String lastUpdate;
        @Expose
        @SerializedName("status")
        public int status;
    }

    public static Portfolios getPortfolios(String responseBody) {
        return new Gson().fromJson(responseBody, Portfolios.class);
    }
}
