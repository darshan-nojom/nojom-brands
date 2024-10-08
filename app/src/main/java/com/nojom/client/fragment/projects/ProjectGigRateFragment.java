package com.nojom.client.fragment.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentProjectRateBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ProjectGigRateFragment extends BaseFragment {

    private ProjectGigRateFragmentVM projectGigRateFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectRateBinding projectRateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_rate, container, false);
        projectGigRateFragmentVM = new ProjectGigRateFragmentVM(Task24Application.getInstance(), projectRateBinding, this);
        return projectRateBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        projectGigRateFragmentVM.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshPage() {
        projectGigRateFragmentVM.refreshPage();
    }

}
