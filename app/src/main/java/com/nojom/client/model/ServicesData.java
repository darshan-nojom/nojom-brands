package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesData extends Wrapper {
    @Expose
    @SerializedName("data")
    public InfServices data;
    @Expose
    @SerializedName("jwt")
    public String jwt;

    public static InfServices getServiceData(String responseBody) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(responseBody,
                    InfServices.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

