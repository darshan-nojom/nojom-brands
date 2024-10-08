package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_CHECK_PROMO_CODE;
import static com.nojom.client.util.Constants.API_DO_BANK_TRANSFER;
import static com.nojom.client.util.Constants.API_DO_BANK_TRANSFER_GIG;
import static com.nojom.client.util.Constants.API_DO_BRAINTREE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_CUSTOM_GIGS_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.API_DO_STRIPE_PAYMENT;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_GET_BANKS;
import static com.nojom.client.util.Constants.API_GET_CLIENT_PROFILE;
import static com.nojom.client.util.Constants.API_GET_STRIPE_CARD_LIST;
import static com.nojom.client.util.Constants.API_SEND_EMAIL_VERIFICATION;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;
import static com.nojom.client.util.Constants.REFERRAL_ID_FROM_LINK;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.BuildConfig;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.Banks;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.PromoCode;
import com.nojom.client.model.PurchaseModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PaymentActivityVM extends AndroidViewModel implements RequestResponseListener {
    private final BaseActivity activity;
    private MutableLiveData<PromoCode> promoCodeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PaymentBraintreeCards> paymentCardMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PurchaseModel> purchaseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Banks> bankMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> braintreeTokenMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isShowProgress = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShowProgress() {
        return isShowProgress;
    }

    public MutableLiveData<Banks> getBankMutableLiveData() {
        return bankMutableLiveData;
    }

    public MutableLiveData<String> getBraintreeTokenMutableLiveData() {
        return braintreeTokenMutableLiveData;
    }

    public MutableLiveData<PurchaseModel> getPurchaseMutableLiveData() {
        return purchaseMutableLiveData;
    }

    public MutableLiveData<PromoCode> getPromoCodeMutableLiveData() {
        return promoCodeMutableLiveData;
    }

    public MutableLiveData<PaymentBraintreeCards> getPaymentCardMutableLiveData() {
        return paymentCardMutableLiveData;
    }

    public PaymentActivityVM(Application application, BaseActivity depositFundsActivity) {
        super(application);
        activity = depositFundsActivity;
        initData();
    }

    private void initData() {

        generateBraintreeToken();

    }


    private void getProfile() {
        if (!activity.isNetworkConnected())
            return;

//        binding.tvPay.setVisibility(View.INVISIBLE);
//        binding.progressBar.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CLIENT_PROFILE, false, null);
    }

    private void verifyEmail(String email) {
        if (!activity.isNetworkConnected())
            return;

//        btnSendEmail.setVisibility(View.INVISIBLE);
//        progressBarEmail.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("platform", "4");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEND_EMAIL_VERIFICATION, true, map);
    }

    public void checkPromoCodeApi(boolean isFromGig, String promoCode, ProjectByID projectData, ExpertGigDetail gigData) {
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

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_CHECK_PROMO_CODE, true, map);


    }

    public void getBanks() {
        if (!activity.isNetworkConnectedDialog())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_BANKS, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_CHECK_PROMO_CODE)) {
            PromoCode model = PromoCode.getPromoCode(responseBody);
            if (model != null) {
                getPromoCodeMutableLiveData().postValue(model);
            }
        } else if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                getBraintreeTokenMutableLiveData().postValue(braintreeToken.token);
                // ((DepositFundsActivity) activity).braintreeToken = braintreeToken.token;
