package com.nojom.client.ui.balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentBalanceDepositBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class DepositFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBalanceDepositBinding balanceDepositBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance_deposit, container, false);
        new DepositFragmentVM(Task24Application.getInstance(), balanceDepositBinding, this);
        return balanceDepositBinding.getRoot();
    }
}
