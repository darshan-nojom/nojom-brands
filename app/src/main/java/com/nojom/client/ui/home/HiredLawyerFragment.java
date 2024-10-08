package com.nojom.client.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentAllPopularLawyerBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class HiredLawyerFragment extends BaseFragment {
    private HiredLawyerFragmentVM hiredLawyerFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAllPopularLawyerBinding allPopularLawyerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_popular_lawyer, container, false);
        hiredLawyerFragmentVM = new HiredLawyerFragmentVM(Task24Application.getInstance(), allPopularLawyerBinding, this);
        return allPopularLawyerBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void unSelectAll() {
        if (hiredLawyerFragmentVM != null) {
            hiredLawyerFragmentVM.refreshAdapter();
        }
    }

    public void getExperts(boolean isClearAll, int serviceCatId) {
        hiredLawyerFragmentVM.getExperts(isClearAll, serviceCatId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && hiredLawyerFragmentVM != null) {
            hiredLawyerFragmentVM.initData(true);
        }
    }
}
