package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentInfAllBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class InfAllFragment  extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentInfAllBinding skillProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_inf_all, container, false);
        InfAllFragmentVM portfolioFragmentVM = new InfAllFragmentVM(Task24Application.getInstance(), skillProfileBinding, this);
        return skillProfileBinding.getRoot();
    }
}
