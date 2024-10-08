package com.nojom.client.fragment.profile;

import static com.nojom.client.util.Constants.API_GET_AGENCY;
import static com.nojom.client.util.Constants.API_GET_AGENT_REVIEW;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_SOCIAL_PLATFORM_LIST;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.adapter.InfluencerServiceAdapter;
import com.nojom.client.adapter.ReviewsAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentInfAllBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.AgencyList;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.home.GigDetailActivity;
import com.nojom.client.ui.projects.HireDescribeActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfAllFragmentVM extends AndroidViewModel implements RequestResponseListener, InfluencerServiceAdapter.OnClickService {

    FragmentInfAllBinding binding;
    BaseFragment fragment;
    private List<ClientReviews.Data> reviewsList;
    private List<SocialPlatformList.Data> socialListPage;
    private List<SocialPlatformList.Data> socialPlatformList;
    private int gigId = 0, selectedPos;

    InfAllFragmentVM(Application application, FragmentInfAllBinding reviewsProfileBinding, BaseFragment reviewsProfileFragment) {
        super(application);
        binding = reviewsProfileBinding;
        fragment = reviewsProfileFragment;
        reviewsList = new ArrayList<>();
        socialPlatformList = new ArrayList<>();
        socialListPage = new ArrayList<>();
        initData();
    }

    private void initData() {
        fragment.activity.runOnUiThread(() -> {
            getReviews(1);
            getSocialPlatforms();
            getAgency();
            if (((InfluencerProfileActivity) fragment.activity).getAgentData().rate != null) {
                String rate = fragment.activity.get1DecimalPlaces(((InfluencerProfileActivity) fragment.activity).getAgentData().rate);
                binding.txtReviewCount.setText("(" + rate + ")");
            }
            if (((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency != null && ((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.size() > 0) {
                binding.tvAgencyName.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).name);
                binding.tvAgencyContact.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).phone);
                binding.tvAgencyWebsite.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).website);
            }
        });

        setServicesAdapter();

        binding.relSendOffer.setOnClickListener(v -> {
            Intent i = new Intent(fragment.activity, HireDescribeActivity.class);
            i.putExtra(Constants.AGENT_PROFILE_DATA, ((InfluencerProfileActivity) fragment.activity).getAgentData());
            fragment.activity.startActivity(i);
        });

        binding.tvLinkedinView.setOnClickListener(v -> ((InfluencerProfileActivity) fragment.activity).setTab(3));

        binding.rlAgencyView.setOnClickListener(v -> ((InfluencerProfileActivity) fragment.activity).setTab(2));
        binding.rlBestView.setOnClickListener(v -> ((InfluencerProfileActivity) fragment.activity).setTab(1));
    }

    public void getReviews(int pageNo) {
        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", ((InfluencerProfileActivity) fragment.activity).agentProfileId() + "");
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_AGENT_REVIEW, true, map);
    }

    public void getSocialPlatforms() {
        fragment.activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_SOCIAL_PLATFORM_LIST +
                /*"456696"*/((InfluencerProfileActivity) fragment.activity).agentProfileId(), false, null);
        //((InfluencerProfileActivity) fragment.activity).getUserID()
    }

    public void getAgency() {
        fragment.activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_AGENCY +
                /*"456696"*/((InfluencerProfileActivity) fragment.activity).agentProfileId(), false, null);
        //((InfluencerProfileActivity) fragment.activity).getUserID()
    }

    private void getGigDetails() {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();

        apiRequest.apiRequest(this, fragment.activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
    }

    private void setReviewAdapter() {
        if (reviewsList != null && reviewsList.size() > 0) {
            ReviewsAdapter mAdapter = new ReviewsAdapter(fragment.activity);
            mAdapter.doRefresh(reviewsList);
            binding.rvLinkedin.setAdapter(mAdapter);
            binding.rvLinkedin.setVisibility(View.VISIBLE);
            binding.tvPhReview.setVisibility(View.GONE);
        } else {
            binding.tvPhReview.setVisibility(View.VISIBLE);
            binding.rvLinkedin.setVisibility(View.GONE);
        }
    }

    InfluencerServiceAdapter influencerServiceAdapter;

    private void setServicesAdapter() {
        if (socialListPage != null && socialListPage.size() > 0) {
            influencerServiceAdapter = new InfluencerServiceAdapter(fragment.activity, socialListPage, InfAllFragmentVM.this);
            binding.rvServices.setAdapter(influencerServiceAdapter);
            binding.rvServices.setVisibility(View.VISIBLE);
            binding.tvPhService.setVisibility(View.GONE);
        } else {
            binding.rvServices.setVisibility(View.GONE);
            binding.tvPhService.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_AGENT_REVIEW)) {
            ClientReviews agentReviews = ClientReviews.getClientReviews(responseBody);
            if (agentReviews != null && agentReviews.data != null) {
                reviewsList.add(agentReviews.data.get(0));
                reviewsList.add(agentReviews.data.get(1));
            }
//            binding.shimmerLayoutReview.setVisibility(View.GONE);
//            binding.shimmerLayoutReview.stopShimmer();
            setReviewAdapter();
        } else if (url.equalsIgnoreCase(API_GET_AGENCY + /*"456696"*/((InfluencerProfileActivity) fragment.activity).agentProfileId())) {

            AgencyList socialList = AgencyList.getAgencyList(responseBody);
            ((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency = socialList.data;

            if (((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency != null
                    && ((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.size() > 0) {
                binding.tvAgencyName.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).name);
                binding.tvAgencyContact.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).phone);
                binding.tvAgencyWebsite.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).website);
            }
        } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
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
        } else {
            SocialPlatformList socialList = SocialPlatformList.getSocialPlatforms(responseBody);
            socialPlatformList = socialList.data;
            ((InfluencerProfileActivity) fragment.activity).setServiceList(socialPlatformList);
            if (socialList.data.size() > 0) {
                socialListPage.add(socialList.data.get(0));
            }
            if (socialList.data.size() > 1) {
                socialListPage.add(socialList.data.get(1));
            }
            setServicesAdapter();
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
//        binding.shimmerLayoutReview.setVisibility(View.GONE);
//        binding.shimmerLayoutReview.stopShimmer();
        setReviewAdapter();
        setServicesAdapter();
    }

    @Override
    public void onClickService(SocialPlatformList.Data data, int pos) {
        gigId = data.id;
        selectedPos = pos;
        getGigDetails();
    }
}
