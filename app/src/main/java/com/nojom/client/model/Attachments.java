package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attachments implements Serializable {
    @Expose
    @SerializedName("status")
    public Integer status;
    @Expose
    @SerializedName(value = "timestamp", alternate = "createdAt")
    public String timestamp;
    @Expose
    @SerializedName(value = "filename", alternate = "fileName")
    public String filename;
    @Expose
    @SerializedName("job_post_id")
    public Integer jobPostId;
    @Expose
    @SerializedName("id")
    public Integer id;

    public Attachments(Integer status, String timestamp, String filename, Integer jobPostId, Integer id) {
        this.status = status;
        this.timestamp = timestamp;
        this.filename = filename;
        this.jobPostId = jobPostId;
        this.id = id;
    }
}