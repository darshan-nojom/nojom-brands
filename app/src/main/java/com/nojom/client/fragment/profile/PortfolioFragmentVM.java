package com.nojom.client.fragment.profile;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.PortfolioListAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentReviewsProfileBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Portfolios;
import com.nojom.client.ui.clientprofile.FreelancerProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nojom.client.util.Constants.API_GET_PORTFOLIO;

class PortfolioFragmentVM extends AndroidViewModel implements RequestResponseListener {
    private FragmentReviewsProfileBinding binding;
    private BaseFragment fragment;
    private List<Portfolios.Data> attachmentList;

    PortfolioFragmentVM(Application application, FragmentReviewsProfileBinding reviewsProfileBinding, BaseFragment reviewsProfileFragment) {
        super(application);
        binding = reviewsProfileBinding;
        fragment = reviewsProfileFragment;
        initData();
    }

    private void initData() {

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_portfolio));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_portfolio_desc));

        binding.rvReviews.setLayoutManager(new LinearLayoutManager(fragment.activity));
        attachmentList = new ArrayList<>();
    }

    public void getMyPortfolios() {
        if (!fragment.activity.isNetworkConnected())
            return;

        binding.rvReviews.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        binding.noData.llNoData.setVisibility(View.GONE);

        attachmentList = new ArrayList<>();
        int agentId = ((FreelancerProfileActivity) fragment.activity).getAgentData().id;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PORTFOLIO, true, map);
    }

    private void setAdapter(String filePath) {
        PortfolioListAdapter portfolioFileAdapter = new PortfolioListAdapter(fragment.activity, attachmentList, filePath);
        binding.rvReviews.setAdapter(portfolioFileAdapter);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PORTFOLIO)) {
            Portfolios portfolios = Portfolios.getPortfolios(responseBody);
            if (portfolios != null && portfolios.data != null && portfolios.data.size() > 0) {
                attachmentList.addAll(portfolios.data);
                binding.noData.llNoData.setVisibility(View.GONE);
            } else {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
            }

            setAdapter(portfolios != null ? portfolios.path : "");

            binding.rvReviews.setVisibility(View.VISIBLE);
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.shimmerLayout.stopShimmer();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.noData.llNoData.setVisibility(View.VISIBLE);
        binding.shimmerLayout.setVisibility(View.GONE);
        binding.shimmerLayout.stopShimmer();
    }
}
