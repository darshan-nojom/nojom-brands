package com.nojom.client.ui.projects;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Services;
import com.nojom.client.model.ServicesData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;


public class GetServiceActivityVM extends ViewModel implements Constants, RequestResponseListener {

    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShowProgress = new MutableLiveData<>();
    public MutableLiveData<InfServices> serviceMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShowProgress() {
        return isShowProgress;
    }

    public GetServiceActivityVM() {

    }

    public void init(BaseActivity activity) {
        this.activity = activity;
    }


    public void getServices(int agentId) {
        if (!activity.isNetworkConnected()) return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getServices(activity, API_ADD_SERVICE + "/" + agentId, this);
    }


    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        InfServices serviceData = ServicesData.getServiceData(responseBody);
        if (serviceData != null) {
            serviceMutableLiveData.postValue(serviceData);
        }
        getIsShowProgress().postValue(false);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getIsShowProgress().postValue(false);
    }
}
