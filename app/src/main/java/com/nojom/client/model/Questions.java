package com.nojom.client.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class Questions extends GeneralModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @Expose
        @SerializedName("type")
        public int type;
        @Expose
        @SerializedName("question")
        public String question;
        @Expose
        @SerializedName("question_ar")
        public String question_ar;
        @Expose
        @SerializedName("id")
        public int id;

        public String getQuestion(BaseActivity activity) {
            if (activity.getLanguage().equals("ar")) {
                return question_ar == null ? question : question_ar;
            }
            return question;
        }
    }

    public static Questions getQuestions(String responseBody) {
        return new Gson().fromJson(responseBody, Questions.class);
    }
}
