package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.io.Serializable;
import java.util.List;

public class Projects extends GeneralModel implements Serializable {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static Projects getJobPost(String responseBody) {
        return new Gson().fromJson(responseBody, Projects.class);
    }

    public static class Data {
        @SerializedName("jp_id")
        @Expose
        public Integer jpId;
        @SerializedName("profile_id")
        @Expose
        public Integer profileId;
        @SerializedName("jp_title")
        @Expose
        public String jpTitle;
        @SerializedName("client_rate_id")
        @Expose
        public Integer clientRateId;
        @SerializedName("edited_by_admin")
        @Expose
        public String editedByAdmin;
        @SerializedName("offered")
        @Expose
        public String offered;
        @SerializedName("date_completed")
        @Expose
        public Object dateCompleted;
        @SerializedName("sys_id")
        @Expose
        public Integer sysId;
        @SerializedName("jp_timestamp")
        @Expose
        public String jpTimestamp;
        @SerializedName("bids_count")
        @Expose
        public Integer bidsCount;
        @SerializedName("jps_id")
        @Expose
        public int jpsId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("name_ar")
        @Expose
        public String name_ar;

        public String getStatusName(BaseActivity activity) {
            if (activity.getLanguage().equals("ar")) {
                return name_ar != null ? name_ar : name;
            } else {
                return name;
            }
        }

        @SerializedName("cr_id")
        @Expose
        public Integer crId;
        @SerializedName("range_from")
        @Expose
        public Double rangeFrom;
        @SerializedName("range_to")
        @Expose
        public Double rangeTo;
        @SerializedName("jpbudget_id")
        @Expose
        public int jpbudgetId;
        @SerializedName("job_post_id")
        @Expose
        public int jobPostId;
        @SerializedName("budget")
        @Expose
        public Double budget;
        @SerializedName("pay_type_id")
        @Expose
        public int payTypeId;
        @SerializedName("jr_id")
        @Expose
        public Integer jrId;
        @SerializedName("jr_timestamp")
        @Expose
        public Object jrTimestamp;
        @SerializedName("jr_status")
        @Expose
        public int jrStatus;
        @SerializedName("job_pay_type")
        @Expose
        public JobPayType jobPayType;
        @SerializedName("agent_id")
        @Expose
        public int agentId;
        @SerializedName("jp_description")
        @Expose
        public String jpDescription;
        @SerializedName("job")
        @Expose
        public String job;
        @SerializedName("gigType")
        @Expose
        public String gigType;
        public boolean isShowProgress;
    }

    public static class JobPayType {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("detail")
        @Expose
        public String detail;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("status")
        @Expose
        public Integer status;
    }

    public static class JobPostState {
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class JobPostBudget {
        @Expose
        @SerializedName("pay_type_id")
        public int payTypeId;
        @Expose
        @SerializedName("budget")
        public int budget;
        @Expose
        @SerializedName("job_post_id")
        public int jobPostId;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class ClientRate {
        @Expose
        @SerializedName("range_to")
        public String rangeTo;
        @Expose
        @SerializedName("range_from")
        public String rangeFrom;
        @Expose
        @SerializedName("pay_type_id")
        public int payTypeId;
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("status")
        public int status;
        @Expose
        @SerializedName("timestamp")
        public String timestamp;
    }
}
