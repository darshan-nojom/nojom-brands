package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_DO_BRAINTREE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.REFERRAL_ID_FROM_LINK;
import static com.nojom.client.util.Utils.round;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.edittext.CustomEditText;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.textview.CustomTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.GooglePayClient;
import com.braintreepayments.api.GooglePayListener;
import com.braintreepayments.api.GooglePayRequest;
import com.braintreepayments.api.PaymentMethodNonce;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.nojom.client.BuildConfig;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.CardsAdapter;
import com.nojom.client.databinding.ActivityPaymentBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.addcard.AddCardActivity;
import com.nojom.client.ui.balance.BalanceActivity;
import com.nojom.client.ui.clientprofile.PaymentActivityVM;
import com.nojom.client.util.Constants;
import com.nojom.client.util.PaymentsUtil;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.CardParams;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class PaymentActivity extends BaseActivity implements View.OnClickListener, CardsAdapter.OnClickCardListener, DropInListener, GooglePayListener {
    private final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private ActivityPaymentBinding binding;
    private PaymentActivityVM paymentActivityVM;
    public boolean isFromGig = false;
    private ProjectByID projectData;
    private ExpertGigDetail gigData;
    private PaymentMethods paymentMethod;
    private PaymentsClient mPaymentsClient;
    public String appliedPromoCode = null;
    public double promoDiscountAmount, userActualBalance = 0, appliedRedeemAmount;

    private CardsAdapter cardsAdapter;
    private String braintreeToken;
    private int payVia;//1=Google, 2=card, 3= bank transfer
    private PaymentBraintreeCards paymentBraintreeCards;
    private String paymentCardToken, cardNumber, expDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        paymentActivityVM = new PaymentActivityVM(Task24Application.getInstance(), this);
        initData();
    }

    private void initData() {
        binding.toolbar.tvTitle.setText(getString(R.string.payment));
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.relAddCard.setOnClickListener(this);
        binding.relBankTransfer.setOnClickListener(this);
        binding.relDepositNow.setOnClickListener(this);
        binding.relGoogle.setOnClickListener(this);
//        binding.relVisa.setOnClickListener(this);
        binding.txtApply.setOnClickListener(this);

        if (getIntent() != null) {
            isFromGig = getIntent().getBooleanExtra(Constants.IS_FROM_GIG, false);
        }

        if (!isFromGig) {
            if (getIntent() != null) {
                projectData = (ProjectByID) getIntent().getSerializableExtra(Constants.USER_DATA);
            }

            if (projectData != null) {
                double fees = 0;
                double actual_amount = getTotalPrice();
                fees = getFees();
                binding.txtServAmount.setText(String.format(Locale.US, getCurrency().equals("SAR") ? getString(R.string.s_sar) : "$%s", Utils.decimalFormat(String.valueOf(fees))));
                setTotal(round(actual_amount + fees, 2));
            }

            String promocode = getSignUpRefCodeClickOnLink();
            if (!TextUtils.isEmpty(promocode) || (projectData.isFirstOrder != null && projectData.isFirstOrder == 1)) {
                promocode = !TextUtils.isEmpty(promocode) ? promocode : projectData.firstOrderCoupon;
                paymentActivityVM.checkPromoCodeApi(isFromGig, promocode, projectData, gigData);
            }

            binding.txtDepAmount.setText(String.format(Locale.US, this.getCurrency().equals("SAR") ? getString(R.string.s_sar) : "$ %s", Utils.decimalFormat(String.valueOf(projectData.fixedPrice))));
            binding.txtJobId.setText(String.format(Locale.US, "%d", projectData.jobPostId));
        } else {
            if (getIntent() != null) {
                gigData = (ExpertGigDetail) getIntent().getSerializableExtra(Constants.USER_DATA);
            }
            if (gigData != null) {
                double fees = 0;
                double actual_amount = getTotalPrice();
                fees = getFees();
                binding.txtServAmount.setText(String.format(Locale.US, getCurrency().equals("SAR") ? getString(R.string.s_sar) : "$%s", Utils.decimalFormat(String.valueOf(fees))));
                setTotal(round(actual_amount + fees, 2));

                String promocode = getSignUpRefCodeClickOnLink();
                if (!TextUtils.isEmpty(promocode) || (gigData.isFirstOrder != null && gigData.isFirstOrder == 1)) {
                    promocode = !TextUtils.isEmpty(promocode) ? promocode : gigData.couponData.couponCode;
                    paymentActivityVM.checkPromoCodeApi(isFromGig, promocode, projectData, gigData);
                }

                binding.txtDepAmount.setText(String.format(Locale.US, getCurrency().equals("SAR") ? getString(R.string.s_sar) : "$ %s", Utils.decimalFormat(String.valueOf(gigData.fixedPrice))));
                binding.txtJobId.setText(String.format(Locale.US, "%d", gigData.gigID));
            }
        }

        paymentMethod = Preferences.getPaymentMethod(this);

        try {
            if (paymentMethod.paymentMethod.size() > 0) {
                for (int i = 0; i < paymentMethod.paymentMethod.size(); i++) {
                    switch (paymentMethod.paymentMethod.get(i).name) {
                        case "Bank Card":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.relAddCard.setVisibility(View.VISIBLE);
                            } else {
                                binding.relAddCard.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentCardType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                        case "Google Pay":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.relGoogle.setVisibility(View.VISIBLE);
                            } else {
                                binding.relGoogle.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentGoogleType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userActualBalance = paymentMethod.clientBalance;
        binding.txtRedeemAmount.setText(getCurrency().equals("SAR") ? "(" + Utils.priceWithSAR(this, Utils.decimalFormat(String.valueOf(paymentMethod.clientBalance))) + ")" : "(" + Utils.priceWith$(Utils.decimalFormat(String.valueOf(paymentMethod.clientBalance)), this) + ")");
        setNojomPayBalance(userActualBalance);
        binding.txtRedeem.setTag("0");


        binding.swRedeemPay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            double redeemBalance = Double.parseDouble(binding.txtRedeemAmount.getTag().toString().replaceAll(",", ""));
            if (isChecked) {
                if (redeemBalance <= Double.parseDouble(getTotal())) {
                    double actual_amount = getTotalPrice();
                    double fees = getFees();
                    double total = (actual_amount + fees) - redeemBalance - promoDiscountAmount;
                    setTotal(round(total, 2));
                    setNojomPayBalance(0);
                    binding.relRedeem.setVisibility(View.VISIBLE);
                    binding.txtRedeem.setTag(redeemBalance);
                    binding.txtRedeem.setText(getCurrency().equals("SAR") ? "- " + Utils.decimalFormat(String.valueOf(redeemBalance)) + " " + getString(R.string.sar) : "- $" + Utils.decimalFormat(String.valueOf(redeemBalance)));
                } else if (redeemBalance >= Double.parseDouble(getTotal())) {
                    double actual_amount = getTotalPrice();
                    double fees = getFees();
                    double total = (actual_amount + fees) - redeemBalance - promoDiscountAmount;
                    if (total < 0) {
                        setTotal(0.0);
                    }
                    setNojomPayBalance(round(Math.abs(total), 2));

                    binding.relRedeem.setVisibility(View.VISIBLE);
                    binding.txtRedeem.setTag(round(redeemBalance - Math.abs(total), 2));
                    binding.txtRedeem.setText(getCurrency().equals("SAR") ? "- " + round(redeemBalance - Math.abs(total), 2) + " " + getString(R.string.sar) : "- $" + round(redeemBalance - Math.abs(total), 2));
                }

                notSelectedBackground(binding.relGoogle, binding.imgGoogleCheck);
//                notSelectedBackground(binding.relVisa, binding.imgVisaCheck);
                notSelectedBackground(binding.relAddCard, binding.imgNewCardCheck);
                notSelectedBackground(binding.relBankTransfer, binding.imgBankCheck);
                if (cardsAdapter != null) {
                    cardsAdapter.clearSelection();
                    cardsAdapter.notifyDataSetChanged();
                }
                paymentCardToken = "";
            } else {
                double redeemValue = Double.parseDouble(binding.txtRedeem.getTag().toString());
                double actual_amount = getTotalPrice();
                double fees = getFees();
                double total = (actual_amount + fees) - promoDiscountAmount;
                setTotal(total);
                setNojomPayBalance(redeemBalance + redeemValue);
                binding.relRedeem.setVisibility(View.GONE);
                binding.txtRedeem.setTag(0);
                binding.txtRedeem.setText(getCurrency().equals("SAR") ? "- " + Utils.decimalFormat("0") + " " + getString(R.string.sar) : "- $" + Utils.decimalFormat("0"));
            }
        });

        binding.etPromoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.txtApply.setBackground(getResources().getDrawable(R.drawable.lightgray_button_bg_12));
                    binding.txtApply.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.C_E5E5EA)));
                    binding.txtApply.setTextColor(getResources().getColor(R.color.c_3C3C4399));
                } else {
                    binding.txtApply.setBackground(getResources().getDrawable(R.drawable.lightgray_button_bg_12));
                    binding.txtApply.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    binding.txtApply.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        paymentActivityVM.getPromoCodeMutableLiveData().observe(this, model -> {

            if (model != null) {
                double redeemValue = Double.parseDouble(binding.txtRedeem.getTag().toString().replaceAll(",", ""));
                double actuRedeemAmount = Double.parseDouble(binding.txtRedeemAmount.getTag().toString().replaceAll(",", ""));
                if (model.isValid) {

                    appliedPromoCode = model.promoCode;
                    promoDiscountAmount = model.discountAmount;
                    binding.relPromoCode.setVisibility(View.VISIBLE);
                    double actual_amount = getTotalPrice();
                    double fees = getFees();
                    double total = (actual_amount + fees) - redeemValue - promoDiscountAmount;
                    if (total < 0) {
                        setTotal(0.0);
                    } else {
                        setTotal(round(Math.abs(total), 2));
                    }
                    binding.txtPromoCode.setTag(promoDiscountAmount + "");
                    binding.txtPromoCode.setText(getCurrency().equals("SAR") ? "- " + Utils.decimalFormat(String.valueOf(promoDiscountAmount)) + " " + getString(R.string.sar) : "- $" + Utils.decimalFormat(String.valueOf(promoDiscountAmount)));

                    if (getTotal().equals("0.0") || getTotal().equals("0")) {

                        setNojomPayBalance(actuRedeemAmount + promoDiscountAmount);

                        double val = Math.round(redeemValue - promoDiscountAmount);
                        if (val < 0) {
                            binding.txtRedeem.setTag("0");
                            binding.txtRedeem.setText(getCurrency().equals("SAR") ? "- " + Utils.decimalFormat("0") + " " + getString(R.string.sar) : "- $" + Utils.decimalFormat("0"));
                        } else {
                            binding.txtRedeem.setTag(round(Math.abs(val), 2));
                            binding.txtRedeem.setText(getCurrency().equals("SAR") ? "- " + round(Math.abs(val), 2) + " " + getString(R.string.sar) : "- $" + round(Math.abs(val), 2));
                        }
                    }

                } else {
                    binding.relPromoCode.setVisibility(View.GONE);
                    appliedPromoCode = null;
                    double actual_amount = getTotalPrice();
                    double fees = getFees();
                    double total = (actual_amount + fees) - redeemValue - promoDiscountAmount;
                    setTotal(round(Math.abs(total), 2));
                    promoDiscountAmount = 0;
                    binding.txtPromoCode.setTag("0");
                    binding.txtPromoCode.setText(getCurrency().equals("SAR") ? "- " + Utils.decimalFormat(String.valueOf(promoDiscountAmount)) + " " + getString(R.string.sar) : "- $" + Utils.decimalFormat(String.valueOf(promoDiscountAmount)));

                    failureError(model.getMessage(this));
                    Preferences.writeString(this, REFERRAL_ID_FROM_LINK, "");
                }
            }
        });

        paymentActivityVM.getPaymentCardMutableLiveData().observe(this, paymentBraintreeCards -> {
            this.paymentBraintreeCards = paymentBraintreeCards;
            if (paymentBraintreeCards != null && paymentBraintreeCards.cardPaypal != null && paymentBraintreeCards.cardPaypal.cards != null) {//cards
                if (paymentBraintreeCards.cardPaypal.cards.size() > 0) {
                    cardsAdapter = new CardsAdapter(this, paymentBraintreeCards.cardPaypal.cards, this);
                    binding.rvCard.setAdapter(cardsAdapter);
                }
            }

            //Paypal accounts
            ArrayList<BraintreeCard.Data> paypalAccountList = new ArrayList<>();
            if (paymentBraintreeCards != null && paymentBraintreeCards.cardPaypal != null && paymentBraintreeCards.cardPaypal.paypal != null) {
                for (Cardlist.Paypal paypal : paymentBraintreeCards.cardPaypal.paypal) {
                    BraintreeCard.Data data = new BraintreeCard.Data();
                    data.paypal = paypal;
                    paypalAccountList.add(data);
                }
//                DepositFundsActivity.paypalAccountToken = paymentBraintreeCards.cardPaypal.paypal.get(0).token;
//                DepositFundsActivity.paypalAccountEmail = paymentBraintreeCards.cardPaypal.paypal.get(0).account;
            }
        });

        paymentActivityVM.getPurchaseMutableLiveData().observe(this, purchaseModel -> {

            if (gigData != null && gigData.isFromOffer) {
                try {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("partitionKey", "#message#" + getUserID() + "-" + gigData.receiverId);
                    jsonData.put("senderId", getUserID());
                    jsonData.put("offerStatus", 2);
                    jsonData.put("receiverId", gigData.receiverId);
                    jsonData.put("messageId", gigData.messageId);
                    jsonData.put("price", gigData.fixedPrice);
                    jsonData.put("contractID", purchaseModel.contractID);
                    mSocket.emit("sendLiveOfferStatus", jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            thanksForPaymentDialog();
        });

        paymentActivityVM.getBraintreeTokenMutableLiveData().observe(this, token -> {
            braintreeToken = token;
        });

        paymentActivityVM.getIsShowProgress().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.txtBtn.setVisibility(View.INVISIBLE);
                binding.txtFinalTotal.setVisibility(View.INVISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtBtn.setVisibility(View.VISIBLE);
                binding.txtFinalTotal.setVisibility(View.VISIBLE);
            }
        });
        initializeGooglePay();
    }

    private void setNojomPayBalance(double balance) {
        if (paymentMethod != null) {
            binding.txtRedeemAmount.setTag(balance);
            binding.txtRedeemAmount.setVisibility(View.VISIBLE);
//            binding.txtRedeemAmount.setText(getCurrency().equals("SAR") ? "(" + Utils.priceWithSAR(this, Utils.decimalFormat(String.valueOf(balance))) + ")" : "(" + Utils.priceWith$(Utils.decimalFormat(String.valueOf(balance)), this) + ")");
        } else {
            binding.txtRedeemAmount.setTag(balance);
            binding.txtRedeemAmount.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
            runOnUiThread(() -> paymentActivityVM.getStripeCardList());
        } else {
            runOnUiThread(() -> paymentActivityVM.getCardList());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_google:
                selectedBackground(binding.relGoogle, binding.imgGoogleCheck);
                notSelectedBackground(binding.relAddCard, binding.imgNewCardCheck);
                notSelectedBackground(binding.relBankTransfer, binding.imgBankCheck);
                //binding.swRedeemPay.setChecked(false);
                if (cardsAdapter != null) {
                    cardsAdapter.clearSelection();
                    cardsAdapter.notifyDataSetChanged();
                }
                payVia = 1;
                paymentCardToken = "";
                break;
            case R.id.rel_visa:
                notSelectedBackground(binding.relGoogle, binding.imgGoogleCheck);
                notSelectedBackground(binding.relAddCard, binding.imgNewCardCheck);
                notSelectedBackground(binding.relBankTransfer, binding.imgBankCheck);
                //binding.swRedeemPay.setChecked(false);
                break;
            case R.id.rel_add_card:
                notSelectedBackground(binding.relGoogle, binding.imgGoogleCheck);
                selectedBackground(binding.relAddCard, binding.imgNewCardCheck);
                notSelectedBackground(binding.relBankTransfer, binding.imgBankCheck);
                //binding.swRedeemPay.setChecked(false);
                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra(Constants.ADD_CARD_KEY, Constants.ADD_CARD_VAL);
                startActivity(intent);
                if (cardsAdapter != null) {
                    cardsAdapter.clearSelection();
                    cardsAdapter.notifyDataSetChanged();
                }
                paymentCardToken = "";
                payVia = 0;
                break;
            case R.id.rel_bank_transfer:
                notSelectedBackground(binding.relGoogle, binding.imgGoogleCheck);
                notSelectedBackground(binding.relAddCard, binding.imgNewCardCheck);
                selectedBackground(binding.relBankTransfer, binding.imgBankCheck);
                //binding.swRedeemPay.setChecked(false);
                Intent in = new Intent(this, AddWalletActivity.class);
                in.putExtra("wallet", binding.txtRedeemAmount.getText().toString());
                in.putExtra("id", binding.txtJobId.getText().toString());
                in.putExtra("depAmnt", binding.txtDepAmount.getText().toString());
                in.putExtra("fees", binding.txtServAmount.getText().toString());
                in.putExtra("total", binding.txtTotal.getText().toString());
                in.putExtra("code", binding.txtPromoCode.getText().toString());
                in.putExtra("redeem", binding.txtRedeem.getText().toString());
                in.putExtra("isFromGig", isFromGig);
                in.putExtra("project", projectData);
                in.putExtra("promocode", appliedPromoCode);
                in.putExtra("promoamount", promoDiscountAmount);
                in.putExtra("gig", gigData);
                startActivity(in);
                if (cardsAdapter != null) {
                    cardsAdapter.clearSelection();
                    cardsAdapter.notifyDataSetChanged();
                }
                paymentCardToken = "";
                payVia = 0;
                break;
            case R.id.txtApply:
                if (TextUtils.isEmpty(binding.etPromoCode.getText().toString().trim())) {
                    toastMessage(getString(R.string.promo_code));
                    return;
                }
                paymentActivityVM.checkPromoCodeApi(isFromGig, binding.etPromoCode.getText().toString().trim(), projectData, gigData);

                break;
            case R.id.rel_depositNow:
                switch (payVia) {
                    case 1://GOOGLE
                        openGoogleConfirmationDialog();
                        break;
                    case 2://CARD
                        onClickCardPay();
                        break;
                }
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    private void selectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(getResources().getDrawable(R.drawable.lightgray_button_bg_12));
        imgCheck.setImageResource(R.drawable.circle_check);
    }

    private void notSelectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(getResources().getDrawable(R.drawable.lightgray_border_12));
        imgCheck.setImageResource(R.drawable.circle_uncheck);
    }

    private void initializeGooglePay() {
        //initialize google pay payment client
        mPaymentsClient = Wallet.getPaymentsClient(this, new Wallet.WalletOptions.Builder().setEnvironment(BuildConfig.DEBUG ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION).build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            possiblyShowGooglePayButton();
        }

        if (!Task24Application.getInstance().paymentGoogleType.equalsIgnoreCase("Stripe")) {
//            checkGooglePayVisibility();
            // stripe = new Stripe(fragment.activity, BuildConfig.DEBUG ? STRIPE_KEY_TEST : STRIPE_KEY_PRODUCTION);
        }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        }
        if (request == null) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this, task1 -> {
            if (task1.isSuccessful()) {
                setGooglePayAvailable(task1.getResult());
            } else {
                Log.w("isReadyToPay failed", task1.getException());
            }
        });
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
//            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
        } else {
//            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.GONE);
            toastMessage(getString(R.string.googlepay_status_unavailable));
        }
    }

    String getSignUpRefCodeClickOnLink() {
        return Preferences.readString(this, REFERRAL_ID_FROM_LINK, null);
    }

    double getTotalPrice() {
        if (!isFromGig) {
            if (projectData != null && projectData.fixedPrice != null) {
                return projectData.fixedPrice;
            }
        } else {
            if (gigData != null && gigData.fixedPrice != null) {
                return gigData.fixedPrice;
            }
        }
        return 0;
    }

    double getFees() {
        if (!isFromGig) {
            if (projectData != null && projectData.fixedPrice != null) {
                double amount = projectData.fixedPrice;
                double depositFee = projectData.jobPostContracts.depositCharges;
                double percentage = (amount * depositFee) / 100;
                return percentage/*Math.max(percentage, depositFee)*/;
            }
        } else {
            if (gigData != null && gigData.fixedPrice != null) {
                double amount = gigData.fixedPrice;
                double depositFee = gigData.depositCharges;
                double percentage = (amount * depositFee) / 100;
                return percentage/*Math.max(percentage, depositFee)*/;
            }
        }
        return 0;
    }

    private void setTotal(Double amount) {
        binding.txtTotal.setText(getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(amount)) + " " + getString(R.string.sar) : "$" + Utils.decimalFormat(String.valueOf(amount)));
        binding.txtFinalTotal.setText(getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(amount)) + " " + getString(R.string.sar) : "$" + Utils.decimalFormat(String.valueOf(amount)));
