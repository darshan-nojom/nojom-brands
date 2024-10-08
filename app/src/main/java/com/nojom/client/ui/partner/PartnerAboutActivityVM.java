package com.nojom.client.ui.partner;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.ui.BaseActivity;

import org.json.JSONArray;

import java.util.HashMap;

import static com.nojom.client.util.Constants.API_ADD_PARTNER_ABOUT_ANSWER;


public class PartnerAboutActivityVM extends ViewModel implements RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSubmitAnswer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSubmitAnswerSuccess = new MutableLiveData<>();

    public PartnerAboutActivityVM() {

    }

    public MutableLiveData<Boolean> getIsShow() {
        return isShow;
    }

    public MutableLiveData<Boolean> getIsSubmitAnswer() {
        return isSubmitAnswer;
    }

    public MutableLiveData<Boolean> getIsSubmitAnswerSuccess() {
        return isSubmitAnswerSuccess;
    }

    public void init(BaseActivity activity) {
        this.activity = activity;

    }

    void addAboutAppSurvey(JSONArray jsonArray) {
        if (!activity.isNetworkConnected())
            return;
        getIsSubmitAnswer().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        HashMap<String, String> map = new HashMap<>();
        map.put("answers", jsonArray.toString() + "");
        apiRequest.apiRequest(this, activity, API_ADD_PARTNER_ABOUT_ANSWER, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_ADD_PARTNER_ABOUT_ANSWER)) {
            getIsSubmitAnswerSuccess().postValue(true);
            getIsSubmitAnswer().postValue(false);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getIsShow().postValue(false);
        if (url.equalsIgnoreCase(API_ADD_PARTNER_ABOUT_ANSWER)) {
            getIsSubmitAnswer().postValue(false);
        }
    }
}
