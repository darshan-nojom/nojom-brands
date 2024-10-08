package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AgencyList extends Wrapper {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("profile_id")
        @Expose
        public Integer profile_id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("website")
        @Expose
        public String website;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("note")
        @Expose
        public String note;

        @SerializedName("name_public")
        @Expose
        public Integer name_public;
        @SerializedName("email_public")
        @Expose
        public Integer email_public;
        @SerializedName("phone_public")
        @Expose
        public Integer phone_public;
        @SerializedName("website_public")
        @Expose
        public Integer website_public;
        @SerializedName("about_public")
        @Expose
        public Integer about_public;
        @SerializedName("address_public")
        @Expose
        public Integer address_public;
        @SerializedName("note_public")
        @Expose
        public Integer note_public;
    }

    public static AgencyList getAgencyList(String responseBody) {
        return new Gson().fromJson(responseBody, AgencyList.class);
    }
}
