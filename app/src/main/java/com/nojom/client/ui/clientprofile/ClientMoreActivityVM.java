package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_GET_PARTNER_QUESTION;
import static com.nojom.client.util.Constants.PREF_PARTNER_ABOUT;
import static com.nojom.client.util.Constants.PREF_PARTNER_APP;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientMoreBinding;
import com.nojom.client.model.PartnerWithUsResponse;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.balance.WalletActivity;
import com.nojom.client.ui.home.FindExpertActivity;
import com.nojom.client.ui.settings.GetDiscountActivity;
import com.nojom.client.ui.settings.MyInvoiceActivity;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;

class ClientMoreActivityVM extends AndroidViewModel implements BaseActivity.OnProfileLoadListener, View.OnClickListener, RequestResponseListener {
    private ActivityClientMoreBinding binding;
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PartnerWithUsResponse.Data>> listMutableLiveData;

    ClientMoreActivityVM(Application application, ActivityClientMoreBinding clientMoreBinding, BaseActivity clientMoreActivity) {
        super(application);
        binding = clientMoreBinding;
        activity = clientMoreActivity;
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
        binding.rlDiscount.setOnClickListener(this);
        binding.rlFeedback.setOnClickListener(this);
        binding.rlWhatWeDo.setOnClickListener(this);
        binding.rlHowitworks.setOnClickListener(this);
        binding.rlSetting.setOnClickListener(this);
        binding.rlBalance.setOnClickListener(this);
        binding.rlGetDiscount.setOnClickListener(this);
        binding.rlExpert.setOnClickListener(this);
        binding.rlPlaystoreAgent.setOnClickListener(this);
        binding.imgCloseHire.setOnClickListener(this);
        binding.rlPartnerWithUs.setOnClickListener(this);
        binding.rlInvoice.setOnClickListener(this);
        if (activity.isLogin()) {
            activity.setOnProfileLoadListener(this);
            activity.getProfile();
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
            binding.login.linLogin.setVisibility(View.GONE);
        }

        Profile profileData = Preferences.getProfileData(activity);
        if (profileData != null) {
            onProfileLoad(profileData);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_profile:
            case R.id.img_profile:
            case R.id.rl_profile:
                activity.setEnableDisableView(binding.rlProfile);
                activity.redirectActivity(ProfileInfoActivity.class);
                break;
            case R.id.rl_discount:
                activity.setEnableDisableView(binding.rlDiscount);
                activity.redirectActivity(ShareDiscountActivity.class);
                break;
            case R.id.rl_expert:
                activity.setEnableDisableView(binding.rlExpert);
                activity.redirectActivity(FindExpertActivity.class);
                break;
            case R.id.rl_feedback:
                activity.setEnableDisableView(binding.rlFeedback);
                activity.showFeedbackDialog(0, activity.getString(R.string.feedback));
                break;
            case R.id.rl_what_we_do:
                activity.setEnableDisableView(binding.rlWhatWeDo);
                activity.redirectActivity(WhatWeDoActivity.class);
                break;
            case R.id.rl_howitworks:
                activity.setEnableDisableView(binding.rlHowitworks);
                activity.redirectActivity(HowItWorksActivity.class);
                break;
            case R.id.rl_setting:
                activity.setEnableDisableView(binding.rlSetting);
                activity.redirectActivity(ClientSettingActivity.class);
                break;
            case R.id.rl_balance:
                activity.setEnableDisableView(binding.rlBalance);
//                activity.redirectActivity(BalanceActivity.class);
                activity.redirectActivity(WalletActivity.class);
                break;
            case R.id.rl_getDiscount:
                activity.setEnableDisableView(binding.rlGetDiscount);
                activity.redirectActivity(GetDiscountActivity.class);
                break;
            case R.id.rl_invoice:
                activity.setEnableDisableView(binding.rlInvoice);
                activity.redirectActivity(MyInvoiceActivity.class);
                break;
            case R.id.rl_partner_with_us:
                getPartnerQuestions();
                break;
        }
    }

    public MutableLiveData<Boolean> getIsShow() {
        return isShow;
    }


    void getPartnerQuestions() {
        if (!activity.isNetworkConnected())
            return;

        getIsShow().postValue(true);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PARTNER_QUESTION, false, null);
    }

    @Override
    public void onProfileLoad(Profile data) {
        binding.tvUsername.setText(activity.getString(R.string.hi_) + " " + data.username);
        if (data.email != null)
            binding.tvEmail.setText(data.email);
//        activity.setImage(binding.imgProfile, TextUtils.isEmpty(data.profilePic) ? "" : data.filePath.pathProfilePicClient + data.profilePic, 0, 0);

        Glide
                .with(activity)
                .load(TextUtils.isEmpty(data.profilePic) ? "" : data.filePath.pathProfilePicClient + data.profilePic)
                .centerCrop()
                .placeholder(R.drawable.dp)
                .into(binding.imgProfile);
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
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getIsShow().postValue(false);
    }
}
