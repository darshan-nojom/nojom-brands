package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentInfAllBinding;
import com.nojom.client.databinding.FragmentInfServicesBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class InfServicesFragment extends BaseFragment {
    InfServicesFragmentVM portfolioFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentInfServicesBinding skillProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_inf_services, container, false);
        portfolioFragmentVM = new InfServicesFragmentVM(Task24Application.getInstance(), skillProfileBinding, this);
        return skillProfileBinding.getRoot();
    }

    public void setList() {
        portfolioFragmentVM.setServicesAdapter();
    }

}
