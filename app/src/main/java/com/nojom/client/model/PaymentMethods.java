package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentMethods extends GeneralModel {
    @SerializedName("client_balance")
    @Expose
    public Double clientBalance;
    @SerializedName("payment_method")
    @Expose
    public List<PaymentMethod> paymentMethod = null;

    public class PaymentMethod implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("payment_method_id")
        @Expose
        public Integer paymentMethodId;
        @SerializedName("active")
        @Expose
        public String active;
    }

    public static PaymentMethods gePaymentMethodInfo(String responseBody) {
        return new Gson().fromJson(responseBody, com.nojom.client.model.PaymentMethods.class);
    }
}
