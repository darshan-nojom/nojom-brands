package com.nojom.client.ui.clientprofile;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_CHECK_PROMO_CODE;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_GET_CLIENT_PROFILE;
import static com.nojom.client.util.Constants.API_SEND_EMAIL_VERIFICATION;
import static com.nojom.client.util.Constants.REFERRAL_ID_FROM_LINK;

import android.app.Application;
import android.app.Dialog;
import android.button.CustomButton;
import android.content.Intent;
import android.edittext.CustomEditText;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityDepositFundsCopyBinding;
import com.nojom.client.fragment.payment.CreditCardDepositFragment;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.PromoCode;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

class DepositFundsActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    public int isPaypal = 0;
    public boolean isFromGig = false;
    public int selectedPackagePosition = 0;
    private final ActivityDepositFundsCopyBinding binding;
    private final BaseActivity activity;
    private ProjectByID projectData;
    private ExpertGigDetail gigData;
    private double userActualBalance = 0, remainingBalance = 0;
    private int selectedFragment;
    private CreditCardDepositFragment creditCardDepositFragment;
    private boolean isVerifyEmail = false, isFrom1TimeVerifyMail = false;
    private CircularProgressBar progressBarEmail;
    private CustomButton btnSendEmail;
    private Dialog dialogEmail;
    private PaymentMethods paymentMethod;

    DepositFundsActivityVM(Application application, ActivityDepositFundsCopyBinding depositFundsBinding, BaseActivity depositFundsActivity) {
        super(application);
        binding = depositFundsBinding;
        activity = depositFundsActivity;
        initData();
    }

    public double getUserActualBalance() {
        return userActualBalance;
    }

    public void setUserActualBalance(double userActualBalance) {
        this.userActualBalance = userActualBalance;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    String getSignUpRefCodeClickOnLink() {
        return Preferences.readString(activity, REFERRAL_ID_FROM_LINK, null);
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvPay.setOnClickListener(this);

        generateBraintreeToken();

        if (activity.getIntent() != null) {
            isFromGig = activity.getIntent().getBooleanExtra(Constants.IS_FROM_GIG, false);
        }

        if (!isFromGig) {
            if (activity.getIntent() != null) {
                projectData = (ProjectByID) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
            }

            if (projectData != null) {
                binding.tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : activity.getString(R.string.dollar)+" %s", activity.get2DecimalPlaces(getTotalPrice())));

                ((DepositFundsActivity) activity).setActual_Amount(getTotalPrice());
            }
        } else {
            if (activity.getIntent() != null) {
                gigData = (ExpertGigDetail) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
                selectedPackagePosition = activity.getIntent().getIntExtra(Constants.SELECTED_PACKAGE_POS, 0);
            }
            if (gigData != null) {
                binding.tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : activity.getString(R.string.dollar)+" %s", activity.get2DecimalPlaces(getTotalPrice())));

                ((DepositFundsActivity) activity).setActual_Amount(getTotalPrice());
            }
        }

        setupTabs();
