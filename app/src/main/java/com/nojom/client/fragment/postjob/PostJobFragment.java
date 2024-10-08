package com.nojom.client.fragment.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentPostJobBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class PostJobFragment extends BaseFragment {

    private PostJobFragmentVM postJobFragmentVM;
    private FragmentPostJobBinding postJobBinding;

    public static PostJobFragment newInstance(boolean isEdit, boolean isDuplicate, boolean isRepost, int projectId, ProjectByID projectData) {
        PostJobFragment fragment = new PostJobFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_EDIT, isEdit);
        args.putBoolean(Constants.DUPLICATE_PROJECT, isDuplicate);
        args.putBoolean(Constants.REPOST_PROJECT, isRepost);
        args.putInt(Constants.PROJECT_ID, projectId);
        args.putSerializable(Constants.PROJECT, projectData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (postJobBinding != null) {
            return postJobBinding.getRoot();
        }
        postJobBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_job, container, false);
        postJobFragmentVM = new PostJobFragmentVM(Task24Application.getInstance(), postJobBinding, this);

        return postJobBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        postJobFragmentVM.onResumeMethod();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        postJobFragmentVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        postJobFragmentVM.onStopMethod();
        super.onStop();
    }
}
