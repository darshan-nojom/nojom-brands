package com.nojom.client.ui.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentBalancePaymentBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class PaymentFragment extends BaseFragment {

    private PaymentFragmentVM paymentFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBalancePaymentBinding balancePaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance_payment, container, false);
        paymentFragmentVM = new PaymentFragmentVM(Task24Application.getInstance(), balancePaymentBinding, this);
        return balancePaymentBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        paymentFragmentVM.onActivityResult(requestCode, resultCode, data);
    }

    void updateUi() {
        if (paymentFragmentVM != null) {
            paymentFragmentVM.updateUi();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        paymentFragmentVM.onResumeMethod();
    }
}
