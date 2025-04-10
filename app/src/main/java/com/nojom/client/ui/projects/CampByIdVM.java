package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_CHARGE_WALLET;
import static com.nojom.client.util.Constants.API_CREATE_CAMP;
import static com.nojom.client.util.Constants.API_GET_WALLET;
import static com.nojom.client.util.Constants.API_GET_WALLET_TXN;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.api.WalletListener;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.ChargeAmount;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class CampByIdVM extends AndroidViewModel implements CampaignListener, WalletListener {
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    public MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public MutableLiveData<List<WalletData>> mpWalletData = new MutableLiveData<>();
    public MutableLiveData<WalletData> mpWalletBalanceData = new MutableLiveData<>();
    public MutableLiveData<CampListData> campListData = new MutableLiveData<>();

    public CampByIdVM(Application application, BaseActivity freelancerProfileActivity) {
        super(application);
        activity = freelancerProfileActivity;
    }

    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        //activity.toastMessage(message);
        mutableProgress.postValue(false);
        activity.isClickableView = false;
        if (url.equals(updateUrl)) {
            updateUrl = "";
            campListData.postValue(responseBody);
        }
    }

    @Override
    public void successResponse(WalletData responseBody, String url, String message) {
        if (url.equals(API_GET_WALLET)) {
            mpWalletBalanceData.postValue(responseBody);
        } else if (url.equals(API_CHARGE_WALLET)) {
            mpWalletBalanceData.postValue(responseBody);
        }
        activity.isClickableView = false;
    }

    @Override
    public void successTxnResponse(List<WalletData> responseBody, String url, String message) {
        if (url.equals(API_GET_WALLET_TXN)) {
            mpWalletData.postValue(responseBody);
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        activity.toastMessage("Error: " + message);
        mutableProgress.postValue(false);
    }

    String updateUrl;

    public void getCampaignById(int id) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);
        updateUrl = API_CREATE_CAMP + "/" + id;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getCampaignById(this, activity, updateUrl);
    }

    public void getWalletBalance() {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getWalletBalance(this, activity, API_GET_WALLET);
    }

    public void getWalletTransaction() {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getWalletTxn(this, activity, API_GET_WALLET_TXN);

    }

    public void chargeWallet(Double amnt) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;
        mutableProgress.postValue(true);

        ChargeAmount amount = new ChargeAmount(amnt);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.chargeWallet(this, activity, API_CHARGE_WALLET, amount);
    }

}

