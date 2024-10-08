package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatLiveOfferStatusModel {
    @SerializedName("offerStatus")
    @Expose
    public int offerStatus = 0;
    @SerializedName("messageId")
    @Expose
    public Long messageId;
    @SerializedName("senderId")
    @Expose
    public String senderId = "";
    @SerializedName("receiverId")
    @Expose
    public String receiverId = "";
    @SerializedName("partitionKey")
    @Expose
    public String partitionKey = "";

}