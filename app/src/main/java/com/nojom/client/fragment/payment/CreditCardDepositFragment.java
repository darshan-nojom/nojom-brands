package com.nojom.client.fragment.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDepositFundsBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class CreditCardDepositFragment extends BaseFragment {

    private CreditCardDepositFragmentVM creditCardDepositFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDepositFundsBinding depositFundsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_deposit_funds, container, false);
        creditCardDepositFragmentVM = new CreditCardDepositFragmentVM(Task24Application.getInstance(), depositFundsBinding, this);
        return depositFundsBinding.getRoot();
    }

    public void updateAccountUI() {
        creditCardDepositFragmentVM.onUpdateUIMethod();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        creditCardDepositFragmentVM.onActivityResult(requestCode, resultCode, data);
    }

    public void updateUI() {
        creditCardDepositFragmentVM.updateUI();
    }

    public void setPromoCode(String promoCode, double discountValue, double totalAmt) {
        creditCardDepositFragmentVM.setPromoCode(promoCode, discountValue, totalAmt);
    }

    public void onClickCardPay( ) {
        creditCardDepositFragmentVM.onClickCardPay();
    }

    public void onClickPaypalPay( ) {
        creditCardDepositFragmentVM.onClickPaypalPay();
    }

    public void onClickGooglePay() {
        creditCardDepositFragmentVM.onClickGooglePay();
    }

    public void onClickVenmoPay() {
        creditCardDepositFragmentVM.onClickVenmoPay();
    }
    public void onClickBankTransferPay() {
        creditCardDepositFragmentVM.onClickBankTransferPay();
    }

    public void updatePaypalAccountList() {
        creditCardDepositFragmentVM.updatePaypalAccountListUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        creditCardDepositFragmentVM.onResumeMethod();
    }

    public void hideCardUI() {
        creditCardDepositFragmentVM.hideCardUI();
    }

    public void hideBankCardUI() {
        creditCardDepositFragmentVM.hideBankCardUI();
    }

    public void googlePayVisibility() {
        creditCardDepositFragmentVM.checkGooglePayVisibility();
    }
}
