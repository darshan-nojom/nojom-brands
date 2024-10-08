package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.ReviewsAdapter;
import com.nojom.client.adapter.VerifiedAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityPublicProfileBinding;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.model.Profile;
import com.nojom.client.model.TrustPoint;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.nojom.client.util.Constants.API_CLIENT_FEEDBACK_REVIEW;

class PublicProfileActivityVM extends AndroidViewModel implements RequestResponseListener {
    private ActivityPublicProfileBinding binding;
    private BaseActivity activity;
    private Profile clientData;
    private List<ClientReviews.Data> reviewsList;
    private ReviewsAdapter mAdapter;
    private VerifiedAdapter mVerifiedAdapter;

    PublicProfileActivityVM(Application application, ActivityPublicProfileBinding publicProfileBinding, BaseActivity publicProfileActivity) {
        super(application);
        binding = publicProfileBinding;
        activity = publicProfileActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(v -> activity.onBackPressed());
        binding.noData.tvNoTitle.setText(activity.getString(R.string.no_reviews));
        binding.noData.tvNoDescription.setText(activity.getString(R.string.no_reviews_desc));

        reviewsList = new ArrayList<>();
        clientData = Preferences.getProfileData(activity);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvVerified.setLayoutManager(new GridLayoutManager(activity, 2));

        setUi();

    }

    public void getReviews() {
        activity.disableEnableTouch(true);
        activity.runOnUiThread(() -> {
            binding.shimmerLayout.startShimmer();
            binding.shimmerLayout.setVisibility(View.VISIBLE);
        });

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_CLIENT_FEEDBACK_REVIEW, false, null);
    }

    private void setUi() {
        getReviews();
        if (clientData != null) {

            if (clientData.username != null) {
                binding.tvUserName.setText(clientData.username);
            } else if (clientData.firstName != null && clientData.lastName != null) {
                binding.tvUserName.setText(String.format(Locale.US, "%s %s", clientData.firstName, clientData.lastName));
            } else if (clientData.firstName != null) {
                binding.tvUserName.setText(clientData.firstName);
            } else if (clientData.lastName != null) {
                binding.tvUserName.setText(clientData.lastName);
            }

            try {
                if (clientData.rate != null) {
                    String rate = activity.get1DecimalPlaces(clientData.rate);
                    binding.tvReviews.setText(String.format(Locale.US, "(%s)", rate));
                    binding.ratingbar.setRating(Float.parseFloat(rate));
                }
            } catch (NumberFormatException e) {
                binding.ratingbar.setRating(0);
                e.printStackTrace();
            }

            if (clientData != null) {

                activity.setImage(binding.imgProfile, TextUtils.isEmpty(clientData.profilePic) ? "" : clientData.filePath.pathProfilePicClient + clientData.profilePic, 0, 0);

                ArrayList<Profile.VerifiedWith> verifiedList = new ArrayList<>();
                if (clientData.trustRate != null) {
                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.email_address), clientData.trustRate.email));
//                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.facebook), clientData.trustRate.facebook));
//                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.payment_1), clientData.trustRate.payment));
                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.phonenumber), clientData.trustRate.phoneNumber));
//                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.government_id), clientData.trustRate.verifyId));
                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.cr_number), clientData.trustRate.cr_id));
                    verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.vat_number), 0));
                }
                setVerifiedAdapter(verifiedList);
            }
        }
    }

    private void setReviewsAdapter() {
        if (reviewsList != null && reviewsList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new ReviewsAdapter(activity);
            }
            mAdapter.doRefresh(reviewsList);
            if (binding.rvReviews.getAdapter() == null) {
                binding.rvReviews.setAdapter(mAdapter);
            }
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            if (mAdapter != null) {
                mAdapter.doRefresh(reviewsList);
            }
        }
    }

    private void setVerifiedAdapter(ArrayList<Profile.VerifiedWith> verifiedList) {
        if (verifiedList != null && verifiedList.size() > 0) {
            binding.tvNoVerified.setVisibility(View.GONE);
            if (mVerifiedAdapter == null) {
                mVerifiedAdapter = new VerifiedAdapter();
            }
            mVerifiedAdapter.doRefresh(verifiedList);

            if (binding.rvVerified.getAdapter() == null) {
                binding.rvVerified.setAdapter(mVerifiedAdapter);
            }
        } else {
            binding.tvNoVerified.setVisibility(View.VISIBLE);
            if (mVerifiedAdapter != null) {
                mVerifiedAdapter.doRefresh(verifiedList);
            }
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.disableEnableTouch(false);
        ClientReviews clientReviews = ClientReviews.getClientReviews(responseBody);
        if (clientReviews != null && clientReviews.data != null) {
            reviewsList = clientReviews.data;
        }
        setReviewsAdapter();
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.disableEnableTouch(false);
        setReviewsAdapter();
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
    }
}
