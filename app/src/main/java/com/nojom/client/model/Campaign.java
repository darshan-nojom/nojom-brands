package com.nojom.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Campaign {

    @SerializedName("title")
    private String title;

    @SerializedName("launch_date")
    private String launchDate;

    @SerializedName("brief")
    private String brief;

    @SerializedName("attachment_url")
    private String attachmentUrl;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("stars")
    private List<Stars> stars;

    public Campaign(String title, String launchDate, String brief, String attachmentUrl, List<Stars> services, boolean isWallet) {
        this.title = title;
        this.launchDate = launchDate;
        this.brief = brief;
        this.attachmentUrl = attachmentUrl;
        this.stars = services;
        this.payment_method = isWallet ? "wallet" : "credit_card";
    }

    public static class Service {
        @SerializedName("id")
        private int id;

        public Service(int id) {
            this.id = id;
        }
    }

    public static class Stars {
        @SerializedName("id")
        private int id;
        @SerializedName("services")
        private List<Service> services;
        @SerializedName("note")
        private String note;

        public Stars(int id, List<Service> services, String note) {
            this.id = id;
            this.services = services;
            this.note = note;
        }
    }
}
