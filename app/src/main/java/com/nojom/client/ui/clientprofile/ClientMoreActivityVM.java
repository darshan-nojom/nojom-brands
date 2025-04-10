package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_GET_PARTNER_QUESTION;
import static com.nojom.client.util.Constants.API_LOGOUT;
import static com.nojom.client.util.Constants.API_UPDATE_PROFILE;
import static com.nojom.client.util.Constants.PREF_PARTNER_ABOUT;
import static com.nojom.client.util.Constants.PREF_PARTNER_APP;
import static com.nojom.client.util.Constants.TAB_HOME;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.SingleSelectionItemAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientSettingsBinding;
import com.nojom.client.model.Language;
import com.nojom.client.model.PartnerWithUsResponse;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.balance.WalletActivity;
import com.nojom.client.ui.projects.CampByIdVM;
import com.nojom.client.ui.settings.MyInvoiceActivity;
import com.nojom.client.ui.settings.NotificationActivity;
import com.nojom.client.ui.workprofile.VerificationActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;

class ClientMoreActivityVM extends AndroidViewModel implements BaseActivity.OnProfileLoadListener, View.OnClickListener, RequestResponseListener {
    private ActivityClientSettingsBinding binding;
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PartnerWithUsResponse.Data>> listMutableLiveData;
    private CampByIdVM campByIdVM;

    ClientMoreActivityVM(Application application, ActivityClientSettingsBinding clientMoreBinding, BaseActivity clientMoreActivity) {
        super(application);
        binding = clientMoreBinding;
        activity = clientMoreActivity;
        campByIdVM = new CampByIdVM(Task24Application.getInstance(), activity);
        initData();
    }