//        notesDialog();

        paymentMethod = Preferences.getPaymentMethod(activity);
        if (getRemainingBalance() > 0) {
            setRemainingBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity, getRemainingBalance()) : Utils.priceWithout$(getRemainingBalance())));
        } else {
            setUserActualBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity, paymentMethod.clientBalance) : Utils.priceWithout$(paymentMethod.clientBalance)));
            setRemainingBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity
                    , paymentMethod.clientBalance) : Utils.priceWithout$(paymentMethod.clientBalance)));
        }

        try {
            if (paymentMethod.paymentMethod.size() > 0) {
                for (int i = 0; i < paymentMethod.paymentMethod.size(); i++) {
                    switch (paymentMethod.paymentMethod.get(i).name) {
                        case "Bank Card":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.sbCard.setVisibility(View.VISIBLE);
                            } else {
                                binding.sbCard.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentCardType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                        case "PayPal":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.sbPayPal.setVisibility(View.VISIBLE);
                            } else {
                                binding.sbPayPal.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentPayPalType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                        case "Google Pay":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.sbGooglePay.setVisibility(View.VISIBLE);
                            } else {
                                binding.sbGooglePay.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentGoogleType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                        case "Venmo":
                            if (paymentMethod.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                binding.sbVenmo.setVisibility(View.VISIBLE);
                            } else {
                                binding.sbVenmo.setVisibility(View.GONE);
                            }
                            Task24Application.getInstance().paymentVenmoType = paymentMethod.paymentMethod.get(i).paymentMethod;
                            break;
                    }
                }
                binding.sbBt.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    CustomTextView getTvPay() {
        return binding.tvPay;
    }

    int getTab() {
        return binding.segmentGroup.getPosition();
    }

    CircularProgressBar getProgressbar() {
        return binding.progressBar;
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

    double getTotalPriceForCheckReedim() {
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

    void setTotalAmount(String amount) {
        binding.tvBudget.setText(amount);
    }

    private void setupTabs() {
        binding.segmentGroup.setOnPositionChangedListener(position -> {
            switch (position) {
                case 0:
                    isPaypal = 0;
                    creditCardDepositFragment.updateAccountUI();
                    binding.tvPay.setText(activity.getString(R.string.pay_with_card));
                    break;
                case 1:
                    isPaypal = 1;
                    creditCardDepositFragment.updatePaypalAccountList();
                    binding.tvPay.setText(activity.getString(R.string.pay_with_paypal));
                    break;
                case 2:
                    isPaypal = 2;
                    creditCardDepositFragment.hideCardUI();
                    binding.tvPay.setText(activity.getString(R.string.pay_with_google_pay));
                    break;
                case 3:
                    isPaypal = 3;
                    creditCardDepositFragment.hideCardUI();
                    binding.tvPay.setText(activity.getString(R.string.pay_with_venmo));
                    break;
                case 4:
                    isPaypal = 4;
                    creditCardDepositFragment.hideBankCardUI();
                    binding.tvPay.setText(activity.getString(R.string.pay_with_card));
                    break;

            }
        });

        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, creditCardDepositFragment = new CreditCardDepositFragment())
                .disallowAddToBackStack()
                .commit();

    }

    public ProjectByID getProjectData() {
        return projectData;
    }

    public ExpertGigDetail getGigData() {
        return gigData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_pay:
                isFrom1TimeVerifyMail = false;
                if (isVerifyEmail) {
                    getProfile();
                } else {
                    if (activity.getUserData().trustRate != null) {
                        if (activity.getUserData().trustRate.email != null && activity.getUserData().trustRate.email <= 0) {
                            new EmailVerificationDialog(activity);
                        } else {
                            doPayment();
                        }
                    } else {
                        getProfile();
                    }
                }
                break;
        }
    }

    private void doPayment() {
        if (isPaypal == 0) {
            creditCardDepositFragment.onClickCardPay();
        } else if (isPaypal == 1) {
            creditCardDepositFragment.onClickPaypalPay();
        } else if (isPaypal == 2) {
            creditCardDepositFragment.onClickGooglePay();
        } else if (isPaypal == 3) {
            creditCardDepositFragment.onClickVenmoPay();
        } else if (isPaypal == 4) {
            creditCardDepositFragment.onClickBankTransferPay();
        }
    }

    private void getProfile() {
        if (!activity.isNetworkConnected())
            return;

        binding.tvPay.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CLIENT_PROFILE, false, null);
    }

    private void verifyEmail(String email) {
        if (!activity.isNetworkConnected())
            return;

        btnSendEmail.setVisibility(View.INVISIBLE);
        progressBarEmail.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("platform", "4");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEND_EMAIL_VERIFICATION, true, map);
    }

    void onResumeMethod() {
        if (getRemainingBalance() > 0) {
            setRemainingBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity, getRemainingBalance()) : Utils.priceWithout$(getRemainingBalance())));
        } else {
            setUserActualBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity, paymentMethod.clientBalance) : Utils.priceWithout$(paymentMethod.clientBalance)));
            setRemainingBalance(Double.parseDouble(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity, paymentMethod.clientBalance) : Utils.priceWithout$(paymentMethod.clientBalance)));
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constants.POSITION && resultCode == RESULT_OK) {
                DepositFundsActivity.paymentAccount = data.getStringExtra("payment_account");
                DepositFundsActivity.paymentAccountId = data.getStringExtra("payment_account_id");
                DepositFundsActivity.cardNumber = data.getStringExtra("cardNo");
                DepositFundsActivity.cardExp = data.getStringExtra("cardExp");

                DepositFundsActivity.paypalAccountEmail = data.getStringExtra("paypal_payment_account");
                DepositFundsActivity.paypalAccountToken = data.getStringExtra("paypal_payment_account_id");

                if (creditCardDepositFragment != null) {
                    if (isPaypal == 0) {
                        creditCardDepositFragment.updateAccountUI();
                    } else if (isPaypal == 1) {
                        creditCardDepositFragment.updatePaypalAccountList();
                    } else if (isPaypal == 4) {
                        creditCardDepositFragment.hideBankCardUI();
                        creditCardDepositFragment.onActivityResult(requestCode, resultCode, data);
                    } else {
                        creditCardDepositFragment.hideCardUI();
                    }
                }

            } else if (requestCode == 991) {
                creditCardDepositFragment.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception ex) {
            activity.toastMessage(ex.toString());
        }
    }

    void checkPromoCodeApi(String promoCode, int fragment) {
        if (!activity.isNetworkConnected())
            return;


        HashMap<String, String> map = new HashMap<>();
        map.put("discount_code", promoCode);
        if (!isFromGig) {
            map.put("job_post_id", projectData.id + "");
        } else {
            map.put("job_post_id", gigData.gigID + "");
            map.put("totalPrice", gigData.fixedPrice + "");
        }
        selectedFragment = fragment;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_CHECK_PROMO_CODE, true, map);


    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_CHECK_PROMO_CODE)) {
            PromoCode model = PromoCode.getPromoCode(responseBody);
            if (model != null) {
                double totalAmount = getTotalPrice();
                if (model.isValid) {
                    ((DepositFundsActivity) activity).appliedPromoCode = model.promoCode;
                    ((DepositFundsActivity) activity).setPromo_Amount(model.discountAmount);
                } else {
                    ((DepositFundsActivity) activity).appliedPromoCode = null;
                    ((DepositFundsActivity) activity).setPromo_Amount(0);
                    activity.failureError(model.getMessage(activity));
                    Preferences.writeString(activity, REFERRAL_ID_FROM_LINK, "");
                }
                switch (selectedFragment) {
                    case 0:
                        creditCardDepositFragment.setPromoCode(((DepositFundsActivity) activity).appliedPromoCode, ((DepositFundsActivity) activity).getPromo_Amount(), totalAmount);
                        break;
                }
            }
        } else if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                ((DepositFundsActivity) activity).braintreeToken = braintreeToken.token;
                if (creditCardDepositFragment != null) {
                    creditCardDepositFragment.googlePayVisibility();
                }
            }
        } else if (url.equalsIgnoreCase(API_SEND_EMAIL_VERIFICATION)) {
            btnSendEmail.setVisibility(View.INVISIBLE);
            progressBarEmail.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
            isVerifyEmail = true;
            isFrom1TimeVerifyMail = true;
            getProfile();
            dialogEmail.dismiss();
        } else if (url.equalsIgnoreCase(API_GET_CLIENT_PROFILE)) {
            Profile profile = Profile.getProfileInfo(responseBody);
            binding.tvPay.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            if (!isFrom1TimeVerifyMail) {
                if (profile != null) {
                    Preferences.setProfileData(activity, profile);
                    if (profile.trustRate.email <= 0) {
                        new EmailVerificationDialog(activity);
                    } else {
                        doPayment();
                    }
                }
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_CHECK_PROMO_CODE)) {
            ((DepositFundsActivity) activity).appliedPromoCode = null;
            ((DepositFundsActivity) activity).setPromo_Amount(0);
            double totalAmount = getTotalPrice();
            switch (selectedFragment) {
                case 0:
                    creditCardDepositFragment.setPromoCode(((DepositFundsActivity) activity).appliedPromoCode, ((DepositFundsActivity) activity).getPromo_Amount(), totalAmount);
                    break;
            }
        }
    }

    void enterPromoCodeDialog(int fragment, String appliedCode) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_promo_code);
        dialog.setCancelable(true);

        LinearLayout llDeposit = dialog.findViewById(R.id.ll_promo);
        CustomEditText etPromoCode = dialog.findViewById(R.id.et_promoCode);
        ImageView imgClose = dialog.findViewById(R.id.img_close);
        CustomTextView tvApply = dialog.findViewById(R.id.tv_apply);

        if (!TextUtils.isEmpty(appliedCode)) {
            etPromoCode.setText(appliedCode);
        }

        llDeposit.setOnClickListener(v -> dialog.dismiss());
        imgClose.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(Objects.requireNonNull(etPromoCode.getText()).toString().trim())) {
                checkPromoCodeApi(etPromoCode.getText().toString().trim(), fragment);
            }
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(dialog1 -> dialog.dismiss());
    }

    private void generateBraintreeToken() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);

    }

    private void notesDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_deposit_notes);
        dialog.setCancelable(true);

        TextView tvOk = dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();

        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public class EmailVerificationDialog {
        private final BaseActivity activity;
        private final CustomEditText edtEmail;

        public EmailVerificationDialog(BaseActivity activity) {
            this.activity = activity;

            dialogEmail = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
            dialogEmail.setTitle(null);
            dialogEmail.setContentView(R.layout.dialog_email_verification);
            dialogEmail.setCancelable(true);

            edtEmail = dialogEmail.findViewById(R.id.edtEmail);
            btnSendEmail = dialogEmail.findViewById(R.id.btnSendEmail);
            progressBarEmail = dialogEmail.findViewById(R.id.progressBarEmail);

            edtEmail.setText(activity.getUserData().email);

            btnSendEmail.setOnClickListener(v -> {
                if (validData()) {
                    verifyEmail(edtEmail.getText().toString());
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialogEmail.getWindow()).getAttributes());
            lp.gravity = Gravity.CENTER;
            dialogEmail.show();
            dialogEmail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogEmail.getWindow().setAttributes(lp);
        }


        private boolean validData() {
            if (activity.isEmpty(edtEmail.getText().toString())) {
                activity.validationError(activity.getString(R.string.enter_emailid));
                return false;
            }

            if (!activity.isValidEmail(edtEmail.getText().toString())) {
                activity.validationError(activity.getString(R.string.enter_valid_emailid));
                return false;
            }
            return true;
        }

    }
}
