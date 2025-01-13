package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_CREATE_CAMP;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.model.CampListData;
import com.nojom.client.ui.BaseActivity;

public class CampaignStarActivityVM extends AndroidViewModel implements CampaignListener {
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    public MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public CampaignStarActivityVM(Application application, BaseActivity freelancerProfileActivity) {
        super(application);
        activity = freelancerProfileActivity;
    }

    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        activity.toastMessage(message);
        mutableProgress.postValue(false);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        activity.toastMessage("Error: "+message);
        mutableProgress.postValue(false);
    }

    public void paymentRelease(int id) {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);
        String url = API_CREATE_CAMP + "/" + id + "/payment/release";
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.paymentRelease(this, activity, url, null);

    }
}
