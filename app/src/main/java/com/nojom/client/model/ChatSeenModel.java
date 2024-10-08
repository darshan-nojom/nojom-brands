package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatSeenModel {
    @SerializedName("partitionKey")
    @Expose
    public String partitionKey;
    @SerializedName("receiverId")
    @Expose
    public String receiverId;
    @SerializedName("senderId")
    @Expose
    public String senderId;
    @SerializedName("messageIds")
    @Expose
    public Long[] messageIds;

}