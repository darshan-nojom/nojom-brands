package com.nojom.client.ui.balance;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_ADD_BALANCE;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.nojom.client.BuildConfig;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityWithdrawMoneyBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.RateClickListener;
import com.nojom.client.util.Constants;
import com.nojom.client.util.PaymentsUtil;
import com.nojom.client.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class WithdrawMoneyActivityVM extends AndroidViewModel implements RateClickListener, View.OnClickListener/*, PaymentMethodNonceCreatedListener*/, RequestResponseListener {
    private final ActivityWithdrawMoneyBinding binding;
    private final BaseActivity activity;
    private final int REQ_ACCOUNT_ID = 101;
    private double withdrawBalance;
    private double availableBalance;
    private String accountId;
    private PaymentsClient mPaymentsClient;
    private boolean isSelectGooglePay = false, isSelectVenmo = false;
    private String bToken;
    private String selectedCardToken;
    private BraintreeCard.Data selectedPaymentData;
    private boolean isNeedToVisibleGooglePay = false;
    private PaymentBraintreeCards cardModel;

    WithdrawMoneyActivityVM(Application application, ActivityWithdrawMoneyBinding withdrawMoneyBinding, BaseActivity withdrawMoneyActivity) {
        super(application);
        binding = withdrawMoneyBinding;
        activity = withdrawMoneyActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.rlPaypal.setOnClickListener(this);
        binding.btnWithdraw.setOnClickListener(this);
        binding.linFrom.setOnClickListener(this);

        if (activity.getIntent() != null) {
            withdrawBalance = activity.getIntent().getDoubleExtra(Constants.WITHDRAW_AMOUNT, 0);
            availableBalance = activity.getIntent().getDoubleExtra(Constants.AVAILABLE_BALANCE, 0);
        }
        if (activity.getCurrency().equals("SAR")) {
            binding.tvBalance.setText(Utils.currencyFormat(withdrawBalance).replace("SAR", ""));
            binding.tvRemainingBalance.setText(String.format(Locale.US, activity.getString(R.string.remaining_balance_0_00_sar) + " %s", Utils.currencyFormat(availableBalance - withdrawBalance)));
            binding.tvAvailableBalance.setText(String.format(Locale.US, activity.getString(R.string.s_sar), Utils.currencyFormat(availableBalance)));
        } else {
            binding.tvBalance.setText(Utils.currencyFormat(withdrawBalance).replace(activity.getString(R.string.dollar), ""));
            binding.tvRemainingBalance.setText(String.format(Locale.US, activity.getString(R.string.remaining_balance_0_00) + " %s", Utils.currencyFormat(availableBalance - withdrawBalance)));
            binding.tvAvailableBalance.setText(String.format(Locale.US, activity.getString(R.string.dollar)+"%s", Utils.currencyFormat(availableBalance)));
        }

        //initialize google pay payment client
        mPaymentsClient = Wallet.getPaymentsClient(activity,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(BuildConfig.DEBUG ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION)
                        .build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            possiblyShowGooglePayButton();
        }
        getBraintreeAccounts();
        generateBraintreeToken();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.lin_from:
                Intent i = new Intent(activity, ChooseAccountActivity.class);
                i.putExtra(Constants.ACCOUNT_ID, accountId);
                if (cardModel != null) {
                    i.putExtra(Constants.ACCOUNT_DATA, cardModel);
                }
                i.putExtra("googlepay", isNeedToVisibleGooglePay);
                activity.startActivityForResult(i, REQ_ACCOUNT_ID);
                break;
            case R.id.btn_withdraw:
                if (!TextUtils.isEmpty(accountId)) {
                    if (selectedPaymentData != null && selectedPaymentData.paypal == null) {//using card
                        doPaymentWithBraintree("", 8);
                    } else {//using PayPal
                        doPaymentWithBraintree("", 9);
                    }
                } else if (isSelectGooglePay) {
                    requestPayment();
                } else if (isSelectVenmo) {
                   /* BraintreeFragment mBraintreeFragment;
                    try {
                        mBraintreeFragment = BraintreeFragment.newInstance(activity, bToken);
                        mBraintreeFragment.addListener(this);
                        mBraintreeFragment.addListener(this);
                        Venmo.authorizeAccount(mBraintreeFragment, false);
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    }*/
                } else {
                    activity.toastMessage(activity.getString(R.string.please_select_your_account_first));
                }
                break;
        }

    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_ACCOUNT_ID) {
            if (data != null) {
                selectedPaymentData = (BraintreeCard.Data) data.getSerializableExtra(Constants.ACCOUNT_DATA);
                if (selectedPaymentData != null) {
                    isSelectGooglePay = false;
                    isSelectVenmo = false;
                    if (selectedPaymentData.paypal != null && selectedPaymentData.paypal.provider != null) {
                        binding.tvProvider.setText(String.format(Locale.US, "%s:", selectedPaymentData.paypal.provider));
                    } else {
                        binding.tvProvider.setText(activity.getString(R.string.bank_card_));
                    }
                    if (selectedPaymentData.lastDigit != null) {
                        binding.txtFrom.setText(String.format(Locale.US, activity.getString(R.string.card) + ": %s", String.format("**** **** **** %s", selectedPaymentData.lastDigit)));
                    } else if (selectedPaymentData.paypal != null) {
                        binding.txtFrom.setText(String.format(Locale.US, activity.getString(R.string.paypal) + ": %s", selectedPaymentData.paypal.account));
                    }
                    if (selectedPaymentData.paypal != null) {
                        accountId = String.valueOf(selectedPaymentData.paypal.id);
                        selectedCardToken = selectedPaymentData.paypal.token;
                    } else {
                        accountId = selectedPaymentData.token;
                        selectedCardToken = selectedPaymentData.token;
                    }
                } else if (data.hasExtra("venmo")) {
                    isSelectVenmo = data.getBooleanExtra("venmo", false);

                    binding.txtFrom.setText(activity.getString(R.string.venmo_pay));
                    accountId = "";
                    selectedCardToken = "";
                    isSelectGooglePay = false;
                } else {
                    isSelectGooglePay = data.getBooleanExtra("googlepay", false);
                    isSelectVenmo = false;
                    binding.txtFrom.setText(activity.getString(R.string.google_pay));
                    accountId = "";
                    selectedCardToken = "";
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == 123) {
            thanksForPaymentDialog();//paypal payment
        }
    }

    private void doPaymentWithBraintree(String nonce, int paymentTypeId) {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBar.setVisibility(View.VISIBLE);
        activity.isClickableView = true;
        binding.btnWithdraw.setVisibility(View.INVISIBLE);
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("amount", withdrawBalance + "");
            map.put("sys_id", "6");
            map.put("nonce", nonce);
            map.put("card_token", selectedCardToken);
            map.put("payment_type_id", paymentTypeId + "");

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_ADD_BALANCE, true, map);
        } catch (Exception e) {
            binding.progressBar.setVisibility(View.GONE);
            activity.isClickableView = false;
            binding.btnWithdraw.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    @Override
    public void onClickRateDialog(boolean isCancelled) {
        activity.gotoMainActivity(Constants.TAB_JOB_LIST);
    }

    private void thanksForPaymentDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_balance_deposit);
        dialog.setCancelable(true);

        LinearLayout llDeposit = dialog.findViewById(R.id.ll_deposit);
        TextView txtThankYou = dialog.findViewById(R.id.tv_thank_you);

        llDeposit.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialog.setOnDismissListener(dialog1 -> {
            dialog.dismiss();
            activity.rateThisAppDialog(this);
        });

        txtThankYou.setOnClickListener(v -> {
            dialog.dismiss();
            activity.rateThisAppDialog(this);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {
        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isReadyToPayJson.isPresent()) {
                return;
            }
        }
        IsReadyToPayRequest request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        }
        if (request == null) {
            return;
        }

        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(activity,
                task1 -> {
                    if (task1.isSuccessful()) {
                        setGooglePayAvailable(task1.getResult());
                    } else {
                        Log.w("isReadyToPay failed", task1.getException());
                    }
                });
    }


    private void setGooglePayAvailable(boolean available) {
        isNeedToVisibleGooglePay = available;
    }

    private void requestPayment() {
        /*try {
            BraintreeFragment mBraintreeFragment = BraintreeFragment.newInstance(activity, bToken);

            mBraintreeFragment.addListener(this);
            GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
                    .transactionInfo(TransactionInfo.newBuilder()
                            .setTotalPrice("" + withdrawBalance)
                            .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                            .setCurrencyCode(activity.getCurrency().equals("SAR") ? "SAR" : "USD")
                            .build())
                    .billingAddressRequired(true);

            GooglePayment.requestPayment(mBraintreeFragment, googlePaymentRequest);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }*/
    }

    private void getBraintreeAccounts() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_USER_WALLET_LIST, false, null);

    }

    private void generateBraintreeToken() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);
    }

   /* @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        String nonce = paymentMethodNonce.getNonce();
        doPaymentWithBraintree(nonce, isSelectVenmo ? 10 : 7);
    }*/

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_USER_WALLET_LIST)) {

            cardModel = PaymentBraintreeCards.getPaymentCards(responseBody);

            if (cardModel != null && cardModel.cardPaypal.isPrimary == 1) {
                isSelectGooglePay = true;
                isSelectVenmo = false;
                binding.txtFrom.setText(activity.getString(R.string.google_pay));
                accountId = "";
                selectedCardToken = "";
            } else if (cardModel != null && cardModel.cardPaypal.isPrimary == 3) {
                isSelectGooglePay = false;
                isSelectVenmo = true;
                binding.txtFrom.setText(activity.getString(R.string.venmo_pay));
                accountId = "";
                selectedCardToken = "";
            } else {
                isSelectGooglePay = false;
                isSelectVenmo = false;

                //check for card primary account
                boolean isPrimarySet = false;
                if (cardModel != null && cardModel.cardPaypal.cards != null && cardModel.cardPaypal.cards.size() > 0) {
                    for (BraintreeCard.Data cards : cardModel.cardPaypal.cards) {
                        if (cards.isPrimary == 1) {
                            selectedPaymentData = cards;
                            binding.txtFrom.setText(String.format(Locale.US, "Card: %s", String.format("**** **** **** %s", cards.lastDigit)));
                            accountId = cards.token;
                            selectedCardToken = cards.token;
                            isPrimarySet = true;
                            break;
                        }
                    }
                }

                //if isPrimarySet=false then goes to check for paypal account otherwise skip this below code.
                if (!isPrimarySet && cardModel != null && cardModel.cardPaypal.paypal != null && cardModel.cardPaypal.paypal.size() > 0) {
                    for (Cardlist.Paypal paypal : cardModel.cardPaypal.paypal) {
                        if (paypal.isPrimary.equals("1") && paypal.verified.equals("1")) {//if verified
                            binding.txtFrom.setText(String.format(Locale.US, activity.getString(R.string.paypal) + ": %s", paypal.account));
                            accountId = paypal.token;
                            selectedCardToken = paypal.token;
                            break;
                        }
                    }
                }
            }
        } else if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                bToken = braintreeToken.token;
            }

        } else if (url.equalsIgnoreCase(API_ADD_BALANCE)) {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnWithdraw.setVisibility(View.VISIBLE);
            thanksForPaymentDialog();
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_ADD_BALANCE)) {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnWithdraw.setVisibility(View.VISIBLE);
        }
    }
}
