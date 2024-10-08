package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentReviewsProfileBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ReviewsProfileFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentReviewsProfileBinding reviewsProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews_profile, container, false);
        ReviewsProfileFragmentVM reviewsProfileFragmentVM = new ReviewsProfileFragmentVM(Task24Application.getInstance(), reviewsProfileBinding, this);
        activity.runOnUiThread(() -> reviewsProfileFragmentVM.getReviews(1));
        return reviewsProfileBinding.getRoot();
    }
}
