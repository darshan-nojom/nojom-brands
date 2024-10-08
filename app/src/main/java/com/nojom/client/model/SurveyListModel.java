package com.nojom.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurveyListModel extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("survey_question_id")
        public int surveyQuestionId;
        @Expose
        @SerializedName("profile_id")
        public int profileId;
        @Expose
        @SerializedName("answer_text")
        public String answer_text;
    }

}
