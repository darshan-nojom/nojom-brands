package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentEnterRateBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class EnterRateFragment extends BaseFragment {

    private FragmentEnterRateBinding enterRateBinding;
    private EnterRateFragmentVM enterRateFragmentVM;

    public static EnterRateFragment newInstance(boolean isEdit) {
        EnterRateFragment fragment = new EnterRateFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (enterRateBinding != null) {
            return enterRateBinding.getRoot();
        }

        enterRateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_rate, container, false);
        enterRateFragmentVM = new EnterRateFragmentVM(Task24Application.getInstance(), enterRateBinding, this);

        return enterRateBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        enterRateFragmentVM.onResumeMethod();
    }
}
