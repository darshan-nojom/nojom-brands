package com.nojom.client.fragment.payment;

import static com.nojom.client.util.Constants.API_DO_BANK_TRANSFER;
import static com.nojom.client.util.Constants.API_DO_BANK_TRANSFER_GIG;
import static com.nojom.client.util.Constants.API_DO_BRAINTREE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_GIGS_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_GIGS_STRIPE_PACKAGE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.API_GET_STRIPE_CARD_LIST;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;
import static com.nojom.client.util.Constants.REFERRAL_ID_FROM_LINK;
import static com.nojom.client.util.Utils.round;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.edittext.CustomEditText;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.textview.CustomTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.GooglePayRequest;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.BuildConfig;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.PaymentAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentDepositFundsBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Banks;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.PurchaseModel;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.activity.VideoPickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.RateClickListener;
import com.nojom.client.ui.addcard.AddCardActivity;
import com.nojom.client.ui.addcard.CardListActivity;
import com.nojom.client.ui.clientprofile.DepositFundsActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
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

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreditCardDepositFragmentVM extends AndroidViewModel implements RateClickListener,
        View.OnClickListener/*, PaymentMethodNonceCreatedListener*/, RequestResponseListener, PaymentAdapter.OnClickPaymentListener {
    private final FragmentDepositFundsBinding binding;
    private final BaseFragment fragment;
    private final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private ProjectByID projectData;
    private ExpertGigDetail gigData;
    private PaymentMethods paymentMethods;
    private String paymentCardToken;
    private int selectedPackagePosition = 0;
    private PaymentsClient mPaymentsClient;
//    private BraintreeFragment mBraintreeFragment;
    private String cvv = "";
    private PaymentBraintreeCards paymentBraintreeCards;
    private Stripe stripe;
    private PaymentData paymentData;
    private PurchaseModel purchaseModel;

    List<Banks.Data> bankList;

    CreditCardDepositFragmentVM(Application application, FragmentDepositFundsBinding depositFundsBinding, BaseFragment creditCardDepositFragment) {
        super(application);
        binding = depositFundsBinding;
        fragment = creditCardDepositFragment;
        initData();
    }

    private void initData() {
        binding.tvReturnRedeem.setOnClickListener(this);
        binding.tvApplyRedeem.setOnClickListener(this);
        binding.tvAddCard.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.tvEditPromo.setOnClickListener(this);
        binding.tvPromocode.setOnClickListener(this);
        binding.rlAddPaypal.setOnClickListener(this);
        binding.tvAddPaypal.setOnClickListener(this);
        binding.txtAttach.setOnClickListener(this);
        binding.etBankName.setOnClickListener(this);
        binding.imgDelete.setOnClickListener(this);

        initializeGooglePay();
        try {
            bankList = Preferences.getBanks(fragment.activity);
            if (fragment.activity != null) {
                if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
                    projectData = ((DepositFundsActivity) fragment.activity).getProjectData();

                    String promocode = ((DepositFundsActivity) fragment.activity).getSignUpRefCodeClickOnLink();
                    if (!TextUtils.isEmpty(promocode) || (projectData.isFirstOrder != null && projectData.isFirstOrder == 1)) {
                        promocode = !TextUtils.isEmpty(promocode) ? promocode : projectData.firstOrderCoupon;
                        ((DepositFundsActivity) fragment.activity).checkPromoCodeApi(promocode, 0);
                    }

                    binding.tvDepositAmount.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$ %s", Utils.decimalFormat(String.valueOf(projectData.fixedPrice))));
                    binding.tvJobId.setText(String.format(Locale.US, "%d", projectData.jobPostId));
                } else {
                    gigData = ((DepositFundsActivity) fragment.activity).getGigData();
                    selectedPackagePosition = ((DepositFundsActivity) fragment.activity).getSelectedPackagePos();

                    String promocode = ((DepositFundsActivity) fragment.activity).getSignUpRefCodeClickOnLink();
                    if (!TextUtils.isEmpty(promocode) || (gigData.isFirstOrder != null && gigData.isFirstOrder == 1)) {
                        promocode = !TextUtils.isEmpty(promocode) ? promocode : gigData.couponData.couponCode;
                        ((DepositFundsActivity) fragment.activity).checkPromoCodeApi(promocode, 0);
                    }

                    binding.tvDepositAmount.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$ %s", Utils.decimalFormat(String.valueOf(gigData.fixedPrice))));
                    binding.tvJobId.setText(String.format(Locale.US, "%d", gigData.gigID));
                }

                paymentMethods = Preferences.getPaymentMethod(fragment.activity);
            }

            if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
                if (projectData != null) {
                    double fees = 0;
                    if (fragment.activity != null) {
                        double actual_amount = ((DepositFundsActivity) fragment.activity).getTotalPrice();
                        fees = ((DepositFundsActivity) fragment.activity).getFees();
                        binding.tvDepositFee.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.decimalFormat(String.valueOf(fees))));
                        setTotal(round(actual_amount + fees, 2));
                    }
                }
            } else {
                if (gigData != null) {
                    double fees = 0;
                    if (fragment.activity != null) {
                        double actual_amount = ((DepositFundsActivity) fragment.activity).getTotalPrice();
                        fees = ((DepositFundsActivity) fragment.activity).getFees();
                        binding.tvDepositFee.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.decimalFormat(String.valueOf(fees))));
                        setTotal(round(actual_amount + fees, 2));
                    }
                }
            }

            if (paymentMethods != null) {
                balanceRedeemView();
            } else {
                binding.relRedeemBalanceView.setVisibility(View.GONE);
                binding.rlAvailableBalance.setVisibility(View.GONE);
            }
            binding.tvBalance.setTag(((DepositFundsActivity) fragment.activity).getUserActualBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeGooglePay() {
        //initialize google pay payment client
        mPaymentsClient = Wallet.getPaymentsClient(fragment.activity,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(BuildConfig.DEBUG ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION)
                        .build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            possiblyShowGooglePayButton();
        }

        if (!Task24Application.getInstance().paymentGoogleType.equalsIgnoreCase("Stripe")) {
            checkGooglePayVisibility();
            // stripe = new Stripe(fragment.activity, BuildConfig.DEBUG ? STRIPE_KEY_TEST : STRIPE_KEY_PRODUCTION);
        }
    }

    void onUpdateUIMethod() {
        binding.rlAddPaypal.setVisibility(View.GONE);
        binding.linBanTransfer.setVisibility(View.GONE);
        binding.rlAddCard.setVisibility(View.VISIBLE);
        if (!fragment.activity.isEmpty(DepositFundsActivity.paymentAccount)) {
            updateCardInField();
        }
    }

    void onResumeMethod() {
        if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
            fragment.activity.runOnUiThread(this::getStripeCardList);
        } else {
            fragment.activity.runOnUiThread(this::getCardList);
        }
    }

    public void onClickCardPay() {
        try {
            if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
                if (paymentBraintreeCards.cardPaypal.cards != null && paymentBraintreeCards.cardPaypal.cards.size() > 0) {
                    enterCVVDialog();
                } else {
                    fragment.activity.toastMessage(fragment.activity.getString(R.string.please_add_card));
                }
            } else {
                if (!TextUtils.isEmpty(paymentCardToken)) {
                    openConfirmationDialog();
                } else {
                    if (getTotal().equalsIgnoreCase("0.0") || getTotal().equalsIgnoreCase("0.00") ||
                            getTotal().equalsIgnoreCase("0")) {
                        openConfirmationDialog();
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.please_add_card));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterCVVDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(fragment.activity);
        dialog.setContentView(R.layout.dialog_enter_cvv);
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        CustomTextView textCancel = dialog.findViewById(R.id.tv_cancel);
        CustomTextView textRateNow = dialog.findViewById(R.id.tv_ok);
        CustomEditText etCvv = dialog.findViewById(R.id.etCvv);

        textCancel.setOnClickListener(v -> dialog.dismiss());

        textRateNow.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etCvv.getText().toString().trim())) {
                fragment.activity.toastMessage("Enter CVV");
                return;
            }
            dialog.dismiss();
            cvv = etCvv.getText().toString().trim();
            callCardAPI(DepositFundsActivity.cardNumber, cvv, DepositFundsActivity.cardExp);
        });

        dialog.show();
    }

    private void callCardAPI(String cardNumber, String cvv, String expiry) {
        //call stripe API call
        try {
            fragment.activity.isClickableView = true;
            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
            ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);
            fragment.activity.isClickableView = true;

            CardInputWidget cardInputWidget = new CardInputWidget(fragment.activity);
            cardInputWidget.setCardNumber(cardNumber + "");
            String[] split = Objects.requireNonNull(expiry).split("/");
            cardInputWidget.setExpiryDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            cardInputWidget.setCvcCode(cvv);

            CardParams card = cardInputWidget.getCardParams();

            String key = PaymentConfiguration.getInstance(fragment.activity).getPublishableKey();
            Stripe stripe = new Stripe(fragment.activity, key);
            if (card != null) {
                stripe.createCardToken(card, new ApiResultCallback<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        String stripeToken = token.getId();
                        doStripeWithPayment(stripeToken);
                    }

                    @Override
                    public void onError(@NotNull Exception e) {
                        fragment.activity.isClickableView = false;
                        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
                        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.GONE);
                        fragment.activity.toastMessage("" + e.getMessage());
                    }
                });
            } else {
                fragment.activity.isClickableView = false;
                ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
                ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.GONE);
                fragment.activity.toastMessage(fragment.activity.getString(R.string.card_details_not_valid));
            }
        } catch (Exception e) {
            fragment.activity.isClickableView = false;
            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
            ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    public void onClickPaypalPay() {
        try {
            if (!TextUtils.isEmpty(DepositFundsActivity.paypalAccountToken)) {
                openPaypalConfirmationDialog();
            } else {
                if (getTotal().equalsIgnoreCase("0.0") || getTotal().equalsIgnoreCase("0.00") ||
                        getTotal().equalsIgnoreCase("0")) {
                    openPaypalConfirmationDialog();
                } else {
                    fragment.activity.toastMessage(fragment.activity.getString(R.string.please_add_paypal_account));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickGooglePay() {
        openGoogleConfirmationDialog();
    }

    public void onClickVenmoPay() {
        openVenmoConfirmationDialog();
    }

    public void onClickBankTransferPay() {
        if (TextUtils.isEmpty(binding.etSenderName.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_enter_sender_name));
            return;
        }
        if (TextUtils.isEmpty(binding.etBankName.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_select_bank_name));
            return;
        }
        if (TextUtils.isEmpty(binding.etCardNumber.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_enter_account_number));
            return;
        }
        if (TextUtils.isEmpty(binding.etTxnAmount.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_enter_valid_transaction_amount));
            return;
        }
        if (TextUtils.isEmpty(binding.etTxnDate.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_enter_transaction_date));
            return;
        }
        if (TextUtils.isEmpty(binding.etRefNo.getText().toString().trim())) {
            fragment.activity.toastMessage(fragment.getString(R.string.please_enter_reference_number));
            return;
        }

        doPaymentWithBraintreeBankTransfer();
    }

    void updateUI() {
        try {
            binding.tvBalance.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())))
                    : Utils.priceWith$(Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())),fragment.activity));
            if (((DepositFundsActivity) fragment.activity).getRedeem_Amount() != 0) {
                binding.etRedeemAmount.setText(String.format(Locale.US, "%s", ((DepositFundsActivity) fragment.activity).getRedeem_Amount()));
                makeNewRedeem(true);
            } else {
                removeRedeemNew(true);
            }
            if (binding.tvRedeem.isShown()) {//redeem is already applied then,
                binding.relRedeemBalanceView.setVisibility(View.VISIBLE);
                binding.rlAvailableBalance.setVisibility(View.GONE);
            } else {
                if (paymentMethods != null) {
                    balanceRedeemView();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCardInField() {
        binding.rlAddCard.setVisibility(View.VISIBLE);
        binding.tvAddCard.setText(String.format(Locale.US, "**** **** **** %s", (DepositFundsActivity.paymentAccount).substring((DepositFundsActivity.paymentAccount).length() - 4)));
        paymentCardToken = DepositFundsActivity.paymentAccountId;
    }

    private void balanceRedeemView() {
        if (paymentMethods != null) {
            if (((DepositFundsActivity) fragment.activity).getRemaining_Balance() > 0) {
                binding.relRedeemBalanceView.setVisibility(View.VISIBLE);
                binding.rlAvailableBalance.setVisibility(View.GONE);
                binding.llRedeemView.setVisibility(View.VISIBLE);
                binding.tvBalance.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())))
                        : Utils.priceWith$(Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())),fragment.activity));
            } else {
                binding.relRedeemBalanceView.setVisibility(View.GONE);
                binding.rlAvailableBalance.setVisibility(View.GONE);
            }
        } else {
            binding.relRedeemBalanceView.setVisibility(View.GONE);
            binding.rlAvailableBalance.setVisibility(View.GONE);
        }
    }

    private void doStripeWithPayment(String token) {
        if (!fragment.activity.isNetworkConnected())
            return;

        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);
        fragment.activity.isClickableView = true;


        if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = ((DepositFundsActivity) fragment.activity).getFees();
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = ((DepositFundsActivity) fragment.activity).getTotalPriceWithDeposit();
                String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;

                HashMap<String, String> map = new HashMap<>();
                map.put("job_post_id", projectData.jobPostId + "");
                map.put("job_post_bid_id", projectData.jpbId + "");
                map.put("redeem", getRedeemAmount() + "");
                map.put("amount", totalPriceWithDeposit + "");
                map.put("deposit_charges", String.valueOf(depositFee));

                if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                    map.put("payment_type_id", "8");
                } /*else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                    map.put("payment_type_id", "9");
                } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                    map.put("payment_type_id", "7");
                } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                    map.put("payment_type_id", "10");
                }*/

                if (!TextUtils.isEmpty(appliedPromoCode)) {
                    map.put("discount_code", appliedPromoCode);
                }
                map.put("payable_amount", getTotal());
                map.put("description", description);
                map.put("payment_platform_id", "3");
                map.put("sys_id", "6");
                map.put("card_token", TextUtils.isEmpty(token) ? "" : token);

                ApiRequest apiRequest = new ApiRequest();
                apiRequest.apiRequest(this, fragment.activity, API_DO_STRIPE_PAYMENT, true, map);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-nojom " + BuildConfig.VERSION_NAME;
                    String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                        map.put("paymentTypeID", "8");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                        map.put("paymentTypeID", "7");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                        map.put("paymentTypeID", "10");
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
                    map.put("redeem", getRedeemAmount() + "");

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }

                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiRequest(this, fragment.activity, API_DO_CUSTOM_GIGS_STRIPE_PAYMENT, true, map);
                } else {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-nojom " + BuildConfig.VERSION_NAME;
                    String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("gigPackageID", gigData.packages.get(selectedPackagePosition).id + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("packagePrice", gigData.packages.get(selectedPackagePosition).price + "");
                    map.put("quantity", gigData.quantity + "");
                    map.put("totalPrice", gigData.fixedPrice + "");
                    map.put("redeem", getRedeemAmount() + "");
                    map.put("payableAmount", getTotal().replaceAll(",", "") + "");
                    map.put("sys_id", "6");
                    map.put("card_token", TextUtils.isEmpty(token) ? "" : token);
                    if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                        map.put("paymentTypeID", "8");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                        map.put("paymentTypeID", "7");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                        map.put("paymentTypeID", "10");
                    }
                    map.put("paymentPlatformID", "3");
                    map.put("description", description);

                    if (!TextUtils.isEmpty(appliedPromoCode)) {
                        map.put("discountCode", appliedPromoCode);
                    }

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }
                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiRequest(this, fragment.activity, API_DO_GIGS_STRIPE_PACKAGE_PAYMENT, true, map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void doPaymentWithBraintree(String nonce, String token) {
        if (!fragment.activity.isNetworkConnected())
            return;

        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);
        fragment.activity.isClickableView = true;


        if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = ((DepositFundsActivity) fragment.activity).getFees();
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = ((DepositFundsActivity) fragment.activity).getTotalPriceWithDeposit();
                String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                double promoCodeDiscount = ((DepositFundsActivity) fragment.activity).getPromo_Amount();

                HashMap<String, String> map = new HashMap<>();
                map.put("job_post_id", projectData.jobPostId + "");
                map.put("job_post_bid_id", projectData.jpbId + "");
                map.put("redeem", getRedeemAmount() + "");
                map.put("amount", totalPriceWithDeposit + "");
                map.put("deposit_charges", String.valueOf(depositFee));

                if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                    map.put("payment_type_id", "8");
                } /*else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                    map.put("payment_type_id", "9");
                } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                    map.put("payment_type_id", "7");
                } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                    map.put("payment_type_id", "10");
                }*/

                if (!TextUtils.isEmpty(appliedPromoCode)) {
                    map.put("discount_code", appliedPromoCode);
                }
                map.put("payable_amount", getTotal());
                map.put("description", description);
                map.put("payment_platform_id", "3");
                map.put("sys_id", "6");
                map.put("nonce", TextUtils.isEmpty(nonce) ? "" : nonce);
                map.put("card_token", TextUtils.isEmpty(token) ? "" : token);

                ApiRequest apiRequest = new ApiRequest();
                apiRequest.apiRequest(this, fragment.activity, API_DO_BRAINTREE_PAYMENT, true, map);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-InfluencerBird " + BuildConfig.VERSION_NAME;
                    String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("deadlineID", gigData.deadlineID + "");
                    if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                        map.put("paymentTypeID", "8");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                        map.put("paymentTypeID", "7");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                        map.put("paymentTypeID", "10");
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
                    map.put("redeem", getRedeemAmount() + "");

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }

                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiRequest(this, fragment.activity, API_DO_CUSTOM_GIGS_PAYMENT, true, map);
                } else if (gigData.gigType.equalsIgnoreCase("2")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-nojom " + BuildConfig.VERSION_NAME;
                    String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("gigPackageID", gigData.packages.get(selectedPackagePosition).id + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("packagePrice", gigData.packages.get(selectedPackagePosition).price + "");
                    map.put("quantity", gigData.quantity + "");
                    map.put("totalPrice", gigData.fixedPrice + "");
                    map.put("redeem", getRedeemAmount() + "");
                    map.put("payableAmount", getTotal().replaceAll(",", "") + "");
                    map.put("sys_id", "6");
                    map.put("nonce", TextUtils.isEmpty(nonce) ? "" : nonce);
                    map.put("card_token", TextUtils.isEmpty(token) ? "" : token);
                    if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                        map.put("paymentTypeID", "8");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                        map.put("paymentTypeID", "7");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                        map.put("paymentTypeID", "10");
                    }
                    map.put("paymentPlatformID", "3");
                    map.put("description", description);
                    if (!TextUtils.isEmpty(appliedPromoCode)) {
                        map.put("discountCode", appliedPromoCode);
                    }
                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }
                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiRequest(this, fragment.activity, API_DO_GIGS_PAYMENT, true, map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void doPaymentWithBraintreeBankTransfer() {
        if (!fragment.activity.isNetworkConnected())
            return;

        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);


        MultipartBody.Part body = null;
        if (selectedFilePath != null) {
            File file = new File(selectedFilePath);
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

            RequestBody requestFile = null;
            if (mimeType != null) {
                requestFile = RequestBody.create(file, MediaType.parse(mimeType));
            }

            Headers.Builder headers = new Headers.Builder();
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"attachment_file\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = ((DepositFundsActivity) fragment.activity).getFees();
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = ((DepositFundsActivity) fragment.activity).getTotalPriceWithDeposit();
                String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                double promoCodeDiscount = ((DepositFundsActivity) fragment.activity).getPromo_Amount();


                ApiRequest apiRequest = new ApiRequest();
                apiRequest.apiBankTransfer(this, fragment.activity, API_DO_BANK_TRANSFER, body,
                        projectData.jobPostId, projectData.jpbId, Double.valueOf(getRedeemAmount()), totalPriceWithDeposit, depositFee,
                        appliedPromoCode, Double.valueOf(getTotal().replaceAll(",", "")), description, 3, 6, binding.etSenderName.getText().toString(), Integer.valueOf(binding.etBankName.getTag().toString()),
                        binding.etCardNumber.getText().toString(), binding.etTxnDate.getText().toString(), binding.etRefNo.getText().toString(),
                        binding.etNote.getText().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-InfluencerBird " + BuildConfig.VERSION_NAME;
                    String appliedPromoCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("deadlineID", gigData.deadlineID + "");
                    if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                        map.put("paymentTypeID", "8");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                        map.put("paymentTypeID", "9");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                        map.put("paymentTypeID", "7");
                    } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                        map.put("paymentTypeID", "10");
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
                    map.put("redeem", getRedeemAmount() + "");

                    if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }

                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiBankTransferGig(this, fragment.activity, API_DO_BANK_TRANSFER_GIG, body,
                            gigData.gigID, gigData.agentProfileID, gigData.deadlineID, 11, 3,
                            description, jsonArray.toString(), appliedPromoCode, Double.parseDouble(getTotal().replaceAll(",", "")), gigData.fixedPrice, binding.etSenderName.getText().toString(), Integer.valueOf(binding.etBankName.getTag().toString()),
                            binding.etCardNumber.getText().toString(), binding.etTxnDate.getText().toString(), binding.etRefNo.getText().toString(),
                            binding.etNote.getText().toString(), gigData.offerID, gigData.pk, gigData.sk + "");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    String selectedFilePath;

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 12345) {
//            DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//            if (result != null) {
//                onPaymentMethodNonceCreated(Objects.requireNonNull(result.getPaymentMethodNonce()));
//            }
        } else if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
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
        } else if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null) {

            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePath = imgPath.get(0).getPath();
                binding.relSelectedCrn.setVisibility(View.VISIBLE);
                binding.txtFileName.setText(imgPath.get(0).getName());
                return;
            }

            String path = null;
            try {
                if (data.getData() != null) {
                    path = Utils.getFilePath(fragment.activity, data.getData());
                    if (path != null) {
                        selectedFilePath = path;
                        binding.relSelectedCrn.setVisibility(View.VISIBLE);
                        binding.txtFileName.setText(path);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }


    private void thanksForPaymentDialog() {
        try {
            final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_AppCompat_Dialog);
            dialog.setTitle(null);
            if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 4) {
                dialog.setContentView(R.layout.dialog_payment_deposit_done);
            } else {
                dialog.setContentView(R.layout.dialog_payment_deposit);
            }

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
                fragment.activity.rateThisAppDialog(this);
            });

            txtThankYou.setOnClickListener(v -> {
                dialog.dismiss();
                fragment.activity.rateThisAppDialog(this);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_return_redeem:
                removeRedeemNew(false);
                break;
            case R.id.tvAddCard:
            case R.id.tvNext:
                if (binding.tvAddCard.getText().toString().trim().contains("****")) {
                    Intent intent = new Intent(DepositFundsActivity.depositActivity, CardListActivity.class);
                    intent.putExtra("is_paypal", false);
                    if (!TextUtils.isEmpty(paymentCardToken))
                        intent.putExtra("payment_account_id", paymentCardToken);
                    DepositFundsActivity.depositActivity.startActivityForResult(intent, Constants.POSITION);
                } else {
                    fragment.activity.redirectActivity(AddCardActivity.class);
                }
                break;
            case R.id.tvAddPaypal:
            case R.id.tvPaypalNext:
                Intent intent = new Intent(fragment.activity, CardListActivity.class);
                intent.putExtra("is_paypal", true);
                if (!TextUtils.isEmpty(DepositFundsActivity.paypalAccountToken))
                    intent.putExtra("payment_account_id", DepositFundsActivity.paypalAccountToken);
                fragment.activity.startActivityForResult(intent, Constants.POSITION);
                break;
            case R.id.tv_apply_redeem:
                if (!fragment.activity.isEmpty(getRedeemValue())) {
                    makeNewRedeem(false);
                } else {
                    fragment.activity.validationError(fragment.activity.getString(R.string.enter_your_redeem_amount));
                }
                break;
            case R.id.tv_promocode:
                if (getTotal().equalsIgnoreCase("0.0") || getTotal().equalsIgnoreCase("0.00") ||
                        getTotal().equalsIgnoreCase("0")) {
                    fragment.activity.validationError(fragment.activity.getString(R.string.you_cannot_apply_a_promo_code_if_amount_is) + " $0.00");
                    return;
                }
                String appliedCode = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                ((DepositFundsActivity) fragment.activity).enterPromoCodeDialog(0, appliedCode);
                break;
            case R.id.tv_edit_promo:
                String appliedCode1 = ((DepositFundsActivity) fragment.activity).appliedPromoCode;
                if (!TextUtils.isEmpty(appliedCode1)) {
                    ((DepositFundsActivity) fragment.activity).checkPromoCodeApi("", 0);
                }
                break;
            case R.id.txt_attach:
                selectFileDialog(1234);
                break;
            case R.id.et_bankName:
                showBankNameDialog();
                break;
            case R.id.imgDelete:
                binding.relSelectedCrn.setVisibility(View.GONE);
                binding.txtFileName.setText("");
                selectedFilePath = null;
                break;
        }
    }

    private String getTotal() {
        if (fragment.activity.getCurrency().equals("SAR")) {
            return Utils.priceWithoutSAR(fragment.activity, binding.tvTotal.getText().toString().trim());
        } else {
            return Utils.priceWithout$(binding.tvTotal.getText().toString().trim());
        }
    }

    private void setTotal(Double amount) {
        binding.tvTotal.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(amount)) + " " + fragment.getString(R.string.sar)
                : "$" + Utils.decimalFormat(String.valueOf(amount)));
        binding.etTxnAmount.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(amount)) + " " + fragment.getString(R.string.sar)
                : "$" + Utils.decimalFormat(String.valueOf(amount)));
    }

    private String getRedeemValue() {
        return Objects.requireNonNull(binding.etRedeemAmount.getText()).toString().trim();
    }

    private String getRedeemAmount() {
        if (fragment.activity.getCurrency().equals("SAR")) {
            return Utils.priceWithoutSAR(fragment.activity, binding.tvRedeem.getText().toString().trim());
        } else {
            return Utils.priceWithout$(binding.tvRedeem.getText().toString().trim());
        }
    }

    void setPromoCode(String promoCode, double discountValue, double totalAmt) {
        try {
            double actualUserBalance = (Double) binding.tvBalance.getTag();
            double actualAmount;
            double enteredRedeem = Double.parseDouble(Utils.priceWithout$(getRedeemValue()));
            if (fragment.activity.getCurrency().equals("SAR")) {
                enteredRedeem = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, getRedeemValue()));
            }
            if (((DepositFundsActivity) fragment.activity).getRedeem_Amount() == 0) {
                enteredRedeem = 0;
            }
            if (!TextUtils.isEmpty(promoCode)) {
                if (((DepositFundsActivity) fragment.activity).getPromo_Amount() != 0) {
                    actualAmount = totalAmt - discountValue;
                } else {
                    actualAmount = totalAmt;
                }

                if (enteredRedeem > 0) {//redeem is applied
                    actualAmount = actualAmount - enteredRedeem;

                    if (discountValue > 0 && Double.parseDouble((getTotal())) < discountValue) {
                        double extraAmt = discountValue - Double.parseDouble((getTotal()));//reduce total amount from discount value
                        double amt = enteredRedeem - extraAmt;//reduce remaining amount from entered redeem
                        binding.etRedeemAmount.setText(Utils.decimalFormat(String.valueOf(amt)) + "");
                        binding.tvRedeem.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(amt)))
                                : Utils.priceWith$(Utils.decimalFormat(String.valueOf(amt)),fragment.activity));
                        //update user balance
                        ((DepositFundsActivity) fragment.activity).setRemaining_Balance(actualUserBalance - Math.abs(amt));
                        binding.tvBalance.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())))
                                : Utils.priceWith$(Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())),fragment.activity));
                        ((DepositFundsActivity) fragment.activity).setRedeem_Amount(Math.abs(amt));//set redeem amount after apply entered successfully
                    }
                }

                if (actualAmount < 0) {
                    actualAmount = 0;
                }

                if (actualAmount == 0 && enteredRedeem == 0) {
                    setTotal(round(((DepositFundsActivity) fragment.activity).getFees(), 2));
                } else if (actualAmount == 0 && enteredRedeem > 0 && discountValue > 0) { //
                    setTotal(round(actualAmount, 2));
                } else {
                    setTotal(round(actualAmount + ((DepositFundsActivity) fragment.activity).getFees(), 2));
                }

                ((DepositFundsActivity) fragment.activity).setActual_Amount(actualAmount);

                ClickableSpan reffCodeClick = new ClickableSpan() {
                    @Override
                    public void onClick(@NotNull View view) {

                    }
                };
                binding.rlPromocode.setVisibility(View.VISIBLE);
                binding.tvPromocodeTitle.setText(String.format(Locale.US, fragment.activity.getString(R.string.promo_code) + " %s", promoCode));
                binding.tvPromoAmount.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.decimalFormat(String.valueOf(discountValue))));
                Utils.makeLinks(binding.tvPromocodeTitle, new String[]{promoCode}, new ClickableSpan[]{reffCodeClick});
            } else {
                binding.rlPromocode.setVisibility(View.GONE);
                binding.tvPromoAmount.setText(fragment.activity.getCurrency().equals("SAR") ? "0 " + fragment.getString(R.string.sar) : "$0");
                binding.tvPromocodeTitle.setText(fragment.getString(R.string.promo_code));

                double promoAmnt = ((DepositFundsActivity) fragment.activity).getPromo_Amount();
                if (promoAmnt != 0) {
                    actualAmount = totalAmt - promoAmnt;
                } else {
                    actualAmount = totalAmt;
                }

                if (enteredRedeem > 0) {//redeem is applied
                    actualAmount = actualAmount - enteredRedeem;
                }

                if (actualAmount < 0) {
                    actualAmount = 0;
                }
                setTotal(round(actualAmount + ((DepositFundsActivity) fragment.activity).getFees(), 2));
                ((DepositFundsActivity) fragment.activity).setActual_Amount(actualAmount);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeNewRedeem(boolean isUptodate) {
        double actualAmount;
        double promoDiscount = ((DepositFundsActivity) fragment.activity).getPromo_Amount();
        double actualUserBalance = (Double) binding.tvBalance.getTag();

        double enteredRedeem = Double.parseDouble(Utils.priceWithout$(getRedeemValue()));
        if (fragment.activity.getCurrency().equals("SAR")) {
            enteredRedeem = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, getRedeemValue()));
        }

        if (!isUptodate && enteredRedeem == 0) {
            fragment.activity.validationError(fragment.activity.getString(R.string.redeem_amount_must_be_greater_than_0));
            return;
        }

        if (!isUptodate && enteredRedeem > actualUserBalance) {//check redeem with actual balance
            fragment.activity.validationError(fragment.activity.getString(R.string.you_cannot_redeem_amount_more_than_your_balance));
            return;
        }

        if (promoDiscount > 0) {//applied promo-code then minus discount value from actual amount
            double total = Double.parseDouble(Utils.priceWithout$(((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
            if (fragment.activity.getCurrency().equals("SAR")) {
                total = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, ((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
            }
            actualAmount = total - promoDiscount;
        } else {//if not then get actual amount
            actualAmount = Double.parseDouble(Utils.priceWithout$(((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
            if (fragment.activity.getCurrency().equals("SAR")) {
                actualAmount = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, ((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
            }
        }

        if (actualAmount < 0 && enteredRedeem > 0) {
            actualAmount = ((DepositFundsActivity) fragment.activity).getFees();
        } else if (actualAmount < 1 && enteredRedeem > 0 && promoDiscount > 0) {
            actualAmount = ((DepositFundsActivity) fragment.activity).getFees();
        }

        if (!isUptodate && enteredRedeem > actualAmount) {//check redeem with actual amount
            fragment.activity.validationError(fragment.activity.getString(R.string.you_cannot_redeem_amount_more_than_total_amount));
            return;
        }

        if (enteredRedeem > 0) {//redeem is applied
            actualAmount = actualAmount - enteredRedeem;
        }

        if (actualAmount < 0) {
            actualAmount = 0;
        }

        ((DepositFundsActivity) fragment.activity).setActual_Amount(actualAmount);//set price after calculate
        ((DepositFundsActivity) fragment.activity).setRedeem_Amount(enteredRedeem);//set redeem amount after apply entered successfully

        binding.rlRedeem.setVisibility(View.VISIBLE);
        binding.tvRedeem.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(enteredRedeem)))
                : Utils.priceWith$(Utils.decimalFormat(String.valueOf(enteredRedeem)),fragment.activity));

        setTotal(round(actualAmount, 2));

        if (!isUptodate) {
            ((DepositFundsActivity) fragment.activity).setRemaining_Balance(actualUserBalance - enteredRedeem);
        }
        binding.tvBalance.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())))
                : Utils.priceWith$(Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())),fragment.activity));
    }

    private void removeRedeemNew(boolean isUptoDate) {
        try {
            double actualAmount;
            double promoDiscount = ((DepositFundsActivity) fragment.activity).getPromo_Amount();
            double actualUserBalance = (Double) binding.tvBalance.getTag();

            binding.etRedeemAmount.setText("");
            binding.relRedeemBalanceView.setVisibility(View.VISIBLE);
            binding.rlAvailableBalance.setVisibility(View.GONE);
            binding.llRedeemView.setVisibility(View.VISIBLE);
            binding.rlRedeem.setVisibility(View.GONE);

            if (promoDiscount > 0) {//applied promo-code then minus discount value from actual amount
                double total = Double.parseDouble(Utils.priceWithout$(((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
                if (fragment.activity.getCurrency().equals("SAR")) {
                    total = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, ((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
                }
                actualAmount = total - promoDiscount;
            } else {//if not then get actual amount
                actualAmount = Double.parseDouble(Utils.priceWithout$(((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
                if (fragment.activity.getCurrency().equals("SAR")) {
                    actualAmount = Double.parseDouble(Utils.priceWithoutSAR(fragment.activity, ((DepositFundsActivity) fragment.activity).getTotalPriceForCheckReedim()));
                }
            }

            if (actualAmount < 0 && promoDiscount > 0) {
                actualAmount = ((DepositFundsActivity) fragment.activity).getFees();
            }

            ((DepositFundsActivity) fragment.activity).setActual_Amount(actualAmount);//after remove redeem set amount
            setTotal(round(actualAmount, 2));

            if (!isUptoDate) {
                ((DepositFundsActivity) fragment.activity).setRemaining_Balance(actualUserBalance);//actual user balance
            }
            binding.tvBalance.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())))
                    : Utils.priceWith$(Utils.decimalFormat(String.valueOf(((DepositFundsActivity) fragment.activity).getRemaining_Balance())),fragment.activity));
            ((DepositFundsActivity) fragment.activity).setRedeem_Amount(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCardList() {
        if (!fragment.activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_USER_WALLET_LIST, false, null);

    }

    private void getStripeCardList() {
        if (!fragment.activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_STRIPE_CARD_LIST, false, null);

    }

    @Override
    public void onClickRateDialog(boolean isCancelled) {
        Profile profileData = Preferences.getProfileData(fragment.activity);
        profileData.clientBalance = ((DepositFundsActivity) fragment.activity).getRemaining_Balance();
        Preferences.setProfileData(fragment.activity, profileData);
        fragment.activity.gotoMainActivity(Constants.TAB_JOB_LIST);
    }

    /*@Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        String nonce = paymentMethodNonce.getNonce();
        paymentCardToken = "";
        doPaymentWithBraintree(nonce, paymentCardToken);
    }*/

    public void openConfirmationDialog() {
        final Dialog dialog = new Dialog(fragment.requireActivity());
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(fragment.getString(R.string.pay_with_card));
        txtDesc.setText(fragment.getString(R.string.are_you_sure_you_want_to_pay_with_card));
        btnNo.setText(fragment.getString(R.string.cancel));
        btnYes.setText(fragment.getString(R.string.Ok));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            doPaymentWithBraintree("", paymentCardToken);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public void openPaypalConfirmationDialog() {

        final Dialog dialog = new Dialog(fragment.requireActivity());
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(fragment.getString(R.string.pay_with_paypal));
        txtDesc.setText(fragment.getString(R.string.are_you_sure_you_want_to_pay_with_paypal));
        btnNo.setText(fragment.getString(R.string.cancel));
        btnYes.setText(fragment.getString(R.string.Ok));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            try {
                if (Double.parseDouble(getTotal()) == 0.0) {
                    doPaymentWithBraintree("", "");
                } else {
                    doPaymentWithBraintree("", DepositFundsActivity.paypalAccountToken);
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

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        fragment.activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_DO_BRAINTREE_PAYMENT) || url.equalsIgnoreCase(API_DO_STRIPE_PAYMENT)
                || url.equalsIgnoreCase(API_DO_BANK_TRANSFER)|| url.equalsIgnoreCase(API_DO_BANK_TRANSFER_GIG)) {
            Preferences.writeString(fragment.activity, REFERRAL_ID_FROM_LINK, "");
            purchaseModel = PurchaseModel.getPurchase(responseBody);
            if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                trackAppsFlayerPaymentJobPostEvent(fragment.activity, "Card");
                Utils.trackBranchAppsEvent(fragment.activity, "Card_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                trackAppsFlayerPaymentJobPostEvent(fragment.activity, "Paypal");
                Utils.trackBranchAppsEvent(fragment.activity, "Paypal_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                trackAppsFlayerPaymentJobPostEvent(fragment.activity, "Google");
                Utils.trackBranchAppsEvent(fragment.activity, "Google_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                trackAppsFlayerPaymentJobPostEvent(fragment.activity, "Venmo");
                Utils.trackBranchAppsEvent(fragment.activity, "Venmo_Payment_Successfull\n Amount " + getTotal());
            }

            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
            ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.GONE);

            thanksForPaymentDialog();
        } else if (url.equalsIgnoreCase(API_DO_GIGS_PAYMENT) || url.equalsIgnoreCase(API_DO_CUSTOM_GIGS_PAYMENT)
                || url.equalsIgnoreCase(API_DO_CUSTOM_GIGS_STRIPE_PAYMENT) || url.equalsIgnoreCase(API_DO_GIGS_STRIPE_PACKAGE_PAYMENT)) {
            purchaseModel = PurchaseModel.getPurchase(responseBody);
            Preferences.writeString(fragment.activity, REFERRAL_ID_FROM_LINK, "");
            if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                trackAppsFlayerPaymentGigEvent(fragment.activity, "Card");
                Utils.trackBranchAppsEvent(fragment.activity, "Gig_Card_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                trackAppsFlayerPaymentGigEvent(fragment.activity, "Paypal");
                Utils.trackBranchAppsEvent(fragment.activity, "Gig_Paypal_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                trackAppsFlayerPaymentGigEvent(fragment.activity, "Google");
                Utils.trackBranchAppsEvent(fragment.activity, "Gig_Google_Payment_Successfull\n Amount " + getTotal());
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                trackAppsFlayerPaymentGigEvent(fragment.activity, "Venmo");
                Utils.trackBranchAppsEvent(fragment.activity, "Gig_Venmo_Payment_Successfull\n Amount " + getTotal());
            }
            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
            ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.GONE);

            if (gigData.isFromOffer) {
                try {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("partitionKey", "#message#" + fragment.activity.getUserID() + "-" + gigData.receiverId);
                    jsonData.put("senderId", fragment.activity.getUserID());
                    jsonData.put("offerStatus", 2);
                    jsonData.put("receiverId", gigData.receiverId);
                    jsonData.put("messageId", gigData.messageId);
                    jsonData.put("price", gigData.fixedPrice);
                    jsonData.put("contractID", purchaseModel.contractID);
                    fragment.activity.mSocket.emit("sendLiveOfferStatus", jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            thanksForPaymentDialog();
        } else if (url.equalsIgnoreCase(API_USER_WALLET_LIST) || url.equalsIgnoreCase(API_GET_STRIPE_CARD_LIST)) {
            paymentBraintreeCards = PaymentBraintreeCards.getPaymentCards(responseBody);

            if (paymentBraintreeCards != null && paymentBraintreeCards.cardPaypal != null && paymentBraintreeCards.cardPaypal.cards != null) {//cards
                if (paymentBraintreeCards.cardPaypal.cards.size() > 0) {
                    if (!fragment.activity.isEmpty(DepositFundsActivity.paymentAccount)) {
                        updateCardInField();
                    } else {
                        binding.tvAddCard.setText(String.format(Locale.US, "**** **** **** %s", paymentBraintreeCards.cardPaypal.cards.get(0).lastDigit));
                        paymentCardToken = paymentBraintreeCards.cardPaypal.cards.get(0).token;
                        DepositFundsActivity.cardNumber = paymentBraintreeCards.cardPaypal.cards.get(0).card_number;
                        DepositFundsActivity.cardExp = paymentBraintreeCards.cardPaypal.cards.get(0).expDate;
                        DepositFundsActivity.paymentAccountId = paymentBraintreeCards.cardPaypal.cards.get(0).token;
                    }
                } else {
                    try {
                        if (!fragment.activity.isEmpty(DepositFundsActivity.paymentAccount)) {
                            updateCardInField();
                        } else {
                            binding.tvAddCard.setText("");
                            binding.tvAddCard.setHint(fragment.getString(R.string.add_new_card));
                            paymentCardToken = "";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                binding.tvAddCard.setText("");
                binding.tvAddCard.setHint(fragment.getString(R.string.add_new_card));
            }

            //Paypal accounts
            DepositFundsActivity.paypalAccountList = new ArrayList<>();
            if (paymentBraintreeCards != null && paymentBraintreeCards.cardPaypal != null && paymentBraintreeCards.cardPaypal.paypal != null) {
                for (Cardlist.Paypal paypal : paymentBraintreeCards.cardPaypal.paypal) {
                    BraintreeCard.Data data = new BraintreeCard.Data();
                    data.paypal = paypal;
                    DepositFundsActivity.paypalAccountList.add(data);
                }
                DepositFundsActivity.paypalAccountToken = paymentBraintreeCards.cardPaypal.paypal.get(0).token;
                DepositFundsActivity.paypalAccountEmail = paymentBraintreeCards.cardPaypal.paypal.get(0).account;
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        fragment.activity.isClickableView = false;
        fragment.activity.toastMessage(message);
//        fragment.activity.finish();
    }

    private void trackAppsFlayerPaymentJobPostEvent(Context context, String eventName) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "JobId-" + projectData.jobPostId + "");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, projectData.getName(fragment.activity.getLanguage()));
            bundle.putString(FirebaseAnalytics.Param.AFFILIATION, "PlayStore");
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
            bundle.putString(FirebaseAnalytics.Param.SHIPPING, "0");
            bundle.putString(FirebaseAnalytics.Param.TAX, "0");
            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, purchaseModel.transactionID);
            bundle.putDouble(FirebaseAnalytics.Param.VALUE, Double.parseDouble(getTotal().replace(",", "")));
            bundle.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, eventName);
            bundle.putString(FirebaseAnalytics.Param.COUPON, ((DepositFundsActivity) fragment.activity).appliedPromoCode + "");
            bundle.putString(FirebaseAnalytics.Param.DISCOUNT, ((DepositFundsActivity) fragment.activity).getPromo_Amount() + "");
            Task24Application.getInstance().getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.PURCHASE, bundle);

            Bundle bundle1 = new Bundle();
            bundle1.putString("app_name", fragment.activity.getResources().getString(R.string.app_name));
            bundle1.putString("user_id", fragment.activity.getUserData() != null ? String.valueOf(fragment.activity.getUserData().id) : "");
            bundle1.putString("item_id", projectData.jobPostId + "");
            bundle1.putString("item_category", "Job");
            bundle1.putString("item_amount", getTotal());
            bundle1.putString("item_title", projectData.title);
            bundle1.putString("main_category", projectData.getSocialPlatformName(fragment.activity.getLanguage()));
            bundle1.putString("sub_category", projectData.getName(fragment.activity.getLanguage()));
            if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                bundle1.putString("payment_type", "Card");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                bundle1.putString("payment_type", "PayPal");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                bundle1.putString("payment_type", "Google");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                bundle1.putString("payment_type", "Vemmo");
            }
            bundle1.putString("affiliation", "PlayStore");
            bundle1.putString("transaction_id", purchaseModel.transactionID);
            bundle1.putString("discount", ((DepositFundsActivity) fragment.activity).getPromo_Amount() + "");
            bundle1.putString("coupon", ((DepositFundsActivity) fragment.activity).appliedPromoCode + "");
            bundle1.putString("payment_gateway", Task24Application.getInstance().paymentCardType);
            Task24Application.getInstance().getFirebaseAnalytics().logEvent("product_purchase", bundle1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void trackAppsFlayerPaymentGigEvent(Context context, String eventName) {
        try {
            Bundle bundle = new Bundle();
            if (gigData.gigType.equalsIgnoreCase("1")) {
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "CustomGigContractId-" + purchaseModel.contractID);
            } else if (gigData.gigType.equalsIgnoreCase("2")) {
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GigContractId-" + purchaseModel.contractID + "");
            } else {
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GigOfferId-" + purchaseModel.contractID + "");
            }
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, gigData.gigTitle + "");
            bundle.putString(FirebaseAnalytics.Param.AFFILIATION, "PlayStore");
            bundle.putString(FirebaseAnalytics.Param.COUPON, ((DepositFundsActivity) fragment.activity).appliedPromoCode + "");
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
            bundle.putString(FirebaseAnalytics.Param.SHIPPING, "0");
            bundle.putString(FirebaseAnalytics.Param.TAX, "0");
            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, purchaseModel.transactionID);
            bundle.putDouble(FirebaseAnalytics.Param.VALUE, Double.parseDouble(getTotal().replace(",", "")));
            bundle.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, eventName + "");
            bundle.putString(FirebaseAnalytics.Param.DISCOUNT, ((DepositFundsActivity) fragment.activity).getPromo_Amount() + "");

            Task24Application.getInstance().getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.PURCHASE, bundle);

            Bundle bundle1 = new Bundle();
            bundle1.putString("app_name", fragment.activity.getResources().getString(R.string.app_name));
            bundle1.putString("user_id", fragment.activity.getUserData() != null ? String.valueOf(fragment.activity.getUserData().id) : "");
            bundle1.putString("item_id", purchaseModel.contractID + "");
            bundle1.putString("item_category", "Gig");
            bundle1.putString("item_amount", getTotal());
            bundle1.putString("item_title", gigData.gigTitle);
            bundle1.putString("main_category", gigData.parentCategoryNamme);
            bundle1.putString("sub_category", gigData.subCategoryNamme);
            if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 0) {//card
                bundle1.putString("payment_type", "Card");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 1) {//paypal
                bundle1.putString("payment_type", "PayPal");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 2) {//google
                bundle1.putString("payment_type", "Google");
            } else if (((DepositFundsActivity) fragment.activity).getSelectedTab() == 3) {//venmo
                bundle1.putString("payment_type", "Vemmo");
            }
            bundle1.putString("affiliation", "PlayStore");
            bundle1.putString("transaction_id", purchaseModel.transactionID);
            bundle1.putString("discount", ((DepositFundsActivity) fragment.activity).getPromo_Amount() + "");
            bundle1.putString("coupon", ((DepositFundsActivity) fragment.activity).appliedPromoCode + "");
            bundle1.putString("payment_gateway", Task24Application.getInstance().paymentCardType);
            Task24Application.getInstance().getFirebaseAnalytics().logEvent("product_purchase", bundle1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePaypalAccountListUI() {
        binding.rlAddCard.setVisibility(View.GONE);
        binding.linBanTransfer.setVisibility(View.GONE);
        if (DepositFundsActivity.paypalAccountList != null && DepositFundsActivity.paypalAccountList.size() > 0) {
            if (!TextUtils.isEmpty(DepositFundsActivity.paypalAccountEmail)) {//selected paypal account
                binding.rlAddPaypal.setVisibility(View.VISIBLE);
                binding.tvAddPaypal.setText(DepositFundsActivity.paypalAccountEmail);
            } else {
                if (DepositFundsActivity.paypalAccountList.get(0).paypal != null) {
                    binding.rlAddPaypal.setVisibility(View.VISIBLE);
                    binding.tvAddPaypal.setText(DepositFundsActivity.paypalAccountList.get(0).paypal.account);
                } else {
                    binding.tvAddPaypal.setText("");
                    binding.tvAddPaypal.setHint(fragment.getString(R.string.add_new_paypal));
                    DepositFundsActivity.paypalAccountToken = "";
                }
            }

        } else {
            if (!TextUtils.isEmpty(DepositFundsActivity.paypalAccountEmail)) {//selected paypal account
                binding.rlAddPaypal.setVisibility(View.VISIBLE);
                binding.tvAddPaypal.setText(DepositFundsActivity.paypalAccountEmail);
            } else {
                binding.tvAddPaypal.setHint(fragment.getString(R.string.add_new_paypal));
                binding.rlAddPaypal.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideCardUI() {
        binding.rlAddCard.setVisibility(View.GONE);
        binding.rlAddPaypal.setVisibility(View.GONE);
        binding.linBanTransfer.setVisibility(View.GONE);
    }

    public void hideBankCardUI() {
        binding.rlAddCard.setVisibility(View.GONE);
        binding.rlAddPaypal.setVisibility(View.GONE);
        binding.linBanTransfer.setVisibility(View.VISIBLE);
    }

    public void openGoogleConfirmationDialog() {

        final Dialog dialog = new Dialog(fragment.requireActivity());
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(fragment.getString(R.string.pay_with_google_pay));
        txtDesc.setText(fragment.getString(R.string.are_you_sure_you_want_to_pay_with_googlepay));
        btnNo.setText(fragment.getString(R.string.cancel));
        btnYes.setText(fragment.getString(R.string.Ok));

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
                        doPaymentWithBraintree("", "");
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

    public void requestPaymentStripe() {

        Optional<JSONObject> paymentDataRequestJson = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), fragment.activity.getUserData().username, projectData.jobPostId);
            } else {
                paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(getTotal(), fragment.activity.getUserData().username, gigData.gigID);
            }

            if (!paymentDataRequestJson.isPresent()) {
                return;
            }

            PaymentDataRequest request =
                    PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

            // Since loadPaymentData may show the UI asking the user to select a payment method, we use
            // AutoResolveHelper to wait for the user interacting with it. Once completed,
            // onActivityResult will be called with the result.
            AutoResolveHelper.resolveTask(
                    mPaymentsClient.loadPaymentData(request),
                    fragment.activity, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    private void requestPayment() {
        double amount = 0.0, depositFee = 0.0;
        if (!((DepositFundsActivity) fragment.activity).isFromGig()) {
            amount = projectData.fixedPrice;
            depositFee = projectData.jobPostContracts.depositCharges;
        } else {
            amount = gigData.fixedPrice;
            depositFee = gigData.depositCharges;
        }
        double percentage = (amount * depositFee) / 100;
//        double depositCharges = Math.max(percentage, depositFee);
        double promoCodeDiscount = ((DepositFundsActivity) fragment.activity).getPromo_Amount();
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

        GooglePayRequest googlePayRequest = new GooglePayRequest();
        googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder()
                .setTotalPrice(finalAmount+"")
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setCurrencyCode(fragment.activity.getCurrency().equals("SAR") ? "SAR" : "USD")
                .build());
        googlePayRequest.setBillingAddressRequired(true);

        DropInRequest dropInRequest = new DropInRequest();
        dropInRequest.setGooglePayRequest(googlePayRequest);
    }

    public void checkGooglePayVisibility() {
        /*try {
            String bToken = ((DepositFundsActivity) fragment.activity).braintreeToken;
            if (!TextUtils.isEmpty(bToken)) {
                mBraintreeFragment = BraintreeFragment.newInstance(fragment.activity, bToken);
                mBraintreeFragment.addListener(this);
            }
//
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
     */
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
        task.addOnCompleteListener(fragment.activity,
                task1 -> {
                    if (task1.isSuccessful()) {
                        setGooglePayAvailable(task1.getResult());
                    } else {
                        Log.w("isReadyToPay failed", task1.getException());
                    }
                });
    }

    void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.VISIBLE);
        } else {
            ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.GONE);
            fragment.activity.toastMessage(fragment.getString(R.string.googlepay_status_unavailable));
        }
    }

    public void openVenmoConfirmationDialog() {

        final Dialog dialog = new Dialog(fragment.requireActivity());
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_open_website);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        TextView txtTitle = dialog.findViewById(R.id.tv_title);
        TextView txtDesc = dialog.findViewById(R.id.tvWebsite);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);
        txtTitle.setText(fragment.getString(R.string.pay_with_venmo));
        txtDesc.setText(fragment.getString(R.string.are_you_sure_you_want_to_pay_with_venmo));
        btnNo.setText(fragment.getString(R.string.cancel));
        btnYes.setText(fragment.getString(R.string.Ok));

        btnNo.setOnClickListener(v -> dialog.dismiss());

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            try {
                if (Double.parseDouble(getTotal()) == 0.0) {
                    doPaymentWithBraintree("", "");
                } else {
                    String bToken = ((DepositFundsActivity) fragment.activity).braintreeToken;
                    if (!TextUtils.isEmpty(bToken)) {
//                        mBraintreeFragment = BraintreeFragment.newInstance(fragment.activity, bToken);
//                        mBraintreeFragment.addListener(this);
//                        mBraintreeFragment.addListener(this);
//                        Venmo.authorizeAccount(mBraintreeFragment, false);
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

    private void selectFileDialog(int code) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(false, code);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(false, code));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(true, code);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(true, code));
            }
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public void checkPermission(final boolean isDocument, int code) {
        try {
            Dexter.withActivity(fragment.activity)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                if (isDocument) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        fragment.activity.openDocuments(fragment.activity, 1, code);
                                    } else {
                                        Intent intent = new Intent(fragment.activity, NormalFilePickActivity.class);
                                        intent.putExtra(Constant.MAX_NUMBER, 1);
                                        intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                        fragment.activity.startActivityForResult(intent, code);
                                    }
                                } else {
                                    Intent intent = new Intent(fragment.activity, ImagePickActivity.class);
                                    intent.putExtra(VideoPickActivity.IS_NEED_CAMERA, true);
                                    intent.putExtra(Constant.MAX_NUMBER, 1);
                                    intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                                    fragment.activity.startActivityForResult(intent, code);
                                }
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                fragment.activity.toastMessage(fragment.activity.getString(R.string.please_give_permission));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Dialog dialogBankName;
    PaymentAdapter paymentAdapter;

    void showBankNameDialog() {
        dialogBankName = new Dialog(fragment.activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialogBankName.setTitle(null);
        dialogBankName.setContentView(R.layout.dialog_item_select_black);
        dialogBankName.setCancelable(true);

        LinearLayout llButton = dialogBankName.findViewById(R.id.ll_bottom);
        TextView tvCancel = dialogBankName.findViewById(R.id.tv_cancel);
        TextView tvApply = dialogBankName.findViewById(R.id.tv_apply);
        final EditText etSearch = dialogBankName.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialogBankName.findViewById(R.id.rv_items);

        etSearch.setVisibility(View.GONE);
        llButton.setVisibility(View.GONE);

        rvTypes.setLayoutManager(new LinearLayoutManager(fragment.activity));
        try {
            paymentAdapter = new PaymentAdapter(fragment.activity, bankList, this);
            paymentAdapter.setSelectedLanguageList(binding.etBankName.getText().toString());
            rvTypes.setAdapter(paymentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvCancel.setOnClickListener(v -> {
            dialogBankName.dismiss();
        });

        tvApply.setOnClickListener(v -> {
            dialogBankName.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialogBankName.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialogBankName.show();
        dialogBankName.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBankName.getWindow().setAttributes(lp);
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(fragment.activity, etSearch)));
        etSearch.requestFocus();
    }

    @Override
    public void onClickBank(Banks.Data bankName, int adapterPos) {
        binding.etBankName.setText(bankName.getName(fragment.activity.getLanguage()));
        binding.etBankName.setTag("" + bankName.id);
        if (dialogBankName != null) {
            dialogBankName.dismiss();
        }
    }
}
