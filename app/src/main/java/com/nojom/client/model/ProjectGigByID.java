package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProjectGigByID extends GeneralModel implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("gigID")
    @Expose
    public Integer gigID;
    @SerializedName("gigPackageID")
    @Expose
    public Integer gigPackageID;
    @SerializedName("agentProfileID")
    @Expose
    public Integer agentProfileID;
    @SerializedName("gigStateID")
    @Expose
    public Integer gigStateID;
    @SerializedName("bank_transfer_status")
    @Expose
    public Integer bank_transfer_status;//status = 2 -> Pending// status =3 -> Rejected// status = 1 -> Approved
    @SerializedName("mainCategoryID")
    @Expose
    public Integer mainCategoryID;
    @SerializedName("packagePrice")
    @Expose
    public Double packagePrice;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;
    @SerializedName("totalPrice")
    @Expose
    public Double totalPrice = 0.0;
    @SerializedName("acceptedAt")
    @Expose
    public String acceptedAt;
    @SerializedName("completedAt")
    @Expose
    public Object completedAt;
    @SerializedName("gigPackageName")
    @Expose
    public String gigPackageName;
    @SerializedName("gigPackageDescription")
    @Expose
    public String gigPackageDescription;
    @SerializedName("revisions")
    @Expose
    public Integer revisions;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("duration")
    @Expose
    public Integer duration;
    @SerializedName("deliveryTitle")
    @Expose
    public String deliveryTitle;
    @SerializedName("gigTitle")
    @Expose
    public String gigTitle;
    @SerializedName("gigDescription")
    @Expose
    public String gigDescription;
    @SerializedName("requirements")
    @Expose
    public List<Requirement> requirements = null;
    @SerializedName("agentDetails")
    @Expose
    public AgentDetails agentDetails;
    @SerializedName("timer")
    @Expose
    public Timer timer;
    @SerializedName("clientAttachmentsPath")
    @Expose
    public String attachmentPath;
    @SerializedName("clientAttachments")
    @Expose
    public List<Attachments> attachments = null;
    @SerializedName("clientJobDescribe")
    @Expose
    public String clientJobDescribe;
    @SerializedName("agentSubmittedPath")
    @Expose
    public String agentSubmittedPath;
    @SerializedName("submittedFiles")
    @Expose
    public List<FileList> submittedFiles;
    @SerializedName("gr_id")
    @Expose
    public Integer grId;
    @SerializedName("gr_createdAt")
    @Expose
    public Object grCreatedAt;
    @SerializedName("gr_status")
    @Expose
    public String grStatus = null;
    @SerializedName("client_rate_id")
    @Expose
    public Integer clientRateId = 0;
    @SerializedName("agentReview")
    @Expose
    public AgentReview agentReview;
    @SerializedName("isAgentReview")
    @Expose
    public Integer isAgentReview = 0;
    @SerializedName("clientReview")
    @Expose
    public ClientReview clientReview;
    @SerializedName("customPackages")
    @Expose
    public List<CustomPackage> customPackages = null;
    @SerializedName("deadlineType")
    @Expose
    public String deadlineType;
    @SerializedName("deadlineValue")
    @Expose
    public Integer deadlineValue;
    @SerializedName("minPrice")
    @Expose
    public Double minPrice;
    @SerializedName("gigType")
    @Expose
    public String gigType;
    @SerializedName("social_platform")
    @Expose
    public List<SocialPlatform> socialPlatform = null;
    @SerializedName("job_post_contracts")
    @Expose
    public ProjectByID.JobPostContract jobPostContracts;

    public static ProjectGigByID getProjectGigById(String responseBody) {
        return new Gson().fromJson(responseBody, ProjectGigByID.class);
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

    public class Address implements Serializable {
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("countryAr")
        @Expose
        public String countryAr;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("cityAr")
        @Expose
        public String cityAr;

        public String getCountry(String lang) {
            if (lang.equals("ar")) {
                return countryAr == null ? country : countryAr;
            }
            return country;
        }

        public String getCity(String lang) {
            if (lang.equals("ar")) {
                return cityAr == null ? city : cityAr;
            }
            return city;
        }

    }

    public class AgentDetails implements Serializable {
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

    public class Timer implements Serializable {
        @SerializedName("days")
        @Expose
        public Integer days;
        @SerializedName("hours")
        @Expose
        public Integer hours;
        @SerializedName("minutes")
        @Expose
        public Integer minutes;
        @SerializedName("seconds")
        @Expose
        public Integer seconds;
        @SerializedName("isDue")
        @Expose
        public Boolean isDue;
    }

    public class CustomPackage implements Serializable {
        @SerializedName("gigID")
        @Expose
        public Integer gigID;
        @SerializedName("cgprID")
        @Expose
        public Integer cgprID;
        @SerializedName("gigs_requirementsID")
        @Expose
        public Integer gigsRequirementsID;
        @SerializedName("custom_packagesID")
        @Expose
        public Integer customPackagesID;
        @SerializedName("quantity")
        @Expose
        public Integer quantity;
        @SerializedName("gigRequirementType")
        @Expose
        public Integer gigRequirementType;
        @SerializedName("inputType")
        @Expose
        public Integer inputType;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("featureName")
        @Expose
        public String featureName = "";
        @SerializedName("reqOrOtherReqID")
        @Expose
        public Integer reqOrOtherReqID;
        @SerializedName("isOtherRequirment")
        @Expose
        public Integer isOtherRequirment;
        @SerializedName("from_quantity")
        @Expose
        public Object fromQuantity;
        @SerializedName("to_quantity")
        @Expose
        public Object toQuantity;
        @SerializedName("price")
        @Expose
        public double price;
    }

    public class SocialPlatform implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("socialPlatformID")
        @Expose
        public Integer socialPlatformID;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("followersCount")
        @Expose
        public Integer followersCount;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("platform_icon")
        @Expose
        public String platformIcon;
    }
}
