package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SimpleResponse extends Wrapper implements Serializable {
    @Expose
    @SerializedName("data")
    public List<SkillTags.Data> data;

}