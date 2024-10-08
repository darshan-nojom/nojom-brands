package com.nojom.client.ui.clientprofile;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_PROFILE_VERIFICATIONS;
import static com.nojom.client.util.Constants.API_VERIFY_PAYPAL_PAYMENT;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityVerifyPaymentBinding;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.ui.BaseActivity;

import java.util.HashMap;

public class VerifyPaymentActivityVM extends AndroidViewModel implements View.OnClickListener,
        RequestResponseListener/*, PaymentMethodNonceCreatedListener*/, DropInListener {
    private final int REQ_PAYMENT_CODE = 9001;
    private ActivityVerifyPaymentBinding binding;
    private BaseActivity activity;
    private String generatedNonce;

    VerifyPaymentActivityVM(Application application, ActivityVerifyPaymentBinding verifyPaymentBinding, BaseActivity verifyPaymentActivity) {
        super(application);
        binding = verifyPaymentBinding;
        activity = verifyPaymentActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.rlPaypal.setOnClickListener(this);
        binding.rlVisa.setOnClickListener(this);
        getPaymentMethod();
    }


    private void getPaymentMethod() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }


    private void verifyPayment() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "3");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_PROFILE_VERIFICATIONS, true, map);
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
            case R.id.rl_visa:
                break;
        }

    }

    private void verifyPaypal() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("nonce", generatedNonce);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_VERIFY_PAYPAL_PAYMENT, true, map);
    }


    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_PAYMENT_CODE) {
            verifyPayment();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_PROFILE_VERIFICATIONS)) {
            activity.setResult(RESULT_OK);
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                Log.e("TOKEN  ", "" + braintreeToken.token);
                generateNonce(braintreeToken.token);
            }
        } else if (url.equalsIgnoreCase(API_VERIFY_PAYPAL_PAYMENT)) {
            activity.setResult(RESULT_OK);
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            activity.isClickableView = false;
            PaymentMethods paymentMethods = PaymentMethods.gePaymentMethodInfo(responseBody);
            try {
                if (paymentMethods.paymentMethod.size() > 0) {
                    for (int i = 0; i < paymentMethods.paymentMethod.size(); i++) {
                        if (paymentMethods.paymentMethod.get(i).name.equals("PayPal")) {
                            if (paymentMethods.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.rlPaypal.setVisibility(View.VISIBLE);
                            } else {
                                binding.rlPaypal.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }

    public void generateBrantreeToken() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);
    }

    public void generateNonce(String token) {

        // Init drop in client
        DropInRequest dropInRequest = new DropInRequest();
        DropInClient dropInClient = new DropInClient(activity, token);
        dropInClient.setListener(this);
        dropInClient.launchDropIn(dropInRequest);

        /*BraintreeFragment mBraintreeFragment;
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(activity, token);

            mBraintreeFragment.addListener(VerifyPaymentActivityVM.this);

            PayPalRequest request = new PayPalRequest("0.50")
                    .localeCode("US")
                    .billingAgreementDescription(activity.getString(R.string.client_paypal_verification));

            PayPal.requestOneTimePayment(mBraintreeFragment, request);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }*/
    }

    /*@Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        generatedNonce = paymentMethodNonce.getNonce();
        verifyPaypal();
    }*/

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        if (dropInResult.getPaymentMethodNonce() != null) {
            generatedNonce = dropInResult.getPaymentMethodNonce().getString();
            verifyPaypal();
        }
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {

    }
}
