package com.nojom.client.ui.projects;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Agents;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.InfServices;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.HashMap;
import java.util.List;


public class AddStarActivityVM extends ViewModel implements Constants, CampaignListener, RequestResponseListener {

    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShowProgress = new MutableLiveData<>();
    public MutableLiveData<CampListData> agentMutableData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShowProgress() {
        return isShowProgress;
    }

    public AddStarActivityVM() {

    }

    public void init(BaseActivity activity) {
        this.activity = activity;
    }


    public void getAgentService(int page, int limit) {
        if (!activity.isNetworkConnected()) return;

        String url = API_AGENT_SERVICE + page + "&limit=" + limit;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getAgentService(this, activity, url);
    }

    public void getAgentServiceFilter(int page, int limit, String search) {
        if (!activity.isNetworkConnected()) return;

        String url = API_AGENT_SERVICE + page + "&limit=" + limit;
        if (!TextUtils.isEmpty(search)) {
            url = API_AGENT_SERVICE + page + "&limit=" + limit + "&agentName=" + search;
        }
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getAgentService(this, activity, url);
    }

    public void getAgentProfile(int agentProfileId) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }


    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        activity.isClickableView = false;
        agentMutableData.postValue(responseBody);
        getIsShowProgress().postValue(false);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            getIsShowProgress().postValue(false);
            Preferences.writeString(activity, "influencerName", null);
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
//            activity.runOnUiThread(() -> {
//                if (bestInfAdapter != null) {
//                    bestInfAdapter.getData().get(selectedPos).isShowProgress = false;
//                    bestInfAdapter.notifyItemChanged(selectedPos);
//                }
//            });
            if (profile != null) {
                Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                activity.startActivity(i);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        getIsShowProgress().postValue(false);
        activity.toastMessage(message);
    }
}
