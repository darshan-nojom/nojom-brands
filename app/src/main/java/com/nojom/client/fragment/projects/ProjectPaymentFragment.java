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
import com.nojom.client.databinding.FragmentProjectPaymentNewBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ProjectPaymentFragment extends BaseFragment {

    private ProjectPaymentFragmentVM projectPaymentFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProjectPaymentNewBinding projectPaymentNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_payment_new, container, false);
        projectPaymentFragmentVM = new ProjectPaymentFragmentVM(Task24Application.getInstance(), projectPaymentNewBinding, this);
        return projectPaymentNewBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        projectPaymentFragmentVM.onActivityResult(requestCode, resultCode, data);
    }
}
