package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WalletData implements Serializable {
    /*get wallet balance response*/
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("balance")
    @Expose
    public Double balance;
    @SerializedName("available_balance")
    @Expose
    public Double available_balance;
    @SerializedName("hold_balance")
    @Expose
    public Double hold_balance;

    /*get wallet transaction history response*/
    @SerializedName("amount")
    @Expose
    public Double amount;
    @SerializedName("direction")
    @Expose
    public String direction;
    //    @SerializedName("status")
//    @Expose
//    public String status;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("created_at")
    @Expose
    public String created_at;
    @SerializedName("updated_at")
    @Expose
    public String updated_at;
    @SerializedName("transaction_type")
    @Expose
    public String transaction_type;
    @SerializedName("transaction_type_name")
    @Expose
    public String transaction_type_name;
    @SerializedName("profile_first_name")
    @Expose
    public String profile_first_name;
    @SerializedName("profile_last_name")
    @Expose
    public String profile_last_name;
    @SerializedName("profile_id")
    @Expose
    public Integer profile_id;
    @SerializedName("campaign_title")
    @Expose
    public String campaign_title;
    @SerializedName("campaign_status")
    @Expose
    public String campaign_status;
    @SerializedName("campaign_id")
    @Expose
    public Integer campaign_id;
    @SerializedName("status")
    @Expose
    public String statusStr;

    /*Wallet-charge API response*/
    @SerializedName("embed_url")
    @Expose
    public String embed_url;

    /*rates data response*/

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("rate_type")
    @Expose
    public String rate_type;
    @SerializedName("rate_value")
    @Expose
    public double rate_value;
    @SerializedName("is_active")
    @Expose
    public int is_active;

    /*agent list response*/
    @Expose
    @SerializedName("agents")
    public List<ServiceAgents> agents;

    /*order list response*/
    @Expose
    @SerializedName("campaigns")
    public List<CampList> campaigns;


    @SerializedName("totalRecords")
    @Expose
    public int totalRecords;
    @SerializedName("totalPages")
    @Expose
    public int totalPages;
    @SerializedName("currentPage")
    @Expose
    public String currentPage;
}