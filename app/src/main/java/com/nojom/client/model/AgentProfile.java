package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AgentProfile implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("saved")
    @Expose
    public Integer saved;
    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("settings_order")
    @Expose
    public String settings_order;
    @SerializedName("about_me")
    @Expose
    public String about_me;
    @SerializedName("mawthooq_id")
    @Expose
    public String mawthooq_id;
    @SerializedName("is_mawthooq_number")
    @Expose
    public String is_mawthooq_number;
    @SerializedName("mawthooq_path")
    @Expose
    public String mawthooq_path;
    @SerializedName("gender")
    @Expose
    public int gender;//2=male & 1=female

    @SerializedName("show_age")
    @Expose
    public int show_age;//2=show & 1=hidden
    @SerializedName("show_email")
    @Expose
    public int show_email;//1== public, 2 == private
    @SerializedName("birth_date")
    @Expose
    public String birth_date;
    @SerializedName("proUsername")
    @Expose
    public String proUsername;
    @SerializedName("firebaseLink")
    @Expose
    public String firebaseLink;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("block_status")
    @Expose
    public Integer blockStatus;
    @SerializedName("working_experience")
    @Expose
    public Integer workingExperience;
    @SerializedName("workbase")
    @Expose
    public String workbase;
    @SerializedName("pay_rate")
    @Expose
    public Double payRate;
    @SerializedName("referral_code")
    @Expose
    public String referralCode;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("email_confirmation_date")
    @Expose
    public String emailConfirmationDate;
    @SerializedName("profile_status")
    @Expose
    public ProfileStatus profileStatus;
    @SerializedName("trust_rate")
    @Expose
    public TrustRate trustRate;
    @SerializedName("profile_type_id")
    @Expose
    public Integer profileTypeId;
    @SerializedName("trust_rate_status")
    @Expose
    public TrustRateStatus trustRateStatus;
    @SerializedName("signup_mode")
    @Expose
    public String signupMode;
    @SerializedName("service_category_id")
    @Expose
    public Integer serviceCategoryId;
    @SerializedName("profile_pic")
    @Expose
    public String profilePic;
    @SerializedName("count_rating")
    @Expose
    public Integer countRating;
    @SerializedName("rate_sum")
    @Expose
    public Double rateSum;
    @SerializedName("rate")
    @Expose
    public Float rate;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("cityName")
    @Expose
    public String cityName;
    @SerializedName("longitude")
    @Expose
    public Double longitude;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("pro_address")
    @Expose
    public String proAddress;
    @SerializedName("headlines")
    @Expose
    public String headlines;
    @SerializedName("websites")
    @Expose
    public String websites;
    @SerializedName("contact_no")
    @Expose
    public String contactNo;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("summaries")
    @Expose
    public String summaries;
    @SerializedName("resumes")
    @Expose
    public String resumes;
    @SerializedName("percentage")
    @Expose
    public Percentage percentage;
    @SerializedName("expertise")
    @Expose
    public Expertise expertise;
    @SerializedName("language")
    @Expose
    public List<Language> language = null;
    @SerializedName("profile_publicity")
    @Expose
    public ProfilePublicity profilePublicity;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("resume_path")
    @Expose
    public String resumePath;
    @SerializedName("generalRank")
    @Expose
    public Integer generalRank = 0;
    @SerializedName("categoryRank")
    @Expose
    public Integer categoryRank = 0;
    @SerializedName("agent_profile_publicity")
    @Expose
    public List<AgentProfilePublicity> agentProfilePublicity = null;
    @SerializedName("agent_agency")
    @Expose
    public List<AgencyList.Data> agent_agency = null;

    @SerializedName("store")
    @Expose
    public List<StoreList> store = null;

    @SerializedName("agent_tags")
    @Expose
    public List<TagList> agent_tags = null;

    @SerializedName("agent_categories")
    @Expose
    public List<TagList> agent_categories = null;
    @SerializedName("social_platform")
    @Expose
    public List<ConnectedPlatform> social_platform = null;
    @SerializedName("bussiness_email")
    @Expose
    public String bussiness_email;
    @SerializedName("show_message_button")
    @Expose
    public int show_message_button;
    @SerializedName("show_send_offer_button")
    @Expose
    public int show_send_offer_button;
    @SerializedName("show_whatsapp")
    @Expose
    public int show_whatsapp;
    @SerializedName("whatsapp_number")
    @Expose
    public String whatsapp_number;
    @SerializedName("min_price")
    @Expose
    public Double min_price;
    @SerializedName("max_price")
    @Expose
    public Double max_price;


    public static AgentProfile getProfileInfo(String responseBody) {
        return new Gson().fromJson(responseBody, AgentProfile.class);
    }

    public static class TrustRate implements Serializable {

        @SerializedName("email")
        @Expose
        public Integer email;
        @SerializedName("phone_number")
        @Expose
        public Integer phoneNumber;
        @SerializedName("facebook")
        @Expose
        public Integer facebook;
        @SerializedName("payment")
        @Expose
        public Integer payment;
        @SerializedName("Verify_id")
        @Expose
        public Integer verifyId;
        @SerializedName("total_points")
        @Expose
        public Integer totalPoints;
        @SerializedName("points_needed")
        @Expose
        public Integer pointsNeeded;
    }

    public static class StoreList implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("profile_id")
        @Expose
        public String profile_id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("image_url")
        @Expose
        public String image_url;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("button_title")
        @Expose
        public String button_title;
    }

    public static class ConnectedPlatform implements Serializable {
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("followers")
        public int followers;
        @Expose
        @SerializedName("nameAr")
        public String nameAr;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return nameAr != null ? nameAr : name;
            }
            return name;
        }

        @Expose
        @SerializedName("web_url")
        public String web_url;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("display_order")
        public int display_order;
        @Expose
        @SerializedName("filename")
        public String filename;
        @Expose
        @SerializedName("social_platform_type_id")
        public int social_platform_type_id;
        @Expose
        @SerializedName("social_platform_id")
        public int social_platform_id;

        public boolean isSelected;

    }

    public static class TrustRateStatus implements Serializable {

        @SerializedName("email")
        @Expose
        public Integer email;
        @SerializedName("facebook")
        @Expose
        public Integer facebook;
        @SerializedName("payment")
        @Expose
        public Integer payment;
        @SerializedName("phone_number")
        @Expose
        public Integer phoneNumber;
        @SerializedName("Verify_id")
        @Expose
        public Integer verifyId;
    }

    public static class Percentage implements Serializable {
        @SerializedName("private_info")
        @Expose
        public Integer privateInfo;
        @SerializedName("skill")
        @Expose
        public Integer skill;
        @SerializedName("professional_info")
        @Expose
        public Integer professionalInfo;
        @SerializedName("verification")
        @Expose
        public Integer verification;
        @SerializedName("total_percentage")
        @Expose
        public Double totalPercentage;
    }

    public static class Expertise implements Serializable {
        @SerializedName("name")
        @Expose
        public String name = "";
        @SerializedName("name_app")
        @Expose
        public String nameApp = "";
        @SerializedName("length")
        @Expose
        public Integer length = 0;
    }

    public static class Services implements Serializable {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("name_app")
        public String nameApp;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class Service implements Serializable {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("name_app")
        public String nameApp;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class Language implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("level")
        @Expose
        public Integer level;
        @SerializedName("name")
        @Expose
        public String name;
    }

    public static class AgentAgency implements Serializable {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("website")
        @Expose
        public String website;
    }

    public static class Skill implements Serializable {
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("skill_category_id")
        public int skillCategoryId;
        @Expose
        @SerializedName("description")
        public String description;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ProfilePublicity implements Serializable {
        @Expose
        @SerializedName("headline")
        public int headline;
        @Expose
        @SerializedName("summary")
        public int summary;
        @Expose
        @SerializedName("resume")
        public int resume;
        @Expose
        @SerializedName("hour_rate")
        public int hourRate;
        @Expose
        @SerializedName("employment")
        public int employment;
        @Expose
        @SerializedName("education")
        public int education;
        @Expose
        @SerializedName("address")
        public int address;
        @Expose
        @SerializedName("pro_address")
        public int proAddress;
        @Expose
        @SerializedName("phone")
        public int phone;
        @Expose
        @SerializedName("email")
        public int email;
        @Expose
        @SerializedName("website")
        public int website;
    }

    public static class ProfileStatus implements Serializable {

        @SerializedName("private_info")
        @Expose
        public Integer privateInfo;
        @SerializedName("skill")
        @Expose
        public Integer skill;
        @SerializedName("professional_info")
        @Expose
        public Integer professionalInfo;
        @SerializedName("verification")
        @Expose
        public Integer verification;
        @SerializedName("total_percentage")
        @Expose
        public Double totalPercentage;
    }

    public class AgentProfilePublicity implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("publicity_type")
        @Expose
        public String publicityType;
        @SerializedName("status")
        @Expose
        public String status;
    }

    public static class TagList implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("rating")
        @Expose
        public int rating;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("name_ar")
        @Expose
        public String name_ar;

        public String getName(String lang) {
            if (lang.equals("ar")) {
                return name_ar != null ? name_ar : name;
            }
            return name;
        }
    }
}
