package com.nojom.client.fragment.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentProjectsListBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class CampaignListFragment extends BaseFragment {

    private CampaignListFragmentVM projectsListFragmentVM;

    public static CampaignListFragment newInstance(int isWorkInProgress) {
        CampaignListFragment fragment = new CampaignListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.IS_WORK_INPROGRESS, isWorkInProgress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectsListBinding projectsListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects_list, container, false);
        projectsListFragmentVM = new CampaignListFragmentVM(Task24Application.getInstance(), projectsListBinding, this);
        return projectsListBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        projectsListFragmentVM.onResumeMethod();
    }

    @Override
    public void onPause() {
        super.onPause();
        projectsListFragmentVM.onPauseMethod();
    }
}
