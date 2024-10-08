package com.nojom.client.ui.balance;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_VERIFY_PAYPAL_PAYMENT;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityChoosePaymentMethodBinding;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.addcard.AddCardActivity;

import java.util.HashMap;

public class ChoosePaymentMethodActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, DropInListener/*, PaymentMethodNonceCreatedListener*/ {
    private ActivityChoosePaymentMethodBinding binding;
    private BaseActivity activity;
    private boolean isRedirectFromDeposite;
    private String generatedNonce;

    ChoosePaymentMethodActivityVM(Application application, ActivityChoosePaymentMethodBinding paymentMethodBinding, BaseActivity choosePaymentMethodActivity) {
        super(application);
        binding = paymentMethodBinding;
        activity = choosePaymentMethodActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.rlPaypal.setOnClickListener(this);
        binding.rlPaynoeer.setOnClickListener(this);
        binding.rlBankCard.setOnClickListener(this);
        binding.rlCard.setOnClickListener(this);
        if (activity.getIntent() != null) {
            isRedirectFromDeposite = activity.getIntent().getBooleanExtra("isFromDeposite", false);
        }
        getPaymentMethod();
    }


    private void getPaymentMethod() {
        if (!activity.isNetworkConnected()) return;

        activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.rl_paypal:
                generateBrantreeToken();
                break;
            case R.id.rl_paynoeer:
            case R.id.rl_bank_card:
                break;
            case R.id.rl_card:
                Intent intent1 = new Intent(activity, AddCardActivity.class);
                activity.startActivityForResult(intent1, 123);
                break;
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            activity.setResult(RESULT_OK);
            activity.finish();
        }
    }

    public void generateBrantreeToken() {
        showProgress();

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                generateNonce(braintreeToken.token);
            }
        } else if (url.equalsIgnoreCase(API_VERIFY_PAYPAL_PAYMENT)) {
            hideProgress();
            activity.setResult(RESULT_OK);
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            activity.isClickableView = false;
            PaymentMethods paymentMethods = PaymentMethods.gePaymentMethodInfo(responseBody);
            try {
                if (paymentMethods.paymentMethod.size() > 0) {
                    try {
                        paymentMethods.paymentMethod.size();
                        for (int i = 0; i < paymentMethods.paymentMethod.size(); i++) {
                            switch (paymentMethods.paymentMethod.get(i).name) {
                                case "Bank Card":
                                    if (paymentMethods.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                        binding.rlCard.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.rlCard.setVisibility(View.GONE);
                                    }
                                    Task24Application.getInstance().paymentCardType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                    break;
                                case "PayPal":
                                    if (paymentMethods.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                        binding.rlPaypal.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.rlPaypal.setVisibility(View.GONE);
                                    }
                                    Task24Application.getInstance().paymentPayPalType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void generateNonce(String token) {
        /*BraintreeFragment mBraintreeFragment;
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(activity, token);

            mBraintreeFragment.addListener(ChoosePaymentMethodActivityVM.this);

            PayPalRequest request = new PayPalRequest()
                    .localeCode("US")
                    .billingAgreementDescription("Test");

            PayPal.requestBillingAgreement(mBraintreeFragment, request);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }*/

        DropInRequest dropInRequest = new DropInRequest();
        DropInClient dropInClient = new DropInClient(activity, token);
        dropInClient.setListener(this);
        dropInClient.launchDropIn(dropInRequest);

        hideProgress();

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        hideProgress();
    }

    /*@Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        generatedNonce = paymentMethodNonce.getNonce();
        verifyPaypal();
    }*/

    private void verifyPaypal() {
        if (!activity.isNetworkConnected()) return;

        showProgress();

        HashMap<String, String> map = new HashMap<>();
        map.put("nonce", generatedNonce);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_VERIFY_PAYPAL_PAYMENT, true, map);
    }

    private void showProgress() {
        activity.isClickableView = true;
        binding.rlPaypal.setBackgroundResource(R.drawable.transp_rounded_corner_10);
        binding.progressBarPaypal.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        activity.isClickableView = false;
        binding.rlPaypal.setBackgroundResource(R.drawable.white_rounded_corner_10);
        binding.progressBarPaypal.setVisibility(View.GONE);
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        if (dropInResult.getPaymentMethodNonce() != null) {
            generatedNonce = dropInResult.getPaymentMethodNonce().getString();
            verifyPaypal();
        }
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {
        hideProgress();
    }
}
