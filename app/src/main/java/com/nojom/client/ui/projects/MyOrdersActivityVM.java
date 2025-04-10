package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_GET_CURRENT_ORDERS;
import static com.nojom.client.util.Constants.API_GET_PAST_ORDERS;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.WalletListener;
import com.nojom.client.model.CampList;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

class MyOrdersActivityVM extends AndroidViewModel implements WalletListener {
    private BaseActivity activity;
    MutableLiveData<List<CampList>> pastOrderMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<CampList>> currentOrderMutableLiveData = new MutableLiveData<>();
    MutableLiveData<WalletData> currentWalletData = new MutableLiveData<>();

    MyOrdersActivityVM(Application application, BaseActivity myProjectsActivity) {
        super(application);
        activity = myProjectsActivity;
        //initData();
    }

    //    private void initData() {
//        if (!activity.isLogin()) {
//
//        }
//    }
    int page;

    public void getOrders(int page, boolean isPast) {
        if (!activity.isNetworkConnectedDialog()) {
            return;
        }
        this.page = page;
        ApiRequest apiRequest = new ApiRequest();
        if (isPast) {
            apiRequest.getOrderList(this, activity, API_GET_PAST_ORDERS + page);
        } else {
            apiRequest.getOrderList(this, activity, API_GET_CURRENT_ORDERS + page);
        }

    }

    @Override
    public void successResponse(WalletData responseBody, String url, String message) {
        if (url.equals(API_GET_CURRENT_ORDERS + page)) {
            if (responseBody != null) {
                currentWalletData.postValue(responseBody);
            }
        } else if (url.equals(API_GET_PAST_ORDERS + page)) {
            if (responseBody != null && responseBody.campaigns != null) {
                if (responseBody.campaigns.size() > 0) {
                    pastOrderMutableLiveData.postValue(responseBody.campaigns);
                }
            }
        }
    }

    @Override
    public void successTxnResponse(List<WalletData> responseBody, String url, String message) {

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }
}
