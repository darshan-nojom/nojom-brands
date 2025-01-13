package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InfServices implements Serializable {
    @Expose
    @SerializedName("services")
    public ArrayList<Serv> services;
    @Expose
    @SerializedName("all_platforms_price")
    public Double all_platforms_price;

}
