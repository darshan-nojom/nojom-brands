package com.nojom.client.ui.workprofile;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityPhoneVerifyBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_PROFILE_VERIFICATIONS;
import static com.nojom.client.util.Constants.COUNTRY_CODE_VALUE;

class VerifyPhoneNumberActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityPhoneVerifyBinding binding;
    private BaseActivity activity;
    static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private FirebaseAuth mAuth;

    boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    VerifyPhoneNumberActivityVM(Application application, ActivityPhoneVerifyBinding phoneVerifyBinding, BaseActivity verifyPhoneNumberActivity) {
        super(application);
        binding = phoneVerifyBinding;
        activity = verifyPhoneNumberActivity;
        initData();
    }

    private void initData() {

        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.tvResendCode.setOnClickListener(this);

        binding.ccp.registerCarrierNumberEditText(binding.etMobile);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode(Locale.getDefault().getLanguage());
        binding.etMobile.setKeyListener(null);
        Profile profileData = activity.getUserData();
        binding.ccp.setEnabled(false);
        binding.ccp.setCcpClickable(false);
        if (profileData != null && profileData.contactNo != null) {
            try {
                String[] split = profileData.contactNo.split("\\.");
                if (split.length == 2) {
                    binding.etMobile.setText(split[1]);
                    binding.tvPhonePrefix.setText(split[0]);
                    try {
                        String nameCode = Preferences.readString(activity, COUNTRY_CODE_VALUE, "");
                        if (!TextUtils.isEmpty(nameCode)) {
                            binding.ccp.setDetectCountryWithAreaCode(false);
                            binding.ccp.setCountryForNameCode(nameCode);
                        } else {
                            String code = split[0].replace("+", "").replace(" ", "");
                            binding.ccp.setCountryForPhoneCode(Integer.parseInt(code));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, credential);
                verifyPhoneNumber();
            }

            @Override
            public void onVerificationFailed(@NotNull FirebaseException e) {
                binding.progressBarSignup.setVisibility(View.GONE);
                binding.tvSubmit.setVisibility(View.VISIBLE);
                mVerificationInProgress = false;
                activity.isClickableView = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    binding.etMobile.setError(activity.getString(R.string.invalid_phone_number));
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    activity.toastMessage(activity.getString(R.string.quota_exceeded));
                }

                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(@NotNull String verificationId,
                                   @NotNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBarSignup.setVisibility(View.GONE);
                binding.tvSubmit.setVisibility(View.VISIBLE);
                mVerificationId = verificationId;
                mResendToken = token;
                mVerificationInProgress = false;
                activity.isClickableView = false;
                updateUI(STATE_CODE_SENT);
            }
        };

        binding.ccp.setOnCountryChangeListener(() -> binding.tvPhonePrefix.setText(binding.ccp.getSelectedCountryCodeWithPlus()));

    }

    private void startPhoneNumberVerification() {
        binding.progressBarSignup.setVisibility(View.VISIBLE);
        binding.tvSubmit.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getMobilePrefix() + getMobile(),
                60,
                TimeUnit.SECONDS,
                (Activity) activity,
                mCallbacks);

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String code) {
        try {
            binding.progressBarSignup.setVisibility(View.VISIBLE);
            binding.tvSubmit.setVisibility(View.INVISIBLE);
            activity.isClickableView = true;

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            verifyPhoneNumber();
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getMobilePrefix() + getMobile(),
                60,
                TimeUnit.SECONDS,
                activity,
                mCallbacks,
                token);
    }

    private String getMobile() {
        String mobile = binding.etMobile.getText().toString().trim();
        return mobile.replace(" ", "");
    }

    private String getMobilePrefix() {
        return binding.ccp.getSelectedCountryCodeWithPlus();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_submit:
                if (binding.tvSubmit.getText().toString().equals(activity.getString(R.string.send_verification_code))) {
                    if (!isValid()) {
                        return;
                    }

                    startPhoneNumberVerification();
                } else {
                    String code = binding.etOtp.getText().toString();
                    if (activity.isEmpty(code)) {
                        activity.toastMessage(activity.getString(R.string.please_enter_otp));
                        return;
                    }

                    verifyPhoneNumberWithCode(code);
                }
                break;
            case R.id.tv_resend_code:
                resendVerificationCode(mResendToken);
                break;
        }
    }

    private void verifyPhoneNumber() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarSignup.setVisibility(View.VISIBLE);
        binding.tvSubmit.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "2");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_PROFILE_VERIFICATIONS, true, map);
    }

    private boolean isValid() {
        if (!binding.ccp.isValidFullNumber()) {
            activity.validationError(activity.getString(R.string.valid_mobile_no));
            return false;
        }
        return true;
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                binding.llOtp.setVisibility(View.GONE);
                break;
            case STATE_CODE_SENT:
                binding.tvSubmit.setText(activity.getString(R.string.verify_otp));
                binding.llOtp.setVisibility(View.VISIBLE);
                disableViews(binding.etMobile, binding.ccp);
                break;
            case STATE_VERIFY_FAILED:
                binding.tvSubmit.setText(activity.getString(R.string.send_verification_code));
                binding.llOtp.setVisibility(View.GONE);
                activity.toastMessage(activity.getString(R.string.verification_failed));
                break;
        }
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    void onStartMethod() {
        if (mVerificationInProgress && isValid()) {
            startPhoneNumberVerification();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        binding.progressBarSignup.setVisibility(View.GONE);
        binding.tvSubmit.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        activity.setResult(RESULT_OK);
        activity.finish();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.progressBarSignup.setVisibility(View.GONE);
        binding.tvSubmit.setVisibility(View.VISIBLE);
        activity.isClickableView = false;

    }
}
