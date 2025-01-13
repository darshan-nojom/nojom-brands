package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampListData extends Wrapper implements Serializable {
    @SerializedName("currentPage")
    @Expose
    public String currentPage;
    @SerializedName("pageSize")
    @Expose
    public Integer pageSize;
    @SerializedName("totalRecords")
    @Expose
    public Integer totalRecords;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("totalPages")
    @Expose
    public Integer totalPages;
    @SerializedName("campaigns")
    @Expose
    public List<CampList> campaigns;

    @SerializedName("invoices")
    @Expose
    public List<Invoices> invoices;

    @SerializedName("agents")
    @Expose
    public List<Agents> agents;

    /*by id response data*/
    @SerializedName("campaign_id")
    @Expose
    public Integer campaign_id;
    @SerializedName("campaign_title")
    @Expose
    public String campaign_title;
    @SerializedName("campaign_launch_date")
    @Expose
    public String campaign_launch_date;
    @SerializedName("campaign_brief")
    @Expose
    public String campaign_brief;
    @SerializedName("campaign_attachment_url")
    @Expose
    public String campaign_attachment_url;
}