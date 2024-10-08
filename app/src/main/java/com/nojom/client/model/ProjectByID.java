package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectByID extends GeneralModel implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("profile_id")
    @Expose
    public Integer profileId;
    @SerializedName("bank_transfer_status")
    @Expose
    public Integer bank_transfer_status;//status = 2 -> Pending// status =3 -> Rejected// status = 1 -> Approved
    @SerializedName("socialPlatformID")
    @Expose
    public int socialPlatformID;
    @SerializedName("socialPlatformName")
    @Expose
    public String socialPlatformName;
    @SerializedName("socialPlatformNameAr")
    @Expose
    public String socialPlatformNameAr;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("job_post_state_id")
    @Expose
    public Integer jobPostStateId;
    @SerializedName("edited_by_admin")
    @Expose
    public String editedByAdmin;
    @SerializedName("offered")
    @Expose
    public String offered;
    @SerializedName("date_completed")
    @Expose
    public String dateCompleted;
    @SerializedName("sys_id")
    @Expose
    public Integer sysId;
    @SerializedName("jp_timestamp")
    @Expose
    public String jpTimestamp;
    @SerializedName("deadline")
    @Expose
    public String deadline;
    @SerializedName("pages")
    @Expose
    public Integer pages;
    @SerializedName("bids_count")
    @Expose
    public Integer bidsCount;
    @SerializedName("jpb_id")
    @Expose
    public Integer jpbId;
    @SerializedName("agent_profile_id")
    @Expose
    public Integer agentProfileId;
    @SerializedName("job_post_id")
    @Expose
    public Integer jobPostId;
    @SerializedName("amount")
    @Expose
    public Double amount;
    @SerializedName("deadline_value")
    @Expose
    public Integer deadlineValue;
    @SerializedName("deadline_type")
    @Expose
    public String deadlineType;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("jpb_timestamp")
    @Expose
    public String jpbTimestamp;
    @SerializedName("jpb_date_accepted")
    @Expose
    public String jpbDateAccepted;
    @SerializedName("jpc_id")
    @Expose
    public Integer jpcId;
    @SerializedName("fixed_price")
    @Expose
    public Double fixedPrice;
    @SerializedName("pay_type_id")
    @Expose
    public Integer payTypeId;
    @SerializedName("s_id")
    @Expose
    public Integer sId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName(value = "name_ar", alternate = {"nameAr"})
    @Expose
    public String nameAr;
    @SerializedName("service_category_id")
    @Expose
    public Integer serviceCategoryId;
    @SerializedName("sc_id")
    @Expose
    public Integer scId;
    @SerializedName("name_app")
    @Expose
    public String nameApp;
    @SerializedName("jpstate_id")
    @Expose
    public Integer jpstateId;
    @SerializedName("jpstate_name")
    @Expose
    public String jpstateName;
    @SerializedName("cr_id")
    @Expose
    public Integer crId;
    @SerializedName("client_rate_id")
    @Expose
    public Integer clientRateId;
    @SerializedName("range_from")
    @Expose
    public String rangeFrom;
    @SerializedName("range_to")
    @Expose
    public String rangeTo;
    @SerializedName("cr_pay_type_id")
    @Expose
    public Integer crPayTypeId;
    @SerializedName("pt_id")
    @Expose
    public Integer ptId;
    @SerializedName("pt_name")
    @Expose
    public String ptName;
    @SerializedName("has_agent")
    @Expose
    public Integer hasAgent;
    @SerializedName("jr_id")
    @Expose
    public Integer jrId;
    @SerializedName("lh_id")
    @Expose
    public Integer lhId;
    @SerializedName("lh_name")
    @Expose
    public String lhName;
    @SerializedName("agent_details")
    @Expose
    public AgentDetails agentDetails;
    @SerializedName("submitted_files")
    @Expose
    public List<FileList> submittedFiles;
    @SerializedName("agent_review")
    @Expose
    public AgentReview agentReview;
    @SerializedName("is_agent_review")
    @Expose
    public Integer isAgentReview;
    @SerializedName("client_review")
    @Expose
    public ClientReview clientReview;
    @SerializedName("attachments")
    @Expose
    public List<Attachments> attachments;
    @SerializedName("job_post_budget")
    @Expose
    public JobPostBudget jobPostBudget;
    @SerializedName("job_pay_type")
    @Expose
    public JobPayType jobPayType;
    @SerializedName("job_offers")
    @Expose
    public List<JobOffer> jobOffers;
    @Expose
    @SerializedName("timer")
    public Timer timer;
    @SerializedName("job_post_contracts")
    @Expose
    public JobPostContract jobPostContracts;
    @SerializedName("submitted_path")
    @Expose
    public String submittedPath;
    @SerializedName("attachment_path")
    @Expose
    public String attachmentPath;
    @SerializedName("is_first_order")
    @Expose
    public Integer isFirstOrder;
    @SerializedName("first_order_coupon")
    @Expose
    public String firstOrderCoupon;
    @SerializedName("job_search_tags")
    @Expose
    public ArrayList<SearchTagModel> jobSearchTags = null;


    public String getName(String lang) {
        if (lang.equals("ar")) {
            return nameAr == null ? name : nameAr;
        }
        return name;
    }

    public String getSocialPlatformName(String lang) {
        if (lang.equals("ar")) {
            return socialPlatformNameAr == null ? socialPlatformName : socialPlatformNameAr;
        }
        return socialPlatformName;
    }

    public static class JobPostBudget implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("job_post_id")
        @Expose
        public Integer jobPostId;
        @SerializedName("budget")
        @Expose
        public Double budget;
        @SerializedName("pay_type_id")
        @Expose
        public Integer payTypeId;
    }


    public static class ClientReview implements Serializable {
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("rate")
        public Float rate;
        @Expose
        @SerializedName("comment")
        public String comment;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
    }

    public static class AgentReview implements Serializable {
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("rate")
        public Float rate;
        @Expose
        @SerializedName("comment")
        public String comment;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
    }

    public static class Files implements Serializable {
        @Expose
        @SerializedName("files")
        public List<FileList> fileList;
        @Expose
        @SerializedName("type")
        public Integer type;
        @Expose
        @SerializedName("timer")
        public Timer timer;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("id")
        public Integer id;
    }


    public static class Timer implements Serializable {
        @Expose
        @SerializedName("isDue")
        public boolean isdue;
        @Expose
        @SerializedName("seconds")
        public long seconds;
        @Expose
        @SerializedName("minutes")
        public long minutes;
        @Expose
        @SerializedName("hours")
        public long hours;
        @Expose
        @SerializedName("days")
        public long days;
    }

    public static class JobPayType implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;

        @Expose
        @SerializedName("nameAr")
        public String nameAr;
        @Expose
        @SerializedName("id")
        public Integer id;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return nameAr == null ? name : nameAr;
            }
            return name;
        }
    }

    public static class JobOffer implements Serializable {

        @SerializedName("profile_id")
        @Expose
        public Integer profileId;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("username")
        @Expose
        public String username;
    }


    public static class AgentDetails implements Serializable {

        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("photo")
        @Expose
        public String photo;
        @SerializedName("address")
        @Expose
        public Address address;
    }

    public static class JobPostContract implements Serializable {

        @SerializedName("deposit_charges")
        @Expose
        public Integer depositCharges = 5;
    }

    public static class Address implements Serializable {

        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("countryNameAr")
        @Expose
        public String countryNameAr;
        @SerializedName("cityName")
        @Expose
        public String cityName;
        @SerializedName("cityNameAr")
        @Expose
        public String cityNameAr;
        @SerializedName("longitude")
        @Expose
        public Float longitude;
        @SerializedName("latitude")
        @Expose
        public Float latitude;

        public String getCountry(String lang) {
            if (lang.equals("ar")) {
                return countryNameAr == null ? countryName : countryNameAr;
            }
            return countryName;
        }

        public String getCity(String lang) {
            if (lang.equals("ar")) {
                return cityNameAr == null ? cityName : cityNameAr;
            }
            return cityName;
        }
    }


    public static ProjectByID getProjectById(String responseBody) {
        return new Gson().fromJson(responseBody, ProjectByID.class);
    }
}
