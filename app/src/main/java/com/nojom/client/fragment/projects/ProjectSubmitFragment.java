package com.nojom.client.fragment.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentProjectSubmitBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ProjectSubmitFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectSubmitBinding projectSubmitBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_submit, container, false);
        new ProjectSubmitFragmentVM(Task24Application.getInstance(), projectSubmitBinding, this);
        return projectSubmitBinding.getRoot();
    }
}
