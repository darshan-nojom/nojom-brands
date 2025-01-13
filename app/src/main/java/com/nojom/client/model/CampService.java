package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampService implements Serializable {

    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("profile")
    @Expose
    public Profile profile;
    @SerializedName("service_id")
    @Expose
    public Integer serviceId;
    @SerializedName("campaign_id")
    @Expose
    public Integer campaignId;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("attachment_url")
    @Expose
    public Object attachmentUrl;
    @SerializedName("social_platform")
    @Expose
    public List<CampSocialPlatform> socialPlatform;

}
