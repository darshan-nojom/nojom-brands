package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentAboutProfileBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class AboutProfileFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAboutProfileBinding aboutProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_profile, container, false);
        new AboutProfileFragmentVM(Task24Application.getInstance(), aboutProfileBinding, this);
        return aboutProfileBinding.getRoot();
    }
}
