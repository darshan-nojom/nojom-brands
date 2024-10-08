package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FileList implements Serializable {

    @SerializedName("title_data")
    @Expose
    public TitleData titleData;
    @SerializedName("files_data")
    @Expose
    public List<FilesDatum> filesData = null;

    public static class FilesDatum implements Serializable {
        @SerializedName("id")
        @Expose
        public Integer id;
        @Expose
        @SerializedName(value = "filename", alternate = "fileName")
        public String filename;
        @SerializedName("size")
        @Expose
        public Integer size;
        @Expose
        @SerializedName(value = "file_attribute_id", alternate = "agentFilesAttributesID")
        public String fileAttributeId;
    }

    public static class TitleData implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("description")
        @Expose
        public String description;
        @Expose
        @SerializedName(value = "timestamp", alternate = "createdAt")
        public String timestamp;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("job_post_bid_id")
        @Expose
        public Integer jobPostBidId;
        @SerializedName("status")
        @Expose
        public Integer status;
    }
}


