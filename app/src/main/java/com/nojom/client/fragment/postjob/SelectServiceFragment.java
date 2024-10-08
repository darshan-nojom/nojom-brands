package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentSelectServiceBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class SelectServiceFragment extends BaseFragment {

    private FragmentSelectServiceBinding selectServiceBinding;
    private SelectServiceFragmentVM selectServiceFragmentVM;

    public static SelectServiceFragment newInstance() {
        SelectServiceFragment fragment = new SelectServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (selectServiceBinding != null) {
            return selectServiceBinding.getRoot();
        }

        selectServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_service, container, false);
        selectServiceFragmentVM = new SelectServiceFragmentVM(Task24Application.getInstance(), selectServiceBinding, this);
        return selectServiceBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectServiceFragmentVM.onResumeMethod();
    }
}
