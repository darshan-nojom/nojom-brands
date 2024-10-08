package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentPayTypeBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class PayTypeFragment extends BaseFragment {

    private PayTypeFragmentVM payTypeFragmentVM;

    public static PayTypeFragment newInstance() {
        PayTypeFragment fragment = new PayTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPayTypeBinding payTypeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_type, container, false);
        payTypeFragmentVM = new PayTypeFragmentVM(Task24Application.getInstance(), payTypeBinding, this);
        return payTypeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        payTypeFragmentVM.onResumeMethod();
    }
}