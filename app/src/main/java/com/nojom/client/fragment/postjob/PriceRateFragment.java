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
import com.nojom.client.databinding.FragmentSelectRateBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class PriceRateFragment extends BaseFragment {
    private FragmentSelectRateBinding selectRateBinding;
    private PriceRateFragmentVM priceRateFragmentVM;

    public static PriceRateFragment newInstance(boolean isEdit) {
        PriceRateFragment fragment = new PriceRateFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (selectRateBinding != null) {
            return selectRateBinding.getRoot();
        }

        selectRateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_rate, container, false);
        priceRateFragmentVM = new PriceRateFragmentVM(Task24Application.getInstance(), selectRateBinding, this);

        selectRateBinding.getRoot().setFocusableInTouchMode(true);
        selectRateBinding.getRoot().requestFocus();
        selectRateBinding.getRoot().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                priceRateFragmentVM.goBack();
                return true;
            }
            return false;
        });

        return selectRateBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        priceRateFragmentVM.onResumeMethod();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        priceRateFragmentVM.onActivityResult(requestCode, resultCode, data);
    }
}
