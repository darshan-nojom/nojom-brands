package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.io.Serializable;
import java.util.List;

public class ExpertGigDetail extends GeneralModel implements Serializable {
    @SerializedName("gigID")
    @Expose
    public Integer gigID;
    @SerializedName("gigTitle")
    @Expose
    public String gigTitle;
    @SerializedName("gigDescription")
    @Expose
    public String gigDescription;
    @SerializedName("parentCategoryNamme")
    @Expose
    public String parentCategoryNamme;
    @SerializedName("parentCategoryID")
    @Expose
    public Integer parentCategoryID;
    @SerializedName("subCategoryNamme")
    @Expose
    public String subCategoryNamme;
    @SerializedName("subCategoryID")
    @Expose
    public Integer subCategoryID;
    @SerializedName("agentProfileID")
    @Expose
    public Integer agentProfileID;
    @SerializedName("agentUserName")
    @Expose
    public String agentUserName;
    @SerializedName("agentProfileImg")
    @Expose
    public String agentProfileImg;
    @SerializedName("agentProfilePath")
    @Expose
    public String agentProfilePath;
    @SerializedName("rate")
    @Expose
    public Float rate;
    @Expose
    @SerializedName("count_rating")
    public Float countRating;
    @SerializedName("gigImages")
    @Expose
    public List<GigImage> gigImages = null;
    @SerializedName("gigImagesPath")
    @Expose
    public String gigImagesPath;
    @SerializedName("packages")
    @Expose
    public List<GigPackage> packages = null;
    @Expose
    @SerializedName("saved")
    public int saved;
    @SerializedName("deposit_charges")
    @Expose
    public Integer depositCharges = 5;
    @SerializedName("fixed_price")
    @Expose
    public Double fixedPrice = 0.0;
    @SerializedName("quantity")
    @Expose
    public Integer quantity = 1;
    @SerializedName("starpoints")
    @Expose
    public Float starpoints;
    @SerializedName("real_count")
    @Expose
    public Integer realCount;
    @SerializedName("generalRank")
    @Expose
    public Integer generalRank = 0;
    @SerializedName("categoryRank")
    @Expose
    public Integer categoryRank = 0;
    @SerializedName("isFirstOrder")
    @Expose
    public Integer isFirstOrder = 0;
    @SerializedName("couponData")
    @Expose
    public CouponData couponData;
    @SerializedName("sum_rating")
    @Expose
    public Double sumRating;
    @SerializedName("finalCalculatedPrice")
    @Expose
    public Double finalCalculatedPrice;
    @SerializedName("gigSubCategories")
    @Expose
    public List<GigSubCategory> gigSubCategories = null;
    @SerializedName("languages")
    @Expose
    public List<Language> languages = null;
    @SerializedName("searchTags")
    @Expose
    public List<SearchTag> searchTags = null;
    @SerializedName("customPackages")
    @Expose
    public List<CustomPackage> customPackages = null;
    @SerializedName("gigType")
    @Expose
    public String gigType;
    @SerializedName("deadlineValue")
    @Expose
    public Integer deadlineValue;
    @SerializedName("deadlineType")
    @Expose
    public String deadlineType;
    @SerializedName("minPrice")
    @Expose
    public Double minPrice = 0.0;
    @SerializedName("deadlineDescription")
    @Expose
    public String deadlineDescription = null;
    @SerializedName("deadlines")
    @Expose
    public List<Deadline> deadlines = null;
    @SerializedName("social_platform")
    @Expose
    public List<SocialPlatform> socialPlatform = null;
    public int deadlineID = 0;
    public double finalAmount = 0;
    public boolean isFromOffer = false;
    public int offerID = 0;
    public String pk = "";
    public Long sk = null;
    public int receiverId = 0;
    public Long messageId = null;
    public double price = 0;

    public static ExpertGigDetail getGigDetail(String responseBody) {
        return new Gson().fromJson(responseBody, ExpertGigDetail.class);
    }

    public static class GigImage implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("imageName")
        @Expose
        public String imageName;
    }

    public class GigPackage implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("revisions")
        @Expose
        public Integer revisions;
        @SerializedName("price")
        @Expose
        public Double price;
        @SerializedName("deliveryTitle")
        @Expose
        public String deliveryTitle;
        @SerializedName("requirements")
        @Expose
        public List<Requirement> requirements = null;
    }

    public class CouponData implements Serializable {
        @SerializedName("coupon_code")
        @Expose
        public String couponCode;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("discount")
        @Expose
        public Integer discount;
    }

    public class GigSubCategory implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
    }

    public class SearchTag implements Serializable {

        @SerializedName("tagName")
        @Expose
        public String tagName;
    }

    public class CustomPackage implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("name_ar")
        @Expose
        public String name_ar;
        @SerializedName("description")
        @Expose
        public String description = "";
        @SerializedName("inputType")
        @Expose
        public Integer inputType;
        @SerializedName("gigRequirementType")
        @Expose
        public Integer gigRequirementType;
        @SerializedName("requirmentDetails")
        @Expose
        public List<RequirmentDetail> requirmentDetails = null;
        public int customPackageID;
        public double price = 0;
        public double finalAmount = 0;
        public String quantity = "0";

        public String getName(BaseActivity activity) {
            if (activity.getLanguage().equals("ar")) {
                return name_ar!=null?(name_ar.isEmpty() ? name : name_ar):name;
            } else {
                return name;
            }

        }
    }


    public class RequirmentDetail implements Serializable {
        @SerializedName("price")
        @Expose
        public Double price;
        @SerializedName("from_quantity")
        @Expose
        public Object fromQuantity;
        @SerializedName("to_quantity")
        @Expose
        public Object toQuantity;
        @SerializedName("featureName")
        @Expose
        public String featureName;
        @SerializedName("customPackageID")
        @Expose
        public int customPackageID;
        @SerializedName("index")
        @Expose
        public int index;
    }

    public class Deadline implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id = 0;
        @SerializedName("value")
        @Expose
        public Integer value = 0;
        @SerializedName("type")
        @Expose
        public Integer type = 0;
        @SerializedName("price")
        @Expose
        public Double price = 0.0;
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
        @SerializedName("followers")
        @Expose
        public Integer followersCount;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("filename")
        @Expose
        public String platformIcon;
        @SerializedName("redirectUrl")
        @Expose
        public String redirectUrl;
        @SerializedName("colorCode")
        @Expose
        public String colorCode;
        @SerializedName("androidRedirectUrl")
        @Expose
        public String androidRedirectUrl;
    }

}
