package com.nojom.client.ui.auth;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityUpdatePasswordBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.HashMap;
import java.util.Objects;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;

import static com.nojom.client.util.Constants.API_FORGET_PASS;
import static com.nojom.client.util.Constants.API_RESET_PASS;
import static com.nojom.client.util.Constants.API_UPDATE_PASS;

public class UpdatePasswordActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityUpdatePasswordBinding binding;
    private BaseActivity activity;
    private boolean isResend;
    private String forgetPasswordEmail;
    private Dialog dialog;

    UpdatePasswordActivityVM(Application application, ActivityUpdatePasswordBinding updatePasswordBinding, BaseActivity updatePasswordActivity) {
        super(application);
        binding = updatePasswordBinding;
        activity = updatePasswordActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.rlSupportChat.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);

        binding.toolbar.tvTitle.setText(activity.getString(R.string.update_password));
    }

    private String getOldPassword() {
        return binding.etOldPassword.getText().toString().trim();
    }

    private String getNewPassword() {
        return binding.etNewPassword.getText().toString().trim();
    }

    private String getConfirmPassword() {
        return binding.etConfirmPassword.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_save:
                if (isValid()) {
                    updatePassword();
                }
                break;
            case R.id.rl_support_chat:
//                activity.openWhatsappChat();
                Intercom.client().displayMessageComposer();
                break;
            case R.id.txt_forgot_password:
                showForgotPasswordDialog();
                break;
        }
    }

    private void updatePassword() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarSignup.setVisibility(View.VISIBLE);
        binding.tvSave.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("old_password", getOldPassword());
        map.put("password", getNewPassword());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_PASS, true, map);
    }

    private boolean isValid() {
        if (activity.isEmpty(getOldPassword())) {
            activity.validationError(activity.getString(R.string.enter_your_old_password));
            return false;
        }

        if (activity.isEmpty(getNewPassword())) {
            activity.validationError(activity.getString(R.string.enter_your_new_password));
            return false;
        }

        if (getNewPassword().length() < 8) {
            activity.validationError(activity.getString(R.string.password_length_should_be_min_8));
            return false;
        }

        if (activity.isEmpty(getConfirmPassword())) {
            activity.validationError(activity.getString(R.string.enter_confirm_password));
            return false;
        }

        if (getConfirmPassword().length() < 8) {
            activity.validationError(activity.getString(R.string.password_length_should_be_min_8));
            return false;
        }

        if (!getNewPassword().equals(getConfirmPassword())) {
            activity.validationError(activity.getString(R.string.doesnt_match_password));
            return false;
        }

        if (getNewPassword().equals(getOldPassword())) {
            activity.validationError(activity.getString(R.string.same_password));
            return false;
        }
        return true;
    }

    private void showForgotPasswordDialog() {
        dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setCancelable(true);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnReset = dialog.findViewById(R.id.btn_reset);
        final EditText etEmail = dialog.findViewById(R.id.et_email);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnReset.setOnClickListener(v -> {
            if (activity.isValidEmail(etEmail.getText().toString())) {
                Utils.hideSoftKeyboard(activity);
                forgotPassword(etEmail.getText().toString(), false);
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.enter_valid_email));
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

    private void forgotPassword(final String email, final boolean isResend) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        this.isResend = isResend;
        forgetPasswordEmail = email;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_FORGET_PASS, true, map);
    }

    private void showSecurityCodeDialog(final String email) {
        Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_security_code);
        dialog.setCancelable(true);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnReset = dialog.findViewById(R.id.btn_reset);
        final EditText etSecurityCode = dialog.findViewById(R.id.et_security_code);
        final EditText etNewPassword = dialog.findViewById(R.id.et_new_password);
        TextView tvResendCode = dialog.findViewById(R.id.tv_resend_code);
        tvResendCode.setPaintFlags(tvResendCode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvResendCode.setOnClickListener(v -> forgotPassword(email, true));

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnReset.setOnClickListener(v -> {
            if (activity.isEmpty(etSecurityCode.getText().toString())) {
                activity.validationError(activity.getString(R.string.enter_code));
                return;
            }

            if (activity.isEmpty(etNewPassword.getText().toString())) {
                activity.validationError(activity.getString(R.string.enter_password));
                return;
            }

            Utils.hideSoftKeyboard(activity);
            resetPassword(email, etSecurityCode.getText().toString(), etNewPassword.getText().toString(), dialog);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void resetPassword(String email, String otp, String password, final Dialog dialog) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("otp", otp);
        map.put("password", password);

        this.dialog = dialog;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_RESET_PASS, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_UPDATE_PASS)) {
            binding.progressBarSignup.setVisibility(View.GONE);
            binding.tvSave.setVisibility(View.VISIBLE);

            activity.toastMessage(message);
            activity.onBackPressed();
        }else if (url.equalsIgnoreCase(API_RESET_PASS)) {
            activity.toastMessage(message);
            if (dialog != null) {
                dialog.dismiss();
                FirebaseAuth.getInstance().signOut();  // Firebase Signout
                Branch.getInstance().logout();

                if (activity.mSocket != null && activity.mSocket.connected()) {
                    activity.mSocket.disconnect();
                }

                Intercom.client().logout();
                Preferences.setProfileData(activity, null);
                Preferences.writeBoolean(activity, Constants.IS_LOGIN, false);
                Preferences.writeString(activity, Constants.JWT, null);
                //Preferences.locationUpdate(activity);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent i = new Intent(activity, LoginSignUpActivity.class);
                    i.putExtra(Constants.FROM_LOGIN, true);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(i);
                    activity.finishAffinity();
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                }, 2000);

            }
        } else if (url.equalsIgnoreCase(API_FORGET_PASS)) {
            activity.toastMessage(message);
            if (!isResend)
                showSecurityCodeDialog(forgetPasswordEmail);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_UPDATE_PASS)) {
            binding.progressBarSignup.setVisibility(View.GONE);
            binding.tvSave.setVisibility(View.VISIBLE);
        }
    }
}
