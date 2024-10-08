package com.nojom.client.ui.clientprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.textview.CustomTextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityDepositFundsCopyBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class DepositFundsActivity extends BaseActivity {

    public static Activity depositActivity;
    public static String paymentAccount;
    public static String paymentAccountId, paypalAccountToken, paypalAccountEmail;
    public static String cardNumber;
    public static String cardExp;
    public static ArrayList<BraintreeCard.Data> paypalAccountList;
    public String appliedPromoCode = null;
    public String braintreeToken;
    /*New Update for Payment*/
    public double actual_Amount, redeem_Amount, promo_Amount;
    private DepositFundsActivityVM depositFundsActivityVM;

    public double getActual_Amount() {
        return actual_Amount;
    }

    public void setActual_Amount(double actual_Amount) {
        this.actual_Amount = actual_Amount;
    }

    public double getRedeem_Amount() {
        return redeem_Amount;
    }

    public void setRedeem_Amount(double redeem_Amount) {
        this.redeem_Amount = redeem_Amount;
    }

    public double getPromo_Amount() {
        return promo_Amount;
    }

    public void setPromo_Amount(double promo_Amount) {
        this.promo_Amount = promo_Amount;
    }

    public double getRemaining_Balance() {
        return depositFundsActivityVM.getRemainingBalance();
    }

    public void setRemaining_Balance(double remaining_Balance) {
        depositFundsActivityVM.setRemainingBalance(remaining_Balance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositActivity = this;
        ActivityDepositFundsCopyBinding depositFundsBinding = DataBindingUtil.setContentView(this, R.layout.activity_deposit_funds_copy);
        depositFundsActivityVM = new DepositFundsActivityVM(Task24Application.getInstance(), depositFundsBinding, this);

    }

    public boolean isFromGig() {
        return depositFundsActivityVM.isFromGig;
    }

    @Override
    protected void onResume() {
        super.onResume();
        depositFundsActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        depositFundsActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    public double getTotalPrice() {
        return depositFundsActivityVM.getTotalPrice();
    }

    public double getTotalPriceWithDeposit() {
        return depositFundsActivityVM.getTotalPriceWithDeposit();
    }

    public String getSignUpRefCodeClickOnLink() {
        return depositFundsActivityVM.getSignUpRefCodeClickOnLink();
    }

    public void checkPromoCodeApi(String promoCode, int fragment) {
        depositFundsActivityVM.checkPromoCodeApi(promoCode, fragment);
    }

    public ProjectByID getProjectData() {
        return depositFundsActivityVM.getProjectData();
    }


    public ExpertGigDetail getGigData() {
        return depositFundsActivityVM.getGigData();
    }

    public double getFees() {
        return depositFundsActivityVM.getFees();
    }

    public int getSelectedPackagePos() {
        return depositFundsActivityVM.selectedPackagePosition;
    }

    public double getUserActualBalance() {
        return depositFundsActivityVM.getUserActualBalance();
    }

    public void enterPromoCodeDialog(int fragment, String appliedCode) {
        depositFundsActivityVM.enterPromoCodeDialog(fragment, appliedCode);
    }

    public double getTotalPriceForCheckReedim() {
        return depositFundsActivityVM.getTotalPriceForCheckReedim();
    }

    public CustomTextView getTvPay() {
        return depositFundsActivityVM.getTvPay();
    }

    public CircularProgressBar getProgressbar() {
        return depositFundsActivityVM.getProgressbar();
    }

    public void setTotalAmount(String amount) {
        depositFundsActivityVM.setTotalAmount(amount);
    }

    public Integer getSelectedTab() {
        return depositFundsActivityVM.isPaypal;
    }
}
