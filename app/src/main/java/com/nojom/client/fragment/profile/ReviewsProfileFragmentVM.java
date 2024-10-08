package com.nojom.client.fragment.profile;

import static com.nojom.client.util.Constants.API_GET_AGENT_REVIEW;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.adapter.ReviewsAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentReviewsProfileBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.ui.projects.InfluencerProfileActivity;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ReviewsProfileFragmentVM extends AndroidViewModel implements RequestResponseListener {
    private FragmentReviewsProfileBinding binding;
    private BaseFragment fragment;
    private List<ClientReviews.Data> reviewsList;
    private ReviewsAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int pageNo = 1;

    ReviewsProfileFragmentVM(Application application, FragmentReviewsProfileBinding reviewsProfileBinding, BaseFragment reviewsProfileFragment) {
        super(application);
        binding = reviewsProfileBinding;
        fragment = reviewsProfileFragment;
        initData();
    }

    private void initData() {

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_reviews));
        if (((InfluencerProfileActivity) fragment.activity).getAgentData().rate != null) {
            String rate = fragment.activity.get1DecimalPlaces(((InfluencerProfileActivity) fragment.activity).getAgentData().rate);
            binding.txtReviewCount.setText("(" + rate + ")");
        }
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_reviews_desc));

        reviewsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvReviews.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mAdapter != null && mAdapter.getItemCount() > 9) {
                    pageNo = page;
                    getReviews(pageNo);
                }
            }
        };

        binding.rvReviews.addOnScrollListener(scrollListener);
    }

    public void getReviews(int pageNo) {
        onPreExcecute(pageNo);
        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", ((InfluencerProfileActivity) fragment.activity).agentProfileId() + "");
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_AGENT_REVIEW, true, map);
    }

    private void setAdapter() {
        if (reviewsList != null && reviewsList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new ReviewsAdapter(fragment.activity);
            }
            mAdapter.doRefresh(reviewsList);

            if (binding.rvReviews.getAdapter() == null) {
                binding.rvReviews.setAdapter(mAdapter);
            }
            binding.shimmerLayoutReview.setVisibility(View.GONE);
            binding.shimmerLayoutReview.stopShimmer();
            binding.rvReviews.setVisibility(View.VISIBLE);
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            if (mAdapter != null) {
                mAdapter.doRefresh(reviewsList);
            }
        }
    }

    private void onPreExcecute(int pageNo) {
        if (pageNo == 1) {
            binding.shimmerLayoutReview.startShimmer();
            binding.shimmerLayoutReview.setVisibility(View.VISIBLE);
            binding.rvReviews.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_AGENT_REVIEW)) {
            ClientReviews agentReviews = ClientReviews.getClientReviews(responseBody);
            if (agentReviews != null && agentReviews.data != null) {
                reviewsList.addAll(agentReviews.data);
            }
            binding.shimmerLayoutReview.setVisibility(View.GONE);
            binding.shimmerLayoutReview.stopShimmer();
            setAdapter();
        }
        fragment.activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        fragment.activity.isClickableView = false;
        binding.shimmerLayoutReview.setVisibility(View.GONE);
        binding.shimmerLayoutReview.stopShimmer();
        setAdapter();
    }
}
