package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientReviews extends Wrapper {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("comment")
        public String comment;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("rate")
        public float rate;
        @Expose
        @SerializedName("title")
        public String title;
    }

    public static ClientReviews getClientReviews(String responseBody) {
        return new Gson().fromJson(responseBody, ClientReviews.class);
    }
}
