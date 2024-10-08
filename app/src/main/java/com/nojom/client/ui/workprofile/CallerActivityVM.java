package com.nojom.client.ui.workprofile;


import static com.nojom.client.util.Constants.API_GET_BANKS;
import static com.nojom.client.util.Constants.API_GET_CLIENT_PROFILE;
import static com.nojom.client.util.Constants.API_GET_CLIENT_RATES;
import static com.nojom.client.util.Constants.API_SERVICE_CATEGORIES;
import static com.nojom.client.util.Constants.API_SERVICE_CATEGORIES_V2;
import static com.nojom.client.util.Constants.API_SOCIAL_PLATFORMS;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.Banks;
import com.nojom.client.model.ClientRate;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SkillTags;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.clientprofile.MyProfileActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.HashMap;
import java.util.List;

public class CallerActivityVM extends AndroidViewModel implements RequestResponseListener, BaseActivity.OnProfileLoadListener {
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    private final SocialGigListVM socialGigListVM;

    public CallerActivityVM(@NonNull Application application, BaseActivity activity) {
        super(application);
        this.activity = activity;
        socialGigListVM = new SocialGigListVM(application, activity);
        socialGigListVM.searchInfluencers("", 1);
        getTopServiceV2List();
        getTopServiceList();
        getSocialPlatforms();

        if (activity.isLogin()) {
            getClientRates();
            activity.getProfile();
            getBanks();
            activity.setOnProfileLoadListener(this);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(activity, MainActivity.class);
            if (activity.isLogin()) {
                if (activity.getIsVerified() == 0) {
                    i = new Intent(activity, MyProfileActivity.class);
                } else {
                    i = new Intent(activity, MainActivity.class);
                    i.putExtra(Constants.SCREEN_NAME, Constants.TAB_HOME);
                }
            }
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(i);
            activity.openToLeft();
            activity.finish();
        }, 4000);
    }

    private void getClientRates() {
        if (!activity.isNetworkConnectedDialog())
            return;

        boolean isFixedPrice = Preferences.readBoolean(activity, Constants.IS_FIXED_PRICE, false);
        HashMap<String, String> map = new HashMap<>();
        map.put("pay_type_id", /*isFixedPrice ? 1 + "" :*/ 1 + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CLIENT_RATES, true, map);
    }

    private void getTopServiceList() {
        if (!activity.isNetworkConnectedDialog())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SERVICE_CATEGORIES, false, null);
    }

    private void getTopServiceV2List() {
        if (!activity.isNetworkConnectedDialog())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SERVICE_CATEGORIES_V2);
    }

    private void getBanks() {
        if (!activity.isNetworkConnectedDialog())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_BANKS, false, null);
    }

    private void getSocialPlatforms() {
        if (!activity.isNetworkConnectedDialog())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SOCIAL_PLATFORMS, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_CLIENT_RATES)) {
            ClientRate model = ClientRate.getClientRates(responseBody);
            if (model != null) {
                Preferences.setClientRate(activity, model);
            }
        } else if (url.equalsIgnoreCase(API_SERVICE_CATEGORIES)) {
            ServicesModel servicesModel = ServicesModel.getServiceData(responseBody);
            if (servicesModel != null && servicesModel.data != null) {
                Preferences.saveTopServices(getApplication().getApplicationContext(), servicesModel.data);
            }
        } else if (url.equalsIgnoreCase(API_SERVICE_CATEGORIES_V2)) {
            List<ServicesModel.Data> servicesModel = ServicesModel.getServiceDataCat(responseBody);
            if (servicesModel != null && servicesModel.size() > 0) {
                Preferences.saveCategoryV2(getApplication().getApplicationContext(), servicesModel);
            }
        } else if (url.equalsIgnoreCase(API_GET_BANKS)) {
            Banks banks = Banks.getBanks(responseBody);
            if (banks != null && banks.data != null) {
                Preferences.saveBanks(getApplication().getApplicationContext(), banks.data);
            }
        } else if (url.equalsIgnoreCase(API_SOCIAL_PLATFORMS)) {
            SocialPlatformModel platformModel = SocialPlatformModel.getSocialPlatform(responseBody);
            if (platformModel != null && platformModel.data != null) {
                Preferences.saveSocialPlatform(getApplication().getApplicationContext(), platformModel.data);
            }
        } else if (url.equalsIgnoreCase(API_GET_CLIENT_PROFILE)) {
            Profile profile = Profile.getProfileInfo(responseBody);
            if (profile != null) {
                Preferences.setProfileData(activity, profile);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }

    @Override
    public void onProfileLoad(Profile data) {
        if (data != null) {
            Preferences.setProfileData(activity, data);
        }
    }
}
