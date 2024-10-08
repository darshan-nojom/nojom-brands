package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FileUpload extends GeneralModel {

    @Expose
    @SerializedName("attachments")
    public List<Data> data;
    @Expose
    @SerializedName("path")
    public String path;

    public static FileUpload getAttachmentList(String responseBody) {
        return new Gson().fromJson(responseBody, FileUpload.class);
    }

    public static class Data {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName(value = "filename", alternate = "fileName")
        public String filename;
        @Expose
        @SerializedName(value = "timestamp", alternate = "createdAt")
        public String timestamp;

    }
}
