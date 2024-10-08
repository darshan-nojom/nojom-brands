package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PaymentBraintreeCards extends GeneralModel implements Serializable {


    @Expose
    @SerializedName("data")
    public CardPaypal cardPaypal;

    public class CardPaypal implements Serializable {
        @Expose
        @SerializedName("cards")
        public ArrayList<BraintreeCard.Data> cards;
        @Expose
        @SerializedName("paypal")
        public ArrayList<Cardlist.Paypal> paypal;
        @Expose
        @SerializedName("is_primary")
        public int isPrimary;
    }

    public static PaymentBraintreeCards getPaymentCards(String responseBody) {
        return new Gson().fromJson(responseBody, PaymentBraintreeCards.class);
    }

}
