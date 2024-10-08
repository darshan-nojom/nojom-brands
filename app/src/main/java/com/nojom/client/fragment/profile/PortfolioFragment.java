package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentReviewsProfileBinding;
import com.nojom.client.fragment.BaseFragment;


public class PortfolioFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentReviewsProfileBinding reviewsProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews_profile, container, false);
        PortfolioFragmentVM portfolioFragmentVM = new PortfolioFragmentVM(Task24Application.getInstance(), reviewsProfileBinding, this);
        portfolioFragmentVM.getMyPortfolios();
        return reviewsProfileBinding.getRoot();
    }
}
