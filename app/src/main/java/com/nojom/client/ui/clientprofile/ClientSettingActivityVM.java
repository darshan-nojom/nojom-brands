package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_LOGOUT;
import static com.nojom.client.util.Constants.TAB_HOME;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.nojom.client.R;
import com.nojom.client.adapter.SingleSelectionItemAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientSettingBinding;
import com.nojom.client.model.Language;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.settings.NotificationActivity;
import com.nojom.client.ui.workprofile.UpdateLocationActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;

class ClientSettingActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private final BaseActivity activity;
    private final ActivityClientSettingBinding binding;

    ClientSettingActivityVM(Application application, ActivityClientSettingBinding settingBinding, BaseActivity clientSettingActivity) {
        super(application);
        binding = settingBinding;
        activity = clientSettingActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.rlPrivacyPolicy.setOnClickListener(this);
        binding.rlTermsOfUse.setOnClickListener(this);
        binding.rlContactUs.setOnClickListener(this);
        binding.rlAboutUs.setOnClickListener(this);
        binding.rlFaqs.setOnClickListener(this);
        binding.rlShareApp.setOnClickListener(this);
        binding.btnSignout.setOnClickListener(this);
        binding.rlLocation.setOnClickListener(this);
        binding.rlNotifications.setOnClickListener(this);
        binding.rlLanguage.setOnClickListener(this);
        binding.rlDataPrivacy.setOnClickListener(this);
        binding.rlCurrency.setOnClickListener(this);

        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.txtVersionName.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        binding.toolbar.tvTitle.setText(activity.getString(R.string.setting));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.rl_notifications:
                activity.setEnableDisableView(binding.rlNotifications);
                activity.redirectActivity(NotificationActivity.class);
                break;
            case R.id.rl_location:
                activity.setEnableDisableView(binding.rlLocation);
                activity.redirectActivity(UpdateLocationActivity.class);
                break;
            case R.id.rl_privacy_policy:
                activity.setEnableDisableView(binding.rlPrivacyPolicy);
                activity.redirectUsingCustomTab(Constants.PRIVACY);
                break;
            case R.id.rl_terms_of_use:
                activity.redirectUsingCustomTab(Constants.TERMS_USE);
                break;
            case R.id.rl_contact_us:
                activity.showContactUsDialog();
                break;
            case R.id.rl_about_us:
                activity.redirectUsingCustomTab(Constants.ABOUTUS);
                break;
            case R.id.rl_faqs:
                activity.redirectUsingCustomTab(Constants.FAQS);
                break;
            case R.id.rl_share_app:
                activity.shareApp();
                break;
            case R.id.btn_signout:
                showLogoutDialog();
                break;
            case R.id.rl_language:
                showLanguageSettingDialog();
                break;
            case R.id.rl_currency:
                selectCurrencyDialog();
                break;
            case R.id.rl_data_privacy:
                activity.setEnableDisableView(binding.rlDataPrivacy);
                activity.redirectActivity(DeleteAccountActivity.class);
                break;
        }
    }

    public void selectCurrencyDialog() {
        String selCurrency = Preferences.readString(activity, Constants.PREF_SELECTED_CURRENCY, "SAR");
        final boolean[] isUSDCurrency = new boolean[1];
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_currency);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView txtSar = dialog.findViewById(R.id.txt_sar);
        TextView txtUsd = dialog.findViewById(R.id.txt_usd);
        RelativeLayout txtApply = dialog.findViewById(R.id.rel_apply);

        if (selCurrency.equals("usd")) {
            isUSDCurrency[0] = true;
            txtUsd.setBackgroundResource(R.drawable.blue_button_bg);
            txtUsd.setTextColor(Color.WHITE);

            txtSar.setBackground(null);
            txtSar.setTextColor(Color.BLACK);
        } else {
            txtSar.setBackgroundResource(R.drawable.blue_button_bg);
            txtSar.setTextColor(Color.WHITE);

            txtUsd.setBackground(null);
            txtUsd.setTextColor(Color.BLACK);
        }

        txtSar.setOnClickListener(v -> {
            isUSDCurrency[0] = false;
            txtSar.setBackgroundResource(R.drawable.blue_button_bg);
            txtSar.setTextColor(Color.WHITE);

            txtUsd.setBackground(null);
            txtUsd.setTextColor(Color.BLACK);

        });

        txtUsd.setOnClickListener(v -> {
            isUSDCurrency[0] = true;
            txtUsd.setBackgroundResource(R.drawable.blue_button_bg);
            txtUsd.setTextColor(Color.WHITE);

            txtSar.setBackground(null);
            txtSar.setTextColor(Color.BLACK);

        });
        txtApply.setOnClickListener(v -> {
            Preferences.writeString(activity, Constants.PREF_SELECTED_CURRENCY, isUSDCurrency[0] ? "usd" : "SAR");
            activity.loadAppLanguage();
            dialog.dismiss();
            activity.gotoMainActivity(TAB_HOME);
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
        binding.btnSignout.setText("");

        HashMap<String, String> map = new HashMap<>();
        map.put("device_token", activity.getToken());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_LOGOUT, true, map);
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
                Preferences.writeString(activity, Constants.SELECTED_LANGUAGE, itemAdapter.getSelectedItem());
                activity.loadAppLanguage();
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

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_LOGOUT)) {
            binding.progressBarLogout.setVisibility(View.GONE);
            binding.btnSignout.setText(activity.getString(R.string.sign_out));
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
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_LOGOUT)) {
            binding.progressBarLogout.setVisibility(View.GONE);
            binding.btnSignout.setText(activity.getString(R.string.sign_out));
        }
    }
}
