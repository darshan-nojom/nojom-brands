package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialSurveyListModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static SocialSurveyListModel getSocialSurveys(String responseBody) {
        return new Gson().fromJson(responseBody, SocialSurveyListModel.class);
    }

    public static class Data {
        @Expose
        @SerializedName("survey_status")
        public int surveyStatus = 0;
        @Expose
        @SerializedName("id")
        public int id = 0;
        @Expose
        @SerializedName("note")
        public String note = "";
    }

}
