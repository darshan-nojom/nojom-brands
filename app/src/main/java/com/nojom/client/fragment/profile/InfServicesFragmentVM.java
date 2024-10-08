package com.nojom.client.fragment.profile;

import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.adapter.InfluencerServiceAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentInfAllBinding;
import com.nojom.client.databinding.FragmentInfServicesBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.home.GigDetailActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.List;

public class InfServicesFragmentVM extends AndroidViewModel implements InfluencerServiceAdapter.OnClickService, RequestResponseListener {

    FragmentInfServicesBinding binding;
    BaseFragment fragment;
    private int gigId = 0, selectedPos;
    InfluencerServiceAdapter influencerServiceAdapter;
    InfServicesFragmentVM(Application application, FragmentInfServicesBinding reviewsProfileBinding, BaseFragment reviewsProfileFragment) {
        super(application);
        binding = reviewsProfileBinding;
        fragment = reviewsProfileFragment;
    }

    public void setServicesAdapter() {
        List<SocialPlatformList.Data> socialPlatformList = ((InfluencerProfileActivity) fragment.activity).getSocialServiceList();
        if (socialPlatformList != null && socialPlatformList.size() > 0) {
            influencerServiceAdapter = new InfluencerServiceAdapter(fragment.activity, socialPlatformList,this);
            binding.rvServices.setAdapter(influencerServiceAdapter);
//            binding.rvServices.setVisibility(View.VISIBLE);
//            binding.tvPhService.setVisibility(View.GONE);
        } else {
//            binding.rvServices.setVisibility(View.GONE);
//            binding.tvPhService.setVisibility(View.VISIBLE);
        }
    }

    private void getGigDetails() {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();

        apiRequest.apiRequest(this, fragment.activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
         if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            ExpertGigDetail expertGigDetail = ExpertGigDetail.getGigDetail(responseBody);
            Preferences.writeString(fragment.activity, "gigID", null);

            if (expertGigDetail != null) {

                fragment.activity.runOnUiThread(() -> {
                    if (influencerServiceAdapter != null) {
                        influencerServiceAdapter.getData().get(selectedPos).isShowProgress = false;
                        influencerServiceAdapter.notifyItemChanged(selectedPos);
                    }
                });

                Intent intent = new Intent(fragment.activity, GigDetailActivity.class);
                intent.putExtra(Constants.PROJECT_DETAIL, expertGigDetail);
//                intent.putExtra("gigID", gigId);
                fragment.activity.startActivity(intent);
            }
            gigId = 0;
            selectedPos = 0;
        }
        fragment.activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        fragment.activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            fragment.activity.runOnUiThread(() -> {
                if (influencerServiceAdapter != null) {
                    influencerServiceAdapter.getData().get(selectedPos).isShowProgress = false;
                    influencerServiceAdapter.notifyItemChanged(selectedPos);
                }
            });
            gigId = 0;
            selectedPos = 0;
        }
    }

    @Override
    public void onClickService(SocialPlatformList.Data data, int pos) {
        gigId = data.id;
        selectedPos = pos;
        getGigDetails();
    }
}
