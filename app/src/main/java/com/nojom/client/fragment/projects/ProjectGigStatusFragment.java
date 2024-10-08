package com.nojom.client.fragment.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentProjectStatusBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ProjectGigStatusFragment extends BaseFragment {

    private ProjectGigStatusFragmentVM projectGigStatusFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectStatusBinding fragmentProjectStatusBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_status, container, false);
        projectGigStatusFragmentVM = new ProjectGigStatusFragmentVM(Task24Application.getInstance(), fragmentProjectStatusBinding, this);
        return fragmentProjectStatusBinding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        projectGigStatusFragmentVM.onPauseMethod();
    }
}
