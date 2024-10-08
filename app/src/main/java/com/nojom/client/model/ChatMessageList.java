package com.nojom.client.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ChatMessageList implements Serializable {
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("data")
        @Expose
        public List<DataChatList> dataChatList = null;
        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("scannedCount")
        @Expose
        public Integer scannedCount;
        @SerializedName("lastEvaluatedKey")
        @Expose
        public LastEvaluatedKey lastEvaluatedKey;
        @SerializedName("project")
        @Expose
        public Project project;

        @NonNull
        @Override
        public String toString() {
            return new Gson().toJson(lastEvaluatedKey);
        }
    }

    public class Project {
        @SerializedName("projectId")
        @Expose
        public int projectId;
        @SerializedName("projectType")
        @Expose
        public String projectType;
        @SerializedName("isMute")
        @Expose
        public boolean isMute;
        @SerializedName("c_mute")
        @Expose
        public boolean c_mute;
        @SerializedName("a_mute")
        @Expose
        public boolean a_mute;
    }

    public class DataChatList {

        @SerializedName("messageSeenAt")
        @Expose
        public String messageSeenAt;
        @SerializedName("messageId")
        @Expose
        public Long messageId;
        @SerializedName("receiverId")
        @Expose
        public String receiverId;
        @SerializedName("senderId")
        @Expose
        public String senderId;
        @SerializedName("isMessageDeleted")
        @Expose
        public boolean isMessageDeleted;
        @SerializedName("messageCreatedAt")
        @Expose
        public long messageCreatedAt;
        @SerializedName("SK")
        @Expose
        public Long sK;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("PK")
        @Expose
        public String pK;
        @SerializedName("isSeenMessage")
        @Expose
        public String isSeenMessage;
        @SerializedName("file")
        @Expose
        public File file;
        @SerializedName("self")
        @Expose
        public boolean self;
        public boolean isDayChange = false;
        @SerializedName("offer")
        @Expose
        public Offer offer = null;
    }

    public class LastEvaluatedKey {
        @SerializedName("PK")
        @Expose
        public String pK;
        @SerializedName("SK")
        @Expose
        public double sK;
    }

    public class File {
        @SerializedName("path")
        @Expose
        public String path;
        @SerializedName("files")
        @Expose
        public List<FileImages> files = null;
    }

    public class FileImages {
        @SerializedName("fileId")
        @Expose
        public double fileId;
        @SerializedName("SK")
        @Expose
        public double sK;
        @SerializedName("file")
        @Expose
        public String file;
        @SerializedName("fileStorage")
        @Expose
        public String fileStorage = "";
        @SerializedName("firebaseUrl")
        @Expose
        public String firebaseUrl = "";
    }

    public class Offer {
        @SerializedName("agentID")
        @Expose
        public Integer agentID;
        @SerializedName("clientID")
        @Expose
        public String clientID;
        @SerializedName("deadlineType")
        @Expose
        public Integer deadlineType;
        @SerializedName("price")
        @Expose
        public Double price;
        @SerializedName("offerID")
        @Expose
        public Integer offerID;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("gigID")
        @Expose
        public Integer gigID;
        @SerializedName("deadlineValue")
        @Expose
        public Integer deadlineValue;
        @SerializedName("parentServiceCategoryID")
        @Expose
        public String parentServiceCategoryID;
        @SerializedName("offerTitle")
        @Expose
        public String offerTitle;
        @SerializedName("gigType")
        @Expose
        public int gigType;
        @SerializedName("offerStatus")
        @Expose
        public int offerStatus;
        @SerializedName("contractID")
        @Expose
        public int contractID;
    }

}

