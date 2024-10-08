package com.nojom.client.fragment.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDescribeBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class DescribeFragment extends BaseFragment {

    private FragmentDescribeBinding describeBinding;
    private DescribeFragmentVM describeFragmentVM;

    public static DescribeFragment newInstance() {
        DescribeFragment fragment = new DescribeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (describeBinding != null) {
            return describeBinding.getRoot();
        }

        describeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_describe, container, false);
        describeFragmentVM = new DescribeFragmentVM(Task24Application.getInstance(), describeBinding, this);
        describeBinding.getRoot().setFocusableInTouchMode(true);
        describeBinding.getRoot().requestFocus();
        describeBinding.getRoot().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                describeFragmentVM.goBack();
                return true;
            }
            return false;
        });
        return describeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        describeFragmentVM.onResumeMethod();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        describeFragmentVM.onActivityResult(requestCode,resultCode,data);
    }
}
