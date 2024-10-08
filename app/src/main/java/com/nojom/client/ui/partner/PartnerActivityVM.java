package com.nojom.client.ui.partner;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.CountryModel;
import com.nojom.client.model.PartnerWithUsResponse;
import com.nojom.client.model.Topic;
import com.nojom.client.ui.BaseActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nojom.client.util.Constants.API_ADD_PARTNER_ANSWER;
import static com.nojom.client.util.Constants.API_GET_ALL_COUNTRIES;
import static com.nojom.client.util.Constants.API_GET_PARTNER_QUESTION;

public class PartnerActivityVM extends ViewModel implements RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private MutableLiveData<ArrayList<PartnerWithUsResponse.Data>> listMutableLiveData;
    private List<Topic.Data> topicDataList;
    private MutableLiveData<List<Topic.Data>> mutableTopicData;
    private MutableLiveData<Boolean> isShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> isShowCountryProgress = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSubmitAnswer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSubmitAnswerSuccess = new MutableLiveData<>();
    private MutableLiveData<List<CountryModel.Data>> countryLiveData = new MutableLiveData<>();

    public PartnerActivityVM() {

    }

    public MutableLiveData<Boolean> getIsSubmitAnswerSuccess() {
        return isSubmitAnswerSuccess;
    }

    public MutableLiveData<Boolean> getIsSubmitAnswer() {
        return isSubmitAnswer;
    }

    public MutableLiveData<List<CountryModel.Data>> getCountryLiveData() {
        return countryLiveData;
    }

    public MutableLiveData<Boolean> getIsShowCountryProgress() {
        return isShowCountryProgress;
    }

    public MutableLiveData<Boolean> getIsShow() {
        return isShow;
    }

    public MutableLiveData<List<Topic.Data>> getMutableTopicData() {
        if (mutableTopicData == null) {
            mutableTopicData = new MutableLiveData<>();
        }
        return mutableTopicData;
    }

    public MutableLiveData<ArrayList<PartnerWithUsResponse.Data>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    public void init(BaseActivity activity) {
        this.activity = activity;
        getCountries();
    }


    void getPartnerQuestions() {
        if (!activity.isNetworkConnected())
            return;

        getIsShow().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PARTNER_QUESTION, false, null);
    }

    public void getCountries() {
        if (!activity.isNetworkConnected())
            return;

        getIsShowCountryProgress().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_ALL_COUNTRIES, false, null);
    }

    void addAppSurvey(JSONArray jsonArray) {
        if (!activity.isNetworkConnected())
            return;
        getIsSubmitAnswer().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        HashMap<String, String> map = new HashMap<>();
        map.put("answers", jsonArray.toString() + "");
        apiRequest.apiRequest(this, activity, API_ADD_PARTNER_ANSWER, true, map);
    }


    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PARTNER_QUESTION)) {
            PartnerWithUsResponse languageList = PartnerWithUsResponse.getPartnerWithUsQuestionList(responseBody);
            if (languageList != null && languageList.data.size() > 0) {
                getListMutableLiveData().postValue(languageList.data);
            }
            getIsShow().postValue(false);
        } else if (url.equalsIgnoreCase(API_GET_ALL_COUNTRIES)) {
            getIsShowCountryProgress().postValue(false);
            CountryModel countryModel = CountryModel.getCountryList(responseBody);
            if (countryModel != null && countryModel.data.size() > 0) {
                getCountryLiveData().postValue(countryModel.data);
            }
        } else if (url.equalsIgnoreCase(API_ADD_PARTNER_ANSWER)) {
            getIsSubmitAnswerSuccess().postValue(true);
            getIsSubmitAnswer().postValue(false);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_GET_ALL_COUNTRIES)) {
            getIsShowCountryProgress().postValue(false);
        } else if (url.equalsIgnoreCase(API_ADD_PARTNER_ANSWER)) {
            getIsSubmitAnswer().postValue(false);
        } else {
            getIsShow().postValue(false);
        }
    }
}
