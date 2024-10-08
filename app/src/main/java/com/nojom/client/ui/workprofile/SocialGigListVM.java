package com.nojom.client.ui.workprofile;

import static com.nojom.client.util.Constants.API_SEARCH_INFLU;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.AllSocialGigs;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialGigListVM extends AndroidViewModel implements RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private MutableLiveData<AllSocialGigs> allSocialGigsMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<AllSocialGigs> getAllSocialGigsMutableLiveData() {
        return allSocialGigsMutableLiveData;
    }
    public SocialGigListVM(@NonNull Application application, BaseActivity activity) {
        super(application);
        this.activity = activity;
//        getSocialGigs();
        searchInfluencers("",1);
    }

//    public void getSocialGigs() {
//        if (!activity.isNetworkConnectedDialog()) {
//            return;
//        }
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequest(this, activity, API_GET_SOCIAL_GIGS, false, null);
//    }


    public void searchInfluencers(String search, int page) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", page + "");
        map.put("search", search + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEARCH_INFLU, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_SEARCH_INFLU)) {
            AllSocialGigs gigs = AllSocialGigs.getAllSocialGigs(responseBody);
            if (gigs != null) {
                getAllSocialGigsMutableLiveData().postValue(gigs);
                Preferences.setAllSocialGigs(activity, gigs);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getAllSocialGigsMutableLiveData().postValue(null);
    }

}
