package com.nojom.client.model;

public class Attachment {

    public String filepath;
    public String fileUrl;
    public boolean isImage;
    public String fileId;

    public Attachment(String filepath, String fileId, String fileUrl, boolean isImage) {
        this.filepath = filepath;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.isImage = isImage;
    }

    public Attachment(String filepath, boolean isImage) {
        this.filepath = filepath;
        this.isImage = isImage;
    }
}
