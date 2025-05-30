package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.io.Serializable;
import java.util.List;

public class CampList implements Serializable {

    @SerializedName("invoice_id")
    @Expose
    public Integer invoiceId;
    @SerializedName("invoice_no")
    @Expose
    public String invoiceNo;
    @SerializedName("total_price")
    @Expose
    public double totalPrice;

    public double getActualPrice() {

        double agencyFee = totalPrice * agency_fee_rate;
        double taxTotal = (totalPrice + agencyFee) * tax_rate;
        double total = totalPrice + taxTotal + agencyFee;
        return total;
    }

    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("campaign_id")
    @Expose
    public Integer campaignId;
    @SerializedName("tax_rate")
    @Expose
    public double tax_rate;
    @SerializedName("agency_fee_rate")
    @Expose
    public double agency_fee_rate;
    @SerializedName("subtotal")
    @Expose
    public double subtotal;
    @SerializedName("campaign_title")
    @Expose
    public String campaignTitle;
    @SerializedName("campaign_launch_date")
    @Expose
    public String campaignLaunchDate;
    @SerializedName("campaign_attachment_url")
    @Expose
    public String campaignAttachmentUrl;
    @SerializedName("campaign_brief")
    @Expose
    public String campaignBrief;
    @SerializedName("campaign_status")
    @Expose
    public String campaignStatus;
    @SerializedName("campaign_created_at")
    @Expose
    public String campaignCreatedAt;
    @SerializedName("client_profile_id")
    @Expose
    public Integer clientProfileId;
    @SerializedName("client_first_name")
    @Expose
    public String clientFirstName;
    @SerializedName("client_last_name")
    @Expose
    public String clientLastName;
    @SerializedName("profiles")
    @Expose
    public List<Profile> profiles;
    @SerializedName("social_platforms")
    @Expose
    public List<CampSocialPlatform> socialPlatforms;
    @SerializedName("services")
    @Expose
    public List<CampService> services;
    @SerializedName("timeline")
    @Expose
    public List<Timeline> timeline;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    public boolean isShowProgress;


    public String getStatusName(BaseActivity activity) {
        if (activity.getLanguage().equals("ar")) {
            return name_ar != null ? name_ar : name;
        } else {
            return name;
        }
    }

    @SerializedName("jp_id")
    @Expose
    public Integer jp_id;
    @SerializedName("jr_id")
    @Expose
    public Integer jr_id;
    @SerializedName("jr_status")
    @Expose
    public String jr_status;
    @SerializedName("profile_id")
    @Expose
    public Integer profile_id;
    @SerializedName("jp_title")
    @Expose
    public String jp_title;
    @SerializedName("jp_description")
    @Expose
    public String jp_description;
    @SerializedName("client_rate_id")
    @Expose
    public int client_rate_id;
    @SerializedName("offered")
    @Expose
    public String offered;
    @SerializedName("jp_timestamp")
    @Expose
    public String jp_timestamp;
    @SerializedName("bids_count")
    @Expose
    public int bids_count;
    @SerializedName("jps_id")
    @Expose
    public Integer jps_id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("name_ar")
    @Expose
    public String name_ar;
    @SerializedName("cr_id")
    @Expose
    public Integer cr_id;
    @SerializedName("range_from")
    @Expose
    public Double range_from;
    @SerializedName("range_to")
    @Expose
    public Double range_to;
    @SerializedName("job")
    @Expose
    public String job;
    @SerializedName("gigType")
    @Expose
    public String gigType;
    @SerializedName("budget")
    @Expose
    public Double budget;

    public boolean isHeaderType = false;
    public String headerText;
}