    public MutableLiveData<ArrayList<PartnerWithUsResponse.Data>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }

    private void initData() {
        binding.llProfile.setOnClickListener(this);
        binding.imgProfile.setOnClickListener(this);
        binding.rlProfile.setOnClickListener(this);
//        binding.rlDiscount.setOnClickListener(this);
//        binding.rlFeedback.setOnClickListener(this);
//        binding.rlWhatWeDo.setOnClickListener(this);
//        binding.rlHowitworks.setOnClickListener(this);
        binding.rlSetting.setOnClickListener(this);
        binding.rlBalance.setOnClickListener(this);
//        binding.rlGetDiscount.setOnClickListener(this);
//        binding.rlExpert.setOnClickListener(this);
//        binding.rlPlaystoreAgent.setOnClickListener(this);
//        binding.imgCloseHire.setOnClickListener(this);
//        binding.rlPartnerWithUs.setOnClickListener(this);
        binding.rlInvoice.setOnClickListener(this);
        binding.rlTrustVerification.setOnClickListener(this);
        binding.rlNotifications.setOnClickListener(this);
        binding.rlLanguage.setOnClickListener(this);
        binding.rlShare.setOnClickListener(this);
        binding.rlRate.setOnClickListener(this);
        binding.rlTerms.setOnClickListener(this);
        binding.rlContact.setOnClickListener(this);
        binding.relHeader.setOnClickListener(this);
        binding.rlFaq.setOnClickListener(this);
        binding.rlPrivacy.setOnClickListener(this);
        binding.linLogout.setOnClickListener(this);
        if (activity.isLogin()) {
            activity.setOnProfileLoadListener(this);
            activity.getProfile();
        }

        campByIdVM.mpWalletBalanceData.observe(activity, walletData -> {
            setBalance(walletData.available_balance);
        });
    }

    private void setBalance(Double availableBalance) {
        if (activity.getCurrency().equals("SAR")) {
            binding.txtSelWallet.setText(Utils.priceWithSAR(activity, Utils.getDecimalValue("" + availableBalance)).replace(activity.getString(R.string.sar), ""));
        } else {
            binding.txtSelWallet.setText(Utils.priceWith$(Utils.getDecimalValue("" + availableBalance), activity).replace(activity.getString(R.string.dollar), ""));
        }
    }

    void onResumeMethod() {
        if (!activity.isLogin()) {
            binding.linProfile.setVisibility(View.GONE);
            binding.login.txtTitle.setText(activity.getString(R.string.not_logged_on_yet));
            binding.login.txtMsg.setText(activity.getString(R.string.please_login_or_register));
            binding.login.linLogin.setVisibility(View.VISIBLE);
            binding.login.tvSure.setOnClickListener(v -> activity.openLoginDialog());
            binding.login.tvNotNow.setOnClickListener(v -> ((MainActivity) activity.getParent()).setHomeTab());
            return;
        } else {
            campByIdVM.getWalletBalance();
            binding.login.linLogin.setVisibility(View.GONE);
        }

        Profile profileData = Preferences.getProfileData(activity);
        if (profileData != null) {
            onProfileLoad(profileData);
        }

        if (activity.getLanguage().equals("ar")) {
            binding.txtSelLang.setText(activity.getString(R.string.arabic));
        } else {
            binding.txtSelLang.setText(activity.getString(R.string.english));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_profile:
            case R.id.img_profile:
            case R.id.rl_profile:
            case R.id.rel_header:
                activity.setEnableDisableView(binding.rlProfile);
                activity.redirectActivity(ProfileInfoActivity.class);
                break;
//            case R.id.rl_discount:
//                activity.setEnableDisableView(binding.rlDiscount);
//                activity.redirectActivity(ShareDiscountActivity.class);
//                break;
//            case R.id.rl_expert:
//                activity.setEnableDisableView(binding.rlExpert);
//                activity.redirectActivity(FindExpertActivity.class);
//                break;
//            case R.id.rl_feedback:
//                activity.setEnableDisableView(binding.rlFeedback);
//                activity.showFeedbackDialog(0, activity.getString(R.string.feedback));
//                break;
//            case R.id.rl_what_we_do:
//                activity.setEnableDisableView(binding.rlWhatWeDo);
//                activity.redirectActivity(WhatWeDoActivity.class);
//                break;
//            case R.id.rl_howitworks:
//                activity.setEnableDisableView(binding.rlHowitworks);
//                activity.redirectActivity(HowItWorksActivity.class);
//                break;
            case R.id.rl_setting:
                activity.setEnableDisableView(binding.rlSetting);
                activity.redirectActivity(ClientSettingActivity.class);
                break;
            case R.id.rl_balance:
                activity.setEnableDisableView(binding.rlBalance);
//                activity.redirectActivity(BalanceActivity.class);
                activity.redirectActivity(WalletActivity.class);
                break;
//            case R.id.rl_getDiscount:
//                activity.setEnableDisableView(binding.rlGetDiscount);
//                activity.redirectActivity(GetDiscountActivity.class);
//                break;
            case R.id.rl_invoice:
                activity.setEnableDisableView(binding.rlInvoice);
                activity.redirectActivity(MyInvoiceActivity.class);
                break;
            case R.id.rl_partner_with_us:
                getPartnerQuestions();
                break;
            case R.id.rl_notifications:
                activity.redirectActivity(NotificationActivity.class);
                break;
            case R.id.rl_language:
                showLanguageSettingDialog();
                break;
            case R.id.rl_trustVerification:
                activity.redirectActivity(VerificationActivity.class);
                break;
            case R.id.rl_share:
                activity.shareApp();
                break;
            case R.id.rl_rate:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    activity.toastMessage("Couldn't launch the market");
                }
                break;
            case R.id.rl_terms:
                activity.redirectUsingCustomTab(Constants.TERMS_USE);
                break;
            case R.id.rl_privacy:
                activity.redirectUsingCustomTab(Constants.PRIVACY);
                break;
            case R.id.rl_faq:
                activity.redirectUsingCustomTab(Constants.FAQS);
                break;
            case R.id.rl_contact:
                activity.showContactUsDialog();
                break;
            case R.id.lin_logout:
                showLogoutDialog();
                break;
        }
    }

    public MutableLiveData<Boolean> getIsShow() {
        return isShow;
    }


    void getPartnerQuestions() {
        if (!activity.isNetworkConnected()) return;

        getIsShow().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PARTNER_QUESTION, false, null);
    }

    @Override
    public void onProfileLoad(Profile data) {
        binding.tvUsername.setText(data.firstName + " " + data.lastName);
        if (data.contactNo != null)
            binding.tvEmail.setText(data.contactNo.replace(".", " "));
//        activity.setImage(binding.imgProfile, TextUtils.isEmpty(data.profilePic) ? "" : data.filePath.pathProfilePicClient + data.profilePic, 0, 0);

        Glide.with(activity).load(TextUtils.isEmpty(data.profilePic) ? "" : data.filePath.pathProfilePicClient + data.profilePic).centerCrop().placeholder(R.drawable.dp).into(binding.imgProfile);

        int trustScore = 0;
        if (data.trustRate.email != 0) {
            trustScore = trustScore + 20;
        }
        if (data.trustRate.phoneNumber != 0) {
            trustScore = trustScore + 20;
        }
        if (data.trustRate.cr_id != 0) {
            trustScore = trustScore + 60;
        }
        if (trustScore > 60) {
            binding.txtSelVerif.setText(activity.getString(R.string.verified));
        } else {
            binding.txtSelVerif.setText(activity.getString(R.string.not_verified));
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PARTNER_QUESTION)) {
            PartnerWithUsResponse languageList = PartnerWithUsResponse.getPartnerWithUsQuestionList(responseBody);
            if (languageList != null && languageList.data != null && languageList.data.size() > 0) {

                for (int i = 0; i < languageList.data.size(); i++) {
                    if (languageList.data.get(i).page == 1 && !TextUtils.isEmpty(languageList.data.get(i).getAnswers(activity.getLanguage()))) {
                        Preferences.writeBoolean(activity, PREF_PARTNER_APP, true);
                    }
                    if (languageList.data.get(i).page == 2 && !TextUtils.isEmpty(languageList.data.get(i).getAnswers(activity.getLanguage()))) {
                        Preferences.writeBoolean(activity, PREF_PARTNER_ABOUT, true);
                    }
                }

                getListMutableLiveData().postValue(languageList.data);
            }
            getIsShow().postValue(false);
        } else if (url.equalsIgnoreCase(API_LOGOUT)) {
            binding.progressBarLogout.setVisibility(View.GONE);
            binding.btnSignout.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signOut();  // Firebase Signout
            Branch.getInstance().logout();

            if (activity.mSocket != null && activity.mSocket.connected()) {
                activity.mSocket.disconnect();
            }

            activity.toastMessage(activity.getString(R.string.sign_out_successfully));
            Intercom.client().logout();
            Preferences.setProfileData(activity, null);
            Preferences.writeBoolean(activity, Constants.IS_LOGIN, false);
            Preferences.writeString(activity, Constants.JWT, null);
            //Preferences.locationUpdate(activity);

            Intent i = new Intent(activity, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(i);
            activity.openToLeft();
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getIsShow().postValue(false);
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_LOGOUT)) {
            binding.progressBarLogout.setVisibility(View.GONE);
            binding.btnSignout.setVisibility(View.VISIBLE);
        }
    }

    private void showLanguageSettingDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_language);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        String language = Preferences.readString(activity, Constants.SELECTED_LANGUAGE, "en");

        ArrayList<Language.Data> languageList = new ArrayList<>();
        languageList.add(new Language.Data(activity.getString(R.string.english), "en"));
        languageList.add(new Language.Data(activity.getString(R.string.arabic), "ar"));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        SingleSelectionItemAdapter itemAdapter = new SingleSelectionItemAdapter(activity, languageList, language);
        rvTypes.setAdapter(itemAdapter);

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            Utils.hideSoftKeyboard(activity);
            if (itemAdapter.getSelectedItem() != null) {
                activity.loadAppLanguage();
                Preferences.writeString(activity, Constants.SELECTED_LANGUAGE, itemAdapter.getSelectedItem());
                updateLanguage(itemAdapter.getSelectedItem());
                dialog.dismiss();
                activity.gotoMainActivity(TAB_HOME);
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_one_item));
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void updateLanguage(String lang) {
        HashMap<String, String> map = new HashMap<>();
        map.put("language", lang);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_PROFILE, true, map);
    }

    private void showLogoutDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        String s = activity.getString(R.string.logout_msg);
        String[] words = {activity.getString(R.string.sign_out_)};
        String[] fonts = {Constants.SFTEXT_BOLD};
        tvMessage.setText(Utils.getBoldString(activity, s, fonts, null, words));

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            logout();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void logout() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        binding.progressBarLogout.setVisibility(View.VISIBLE);
        binding.btnSignout.setVisibility(View.GONE);

        HashMap<String, String> map = new HashMap<>();
        map.put("device_token", activity.getToken());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_LOGOUT, true, map);
    }
}
