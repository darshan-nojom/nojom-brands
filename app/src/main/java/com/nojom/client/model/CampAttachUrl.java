package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampAttachUrl {

    @Expose
    @SerializedName("url")
    public String url;

    @Expose
    @SerializedName("campaign_id")
    public Integer campaign_id;

    @Expose
    @SerializedName("intent_id")
    public String intent_id;

    @Expose
    @SerializedName("embed_url")
    public String embed_url;
}