//        binding.etTxnAmount.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(amount)) + " " + fragment.getString(R.string.sar)
//                : "$" + Utils.decimalFormat(String.valueOf(amount)));
    }

    private String getTotal() {
        if (getCurrency().equals("SAR")) {
            return Utils.priceWithoutSAR(this, binding.txtTotal.getText().toString().trim()).replaceAll(",", "");
        } else {
            return Utils.priceWithout$(binding.txtTotal.getText().toString().trim()).replaceAll(",", "");
        }
    }

    @Override
    public void onClickCard(BraintreeCard.Data card) {
        notSelectedBackground(binding.relGoogle, binding.imgGoogleCheck);
        notSelectedBackground(binding.relAddCard, binding.imgNewCardCheck);
        notSelectedBackground(binding.relBankTransfer, binding.imgBankCheck);
        paymentCardToken = card.token;
        cardNumber = card.card_number;
        expDate = card.expDate;
        payVia = 2;
    }

    public void openGoogleConfirmationDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(getString(R.string.pay_with_google_pay));
        txtDesc.setText(getString(R.string.are_you_sure_you_want_to_pay_with_googlepay));
        btnNo.setText(getString(R.string.cancel));
        btnYes.setText(getString(R.string.Ok));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            try {
                if (Task24Application.getInstance().paymentGoogleType.equalsIgnoreCase("Stripe")) {
                    if (Double.parseDouble(getTotal()) == 0.0) {
                        doStripeWithPayment("");
                    } else {
                        requestPaymentStripe();
                    }
                } else {
                    if (Double.parseDouble(getTotal()) == 0.0) {
                        doPaymentWithBraintree("", "", payVia);
                    } else {
                        requestPayment();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void doStripeWithPayment(String token) {
        if (!isNetworkConnected()) return;

        isClickableView = true;

        double redeemValue = Double.parseDouble(binding.txtRedeem.getTag().toString().replaceAll(",", ""));
        if (!isFromGig) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = getFees();
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = getTotalPriceWithDeposit();


                HashMap<String, String> map = new HashMap<>();
                map.put("job_post_id", projectData.jobPostId + "");
                map.put("job_post_bid_id", projectData.jpbId + "");
                map.put("redeem", redeemValue + "");
                map.put("amount", totalPriceWithDeposit + "");
                map.put("deposit_charges", String.valueOf(depositFee));

                map.put("payment_type_id", "8");

                if (!TextUtils.isEmpty(appliedPromoCode)) {
                    map.put("discount_code", appliedPromoCode);
                }
                map.put("payable_amount", getTotal());
                map.put("description", description);
                map.put("payment_platform_id", "3");
                map.put("sys_id", "6");
                map.put("card_token", TextUtils.isEmpty(token) ? "" : token);

                paymentActivityVM.doStripeWithPayment(API_DO_STRIPE_PAYMENT, map);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-nojom " + BuildConfig.VERSION_NAME;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");

                    if (payVia == 2) {//card
                        map.put("paymentTypeID", "8");
                    } else if (payVia == 4) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (payVia == 1) {//google
                        map.put("paymentTypeID", "7");
                    }
                    map.put("paymentPlatformID", "3");
                    map.put("description", description);
                    JSONArray jsonArray = new JSONArray();
                    if (gigData.customPackages != null && gigData.customPackages.size() > 0) {
                        for (int i = 0; i < gigData.customPackages.size(); i++) {
                            JSONObject object = new JSONObject();
                            if (!gigData.customPackages.get(i).quantity.equalsIgnoreCase("0")) {
                                object.put("gig_requirment_id", gigData.customPackages.get(i).id);
                                object.put("quantity", Integer.parseInt(gigData.customPackages.get(i).quantity));
                                jsonArray.put(object);
                            }
                        }

                        map.put("requirments", jsonArray.toString());
                    }

                    if (!TextUtils.isEmpty(appliedPromoCode)) {
                        map.put("discountCode", appliedPromoCode);
                    }

                    map.put("sys_id", "6");
                    map.put("payableAmount", getTotal().replaceAll(",", "") + "");
                    map.put("totalPrice", gigData.fixedPrice + "");
                    map.put("card_token", TextUtils.isEmpty(token) ? "" : token);
                    map.put("redeem", redeemValue + "");

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }

                    paymentActivityVM.doStripeWithPayment(API_DO_CUSTOM_GIGS_STRIPE_PAYMENT, map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    double getTotalPriceWithDeposit() {
        if (!isFromGig) {
            if (projectData != null && projectData.fixedPrice != null) {
                double amount = projectData.fixedPrice;
                return amount + getFees();
            }
        } else {
            if (gigData != null && gigData.fixedPrice != null) {
                double amount = gigData.fixedPrice;
                return amount + getFees();
            }
        }
        return 0;
    }

    private void thanksForPaymentDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_bank_transfer_done);
        dialog.setCancelable(true);

        TextView txtGoto = dialog.findViewById(R.id.txt_goto_wallet);
        TextView txtService = dialog.findViewById(R.id.txt_customerService);

        txtGoto.setOnClickListener(v -> {
            gotoMainActivity(4);
            startActivity(new Intent(this, BalanceActivity.class));
        });
        txtService.setOnClickListener(v -> {
            dialog.dismiss();
            gotoMainActivity(Constants.TAB_JOB_LIST);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialog.setOnDismissListener(dialog1 -> {
            dialog.dismiss();
            gotoMainActivity(Constants.TAB_JOB_LIST);
        });
    }

    public void requestPaymentStripe() {

        Optional<JSONObject> paymentDataRequestJson = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isFromGig) {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), getUserData().username, projectData.jobPostId);
            } else {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), getUserData().username, gigData.gigID);
            }

            if (!paymentDataRequestJson.isPresent()) {
                return;
            }

            PaymentDataRequest request = PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

            // Since loadPaymentData may show the UI asking the user to select a payment method, we use
            // AutoResolveHelper to wait for the user interacting with it. Once completed,
            // onActivityResult will be called with the result.
            AutoResolveHelper.resolveTask(mPaymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {

                case Activity.RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);

                    final String paymentInfo = paymentData.toJson();

                    try {
                        JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");

                        final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
                        final String tokenJson = tokenizationData.getString("token");
                        JSONObject objToken = new JSONObject(tokenJson);
                        String token = objToken.getString("id");
                        // Logging token string.
                        Log.e("GooglePaytokenStrip: ", token);

                        //get secret key from this API, and that secret key have to pass for stripe confirmation payment
                        doStripeWithPayment(token);

                    } catch (JSONException e) {
                        throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
                    }
                    break;

                case Activity.RESULT_CANCELED:
                    // The user cancelled the payment attempt
                    break;

                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    if (status != null) {
                        //handleError(status.getStatusCode());
                    }
                    break;
            }
        }
    }

    private void doPaymentWithBraintree(String nonce, String token, int payVia) {

        double redeemValue = Double.parseDouble(binding.txtRedeem.getTag().toString().replaceAll(",", ""));
        if (!isFromGig) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = getFees();
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = getTotalPriceWithDeposit();
                double promoCodeDiscount = promoDiscountAmount;

                HashMap<String, String> map = new HashMap<>();
                map.put("job_post_id", projectData.jobPostId + "");
                map.put("job_post_bid_id", projectData.jpbId + "");
                map.put("redeem", redeemValue + "");
                map.put("amount", totalPriceWithDeposit + "");
                map.put("deposit_charges", String.valueOf(depositFee));

                map.put("payment_type_id", "8");

                if (!TextUtils.isEmpty(appliedPromoCode)) {
                    map.put("discount_code", appliedPromoCode);
                }
                map.put("payable_amount", getTotal());
                map.put("description", description);
                map.put("payment_platform_id", "3");
                map.put("sys_id", "6");
                map.put("nonce", TextUtils.isEmpty(nonce) ? "" : nonce);
                map.put("card_token", TextUtils.isEmpty(token) ? "" : token);

                paymentActivityVM.doPaymentWithBraintree(API_DO_BRAINTREE_PAYMENT, map);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-InfluencerBird " + BuildConfig.VERSION_NAME;

                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("deadlineID", gigData.deadlineID + "");
                    if (payVia == 2) {//card
                        map.put("paymentTypeID", "8");
                    } else if (payVia == 4) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (payVia == 1) {//google
                        map.put("paymentTypeID", "7");
                    }
                    map.put("paymentPlatformID", "3");
                    map.put("description", description);
                    JSONArray jsonArray = new JSONArray();
                    if (gigData.customPackages != null && gigData.customPackages.size() > 0) {
                        for (int i = 0; i < gigData.customPackages.size(); i++) {
                            if (gigData.customPackages.get(i).customPackageID != 0) {
                                JSONObject object = new JSONObject();
                                object.put("gig_requirment_id", gigData.customPackages.get(i).id);
                                object.put("customPackageID", gigData.customPackages.get(i).customPackageID);
                                if (gigData.customPackages.get(i).inputType == 1 && !gigData.customPackages.get(i).quantity.equalsIgnoreCase("0")) {
                                    object.put("quantity", Integer.parseInt(gigData.customPackages.get(i).quantity));
                                }
                                jsonArray.put(object);
                            }
                        }

                        map.put("requirments", jsonArray.toString());
                    }

                    if (!TextUtils.isEmpty(appliedPromoCode)) {
                        map.put("discountCode", appliedPromoCode);
                    }
                    map.put("sys_id", "6");
                    map.put("payableAmount", getTotal().replaceAll(",", "") + "");
                    map.put("totalPrice", gigData.fixedPrice + "");
                    map.put("nonce", TextUtils.isEmpty(nonce) ? "" : nonce);
                    map.put("card_token", TextUtils.isEmpty(token) ? "" : token);
                    map.put("redeem", redeemValue + "");

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }

                    paymentActivityVM.doPaymentWithBraintree(API_DO_CUSTOM_GIGS_PAYMENT, map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private BraintreeClient braintreeClient;
    private GooglePayClient googlePayClient;

    private void requestPayment() {
        double amount = 0.0, depositFee = 0.0;
        if (!isFromGig) {
            amount = projectData.fixedPrice;
            depositFee = projectData.jobPostContracts.depositCharges;
        } else {
            amount = gigData.fixedPrice;
            depositFee = gigData.depositCharges;
        }
        double percentage = (amount * depositFee) / 100;
//        double depositCharges = Math.max(percentage, depositFee);
        double promoCodeDiscount = promoDiscountAmount;
        double finalAmount = amount + percentage - promoCodeDiscount;

        /*GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
                .transactionInfo(TransactionInfo.newBuilder()
                        .setTotalPrice("" + finalAmount)
                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                        .setCurrencyCode(fragment.activity.getCurrency().equals("SAR") ? "SAR" : "USD")
                        .build())
                // We recommend collecting billing address information, at minimum
                // billing postal code, and passing that billing postal code with all
                // Google Pay card transactions as a best practice.
                .billingAddressRequired(true);

        GooglePayment.requestPayment(mBraintreeFragment, googlePaymentRequest);*/

        /*GooglePayRequest googlePayRequest = new GooglePayRequest();
        googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder().setTotalPrice(finalAmount + "").setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL).setCurrencyCode(getCurrency().equals("SAR") ? "SAR" : "USD").build());
        googlePayRequest.setBillingAddressRequired(true);

        DropInRequest dropInRequest = new DropInRequest();
        dropInRequest.setGooglePayRequest(googlePayRequest);
        runOnUiThread(() -> {
            DropInClient dropInClient = new DropInClient(this, braintreeToken);
            dropInClient.setListener(this);
            dropInClient.launchDropIn(dropInRequest);
        });*/

        /*try {

            if (braintreeClient == null) {
                braintreeClient = new BraintreeClient(this, braintreeToken);
                googlePayClient = new GooglePayClient(this, braintreeClient);
                googlePayClient.setListener(this);
            }


            GooglePayRequest googlePayRequest = new GooglePayRequest();
            googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder()
                    .setTotalPrice("" + finalAmount)
                    .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                    .setCurrencyCode(getCurrency().equals("SAR") ? "SAR" : "USD")
                    .build());
            googlePayRequest.setBillingAddressRequired(true);

            googlePayClient.requestPayment(this, googlePayRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Optional<JSONObject> paymentDataRequestJson = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isFromGig) {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), getUserData().username, projectData.jobPostId);
            } else {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), getUserData().username, gigData.gigID);
            }

            if (paymentDataRequestJson.isPresent()) {
                PaymentDataRequest request = PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

                // Launch the Google Pay payment sheet
                AutoResolveHelper.resolveTask(mPaymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        if (dropInResult.getPaymentMethodNonce() != null) {
            String generatedNonce = dropInResult.getPaymentMethodNonce().getString();
            doPaymentWithBraintree(generatedNonce, "", payVia);
        }
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {

    }

    public void onClickCardPay() {
        try {
            if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
                if (paymentBraintreeCards != null && paymentBraintreeCards.cardPaypal != null && paymentBraintreeCards.cardPaypal.cards != null && paymentBraintreeCards.cardPaypal.cards.size() > 0) {
                    enterCVVDialog();
                } else {
                    toastMessage(getString(R.string.please_add_card));
                }
            } else {
                if (!TextUtils.isEmpty(paymentCardToken)) {
                    openConfirmationDialog();
                } else {
                    if (getTotal().equalsIgnoreCase("0.0") || getTotal().equalsIgnoreCase("0.00") || getTotal().equalsIgnoreCase("0")) {
                        openConfirmationDialog();
                    } else {
                        toastMessage(getString(R.string.please_add_card));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterCVVDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enter_cvv);
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        CustomTextView textCancel = dialog.findViewById(R.id.tv_cancel);
        CustomTextView textRateNow = dialog.findViewById(R.id.tv_ok);
        CustomEditText etCvv = dialog.findViewById(R.id.etCvv);

        textCancel.setOnClickListener(v -> dialog.dismiss());

        textRateNow.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etCvv.getText().toString().trim())) {
                toastMessage(getString(R.string.enter_cvv));
                return;
            }
            if (TextUtils.isEmpty(cardNumber)) {
                toastMessage(getString(R.string.enter_card_number));
                return;
            }
            dialog.dismiss();
            String cvv = etCvv.getText().toString().trim();
            callCardAPI(cardNumber, cvv, expDate);
        });

        dialog.show();
    }

    public void openConfirmationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(getString(R.string.pay_with_card));
        txtDesc.setText(getString(R.string.are_you_sure_you_want_to_pay_with_card));
        btnNo.setText(getString(R.string.cancel));
        btnYes.setText(getString(R.string.Ok));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            doPaymentWithBraintree("", paymentCardToken, payVia);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void callCardAPI(String cardNumber, String cvv, String expiry) {
        //call stripe API call
        try {
            isClickableView = true;
            paymentActivityVM.getIsShowProgress().postValue(true);
            CardInputWidget cardInputWidget = new CardInputWidget(this);
            cardInputWidget.setCardNumber(cardNumber + "");
            String[] split = Objects.requireNonNull(expiry).split("/");
            cardInputWidget.setExpiryDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            cardInputWidget.setCvcCode(cvv);

            CardParams card = cardInputWidget.getCardParams();

            String key = PaymentConfiguration.getInstance(this).getPublishableKey();
            Stripe stripe = new Stripe(this, key);
            if (card != null) {
                stripe.createCardToken(card, new ApiResultCallback<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        String stripeToken = token.getId();
                        doStripeWithPayment(stripeToken);
                    }

                    @Override
                    public void onError(@NotNull Exception e) {
                        isClickableView = false;
                        paymentActivityVM.getIsShowProgress().postValue(false);
                        toastMessage("" + e.getMessage());
                    }
                });
            } else {
                isClickableView = false;
                paymentActivityVM.getIsShowProgress().postValue(false);
                toastMessage(getString(R.string.card_details_not_valid));
            }
        } catch (Exception e) {
            isClickableView = false;
            paymentActivityVM.getIsShowProgress().postValue(false);
            e.printStackTrace();
        }
    }

    @Override
    public void onGooglePaySuccess(@NonNull PaymentMethodNonce paymentMethodNonce) {
        doPaymentWithBraintree(paymentMethodNonce.toString(), "", payVia);
    }

    @Override
    public void onGooglePayFailure(@NonNull Exception error) {

    }
}
