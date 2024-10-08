package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientRate extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("pay_type_id")
        public int payTypeId;
        @Expose
        @SerializedName("range_to")
        public String rangeTo;
        @Expose
        @SerializedName("range_from")
        public String rangeFrom;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static ClientRate getClientRates(String responseBody) {
        return new Gson().fromJson(responseBody, ClientRate.class);
    }
}
