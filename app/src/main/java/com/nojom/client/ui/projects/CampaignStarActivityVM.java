package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_CREATE_CAMP;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.CampaignRelease;
import com.nojom.client.ui.BaseActivity;

public class CampaignStarActivityVM extends AndroidViewModel implements CampaignListener {
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    public MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public MutableLiveData<Integer> mutableSuccess = new MutableLiveData<>();

    public CampaignStarActivityVM(Application application, BaseActivity freelancerProfileActivity) {
        super(application);
        activity = freelancerProfileActivity;
    }

    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        activity.toastMessage(message);
        mutableProgress.postValue(false);
        mutableSuccess.postValue(1);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (!TextUtils.isEmpty(message)) {
            activity.toastMessage("Error: " + message);
        }
        mutableProgress.postValue(false);
        mutableSuccess.postValue(0);
    }

    public void paymentRelease(int starId, int campId) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);
        String url = API_CREATE_CAMP + "/" + campId + "/payment/release";

        CampaignRelease campaignRelease = new CampaignRelease();
        campaignRelease.star_id = starId;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.paymentRelease(this, activity, url, campaignRelease);

    }
}