//                if (creditCardDepositFragment != null) {
//                    creditCardDepositFragment.googlePayVisibility();
//                }
            }
        } else if (url.equalsIgnoreCase(API_SEND_EMAIL_VERIFICATION)) {
//            btnSendEmail.setVisibility(View.INVISIBLE);
//            progressBarEmail.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
//            isVerifyEmail = true;
//            isFrom1TimeVerifyMail = true;
            getProfile();
//            dialogEmail.dismiss();
        } else if (url.equalsIgnoreCase(API_GET_CLIENT_PROFILE)) {
            Profile profile = Profile.getProfileInfo(responseBody);
//            binding.tvPay.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.GONE);
            /*if (!isFrom1TimeVerifyMail) {
                if (profile != null) {
                    Preferences.setProfileData(activity, profile);
                    if (profile.trustRate.email <= 0) {
                        new EmailVerificationDialog(activity);
                    } else {
                        doPayment();
                    }
                }
            }*/
        } else if (url.equalsIgnoreCase(API_USER_WALLET_LIST) || url.equalsIgnoreCase(API_GET_STRIPE_CARD_LIST)) {
            PaymentBraintreeCards paymentBraintreeCards = PaymentBraintreeCards.getPaymentCards(responseBody);

            getPaymentCardMutableLiveData().postValue(paymentBraintreeCards);
        } else if (url.equalsIgnoreCase(API_DO_BRAINTREE_PAYMENT) || url.equalsIgnoreCase(API_DO_STRIPE_PAYMENT)
                || url.equalsIgnoreCase(API_DO_BANK_TRANSFER) || url.equalsIgnoreCase(API_DO_BANK_TRANSFER_GIG)
                || url.equalsIgnoreCase(API_DO_CUSTOM_GIGS_STRIPE_PAYMENT)
                || url.equalsIgnoreCase(API_DO_CUSTOM_GIGS_PAYMENT)) {
            Preferences.writeString(activity, REFERRAL_ID_FROM_LINK, "");
            PurchaseModel purchaseModel = PurchaseModel.getPurchase(responseBody);
            getPurchaseMutableLiveData().postValue(purchaseModel);
            getIsShowProgress().postValue(false);
        } else if (url.equalsIgnoreCase(API_GET_BANKS)) {
            Banks banks = Banks.getBanks(responseBody);
            if (banks != null && banks.data != null) {
                Preferences.saveBanks(getApplication().getApplicationContext(), banks.data);
                getBankMutableLiveData().postValue(banks);
            }
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_CHECK_PROMO_CODE)) {
            /*((DepositFundsActivity) activity).appliedPromoCode = null;
            ((DepositFundsActivity) activity).setPromo_Amount(0);*/
            getPromoCodeMutableLiveData().postValue(null);
            activity.toastMessage(message);
            //            double totalAmount = getTotalPrice();
//            switch (selectedFragment) {
//                case 0:
//                    creditCardDepositFragment.setPromoCode(((DepositFundsActivity) activity).appliedPromoCode, ((DepositFundsActivity) activity).getPromo_Amount(), totalAmount);
//                    break;
//            }
        } else if (url.equalsIgnoreCase(API_DO_BRAINTREE_PAYMENT) || url.equalsIgnoreCase(API_DO_STRIPE_PAYMENT)
                || url.equalsIgnoreCase(API_DO_BANK_TRANSFER) || url.equalsIgnoreCase(API_DO_BANK_TRANSFER_GIG)) {
            activity.toastMessage(message);
        }
        getIsShowProgress().postValue(false);
        activity.isClickableView = false;
    }

    private void generateBraintreeToken() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);

    }

    public void getStripeCardList() {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_STRIPE_CARD_LIST, false, null);

    }

    public void getCardList() {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_USER_WALLET_LIST, false, null);

    }

    public void doPaymentWithBraintreeBankTransfer(boolean isFromGig, String selectedFilePath, ProjectByID projectData, ExpertGigDetail gigData,
                                                   String fees, String senderName, Integer bankId, String cardNo,
                                                   String txnDate, String refNo, String note, String total, double redeem, String appliedPromoCode, double promoDiscountAmount) {
        if (!activity.isNetworkConnected())
            return;

//        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
//        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);

        getIsShowProgress().postValue(true);
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

        if (!isFromGig) {
            try {
                int jobPostId = projectData.jobPostId;

                double depositFee = activity.getCurrency().equals("SAR") ? Double.parseDouble(Utils.priceWithoutSAR(activity, fees).replaceAll(",", "")) :
                        Double.parseDouble(Utils.priceWithout$(fees).replaceAll(",", ""));
                String description = "Payment - Job ID: " + jobPostId + " Android AppVersion: " + BuildConfig.VERSION_NAME;
                double totalPriceWithDeposit = getTotalPriceWithDeposit(isFromGig, projectData, gigData);
                double promoCodeDiscount = promoDiscountAmount;


                ApiRequest apiRequest = new ApiRequest();
                apiRequest.apiBankTransfer(this, activity, API_DO_BANK_TRANSFER, body,
                        projectData.jobPostId, projectData.jpbId, redeem, totalPriceWithDeposit, depositFee,
                        appliedPromoCode, Double.valueOf(getTotal(total).replaceAll(",", "")), description, 3, 6, senderName, bankId,
                        cardNo, txnDate, refNo, note);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                if (gigData.gigType.equalsIgnoreCase("1") || gigData.gigType.equalsIgnoreCase("3")) {
                    String description = "Payment Deposit - Gig ID: " + gigData.gigID + " Android-InfluencerBird " + BuildConfig.VERSION_NAME;
                    /*HashMap<String, String> map = new HashMap<>();
                    map.put("gigID", gigData.gigID + "");
                    map.put("agentProfileID", gigData.agentProfileID + "");
                    map.put("deadlineID", gigData.deadlineID + "");
                    map.put("paymentTypeID", "11");
                    map.put("paymentPlatformID", "3");
                    map.put("description", description);*/
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

//                        map.put("requirments", jsonArray.toString());
                    }

                    /*if (!TextUtils.isEmpty(appliedPromoCode)) {
                        map.put("discountCode", appliedPromoCode);
                    }
                    map.put("sys_id", "6");
                    map.put("payableAmount", getTotal(total).replaceAll(",", "") + "");
                    map.put("totalPrice", gigData.fixedPrice + "");
                    map.put("redeem", redeem + "");*/

                    /*if (gigData.isFromOffer) {
                        if (gigData.offerID != 0) {
                            map.put("offerID", gigData.offerID + "");
                        }
                        map.put("PK", gigData.pk);
                        map.put("SK", gigData.sk + "");
                    }*/

                    ApiRequest apiRequest = new ApiRequest();
                    apiRequest.apiBankTransferGig(this, activity, API_DO_BANK_TRANSFER_GIG, body,
                            gigData.gigID, gigData.agentProfileID, gigData.deadlineID, 11, 3,
                            description, jsonArray.toString(), appliedPromoCode, Double.parseDouble(getTotal(total).replaceAll(",", "")), gigData.fixedPrice, senderName, bankId,
                            cardNo, txnDate, refNo,
                            note, gigData.offerID, gigData.pk, gigData.sk + "");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    double getTotalPriceWithDeposit(boolean isFromGig, ProjectByID projectData, ExpertGigDetail gigData) {
        if (!isFromGig) {
            if (projectData != null && projectData.fixedPrice != null) {
                double amount = projectData.fixedPrice;
                return amount + getFees(isFromGig, projectData, gigData);
            }
        } else {
            if (gigData != null && gigData.fixedPrice != null) {
                double amount = gigData.fixedPrice;
                return amount + getFees(isFromGig, projectData, gigData);
            }
        }
        return 0;
    }

    double getFees(boolean isFromGig, ProjectByID projectData, ExpertGigDetail gigData) {
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

    private String getTotal(String total) {
        if (activity.getCurrency().equals("SAR")) {
            return Utils.priceWithoutSAR(activity, total);
        } else {
            return Utils.priceWithout$(total);
        }
    }

    public void doStripeWithPayment(String url, HashMap<String, String> map) {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        getIsShowProgress().postValue(true);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, url, true, map);
    }

    public void doPaymentWithBraintree(String url, HashMap<String, String> map) {
        if (!activity.isNetworkConnected())
            return;

//        ((DepositFundsActivity) fragment.activity).getTvPay().setVisibility(View.INVISIBLE);
//        ((DepositFundsActivity) fragment.activity).getProgressbar().setVisibility(View.VISIBLE);
        getIsShowProgress().postValue(true);
        activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, url, true, map);

    }
}
