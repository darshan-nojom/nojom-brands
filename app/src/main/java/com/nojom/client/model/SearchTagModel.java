package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchTagModel implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id=0;
    @SerializedName("serviceCategoryID")
    @Expose
    public Integer serviceCategoryID=0;
    @SerializedName("customSearchTagID")
    @Expose
    public Integer customSearchTagID=0;
    @SerializedName("tag_name")
    @Expose
    public String tagName="";

    public SearchTagModel(Integer id, Integer serviceCategoryID, Integer customSearchTagID, String tagName) {
        this.id = id;
        this.serviceCategoryID = serviceCategoryID;
        this.customSearchTagID = customSearchTagID;
        this.tagName = tagName;
    }
}
