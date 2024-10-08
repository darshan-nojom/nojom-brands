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
import com.nojom.client.databinding.FragmentProjectDetailsBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ProjectDetailsFragment extends BaseFragment {

    private ProjectDetailsFragmentVM projectDetailsFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectDetailsBinding projectDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        projectDetailsFragmentVM = new ProjectDetailsFragmentVM(Task24Application.getInstance(), projectDetailsBinding, this);
        return projectDetailsBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        projectDetailsFragmentVM.onActivityResult(requestCode, resultCode, data);
    }
}
