package com.nojom.client.ui.auth;


import static com.nojom.client.util.Constants.ANDROID;
import static com.nojom.client.util.Constants.API_PROFILE_VERIFICATIONS;
import static com.nojom.client.util.Constants.API_REGISTER;
import static com.nojom.client.util.Constants.SYS_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.MyProfileActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;

public class OtpActivityVM extends ViewModel implements RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;

    private MutableLiveData<Integer> stateVisibility = new MutableLiveData<>();
    private MutableLiveData<Boolean> verificationInProgress = new MutableLiveData<>();
    private MutableLiveData<Boolean> isShowProgress = new MutableLiveData<>();
    private MutableLiveData<Boolean> verifyOtpSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShowProgress() {
        return isShowProgress;
    }

    public MutableLiveData<Boolean> getVerifyOtpSuccess() {
        return verifyOtpSuccess;
    }

    public MutableLiveData<Integer> getStateVisibility() {
        return stateVisibility;
    }

    public MutableLiveData<Boolean> getVerificationInProgress() {
        return verificationInProgress;
    }

    private FirebaseAuth mAuth;

    public void init(BaseActivity activity, FirebaseAuth mAuth) {
        this.activity = activity;
        this.mAuth = mAuth;
    }

    void startPhoneNumberVerification(String code, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks, FirebaseAuth mAuth) {
//        getIsShowProgress().postValue(true);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                code,
//                60,
//                TimeUnit.SECONDS,
//                activity,
//                mCallbacks);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(code)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


        getVerificationInProgress().postValue(true);
    }

    void verifyPhoneNumberWithCode(String verificationId, String code, FirebaseAuth mAuth, String uname, String pass, String email, String mob, String pref) {
        getIsShowProgress().postValue(true);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                //verifyPhoneNumber();
                register(uname, pass, email, mob, pref);
            } else {
                // Sign in failed, display a message and update the UI
                task.getException();// The verification code entered was invalid
                getIsShowProgress().postValue(false);
            }
        });
    }

    void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token, String s, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks) {
//        activity.showProgress();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(s, 60, TimeUnit.SECONDS, activity, mCallbacks, token);
    }


    void verifyPhoneNumber() {
        if (!activity.isNetworkConnected()) return;

//        activity.showProgress();

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "2");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_PROFILE_VERIFICATIONS, true, map);

    }

    boolean isValid(boolean validFullNumber) {
        if (!validFullNumber) {
            activity.validationError(activity.getString(R.string.valid_mobile_no));
            return false;
        }
        return true;
    }

    void updateUI(int uiState) {
        switch (uiState) {
            case STATE_INITIALIZED:
                getStateVisibility().postValue(STATE_INITIALIZED);
                break;
            case STATE_CODE_SENT:
                getStateVisibility().postValue(STATE_CODE_SENT);
                break;
            case STATE_VERIFY_FAILED:
                getStateVisibility().postValue(STATE_VERIFY_FAILED);
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
//        activity.redirectActivity(NameActivity.class);
        if (url.equalsIgnoreCase(API_REGISTER)) {
            Preferences.writeBoolean(activity, Constants.IS_SOCIAL_LOGIN, false);
            Preferences.locationUpdate(activity);
            firebaseSignIn(responseBody, true);
            getVerifyOtpSuccess().postValue(true);
        } else {
            if (!activity.isEmpty(activity.getToken())) {
                gotoMain();
            } else {
                getFirebaseToken();
            }
        }
        activity.disableEnableTouch(false);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        getIsShowProgress().postValue(false);
        activity.disableEnableTouch(false);
        activity.toastMessage(message);
        if (url.equalsIgnoreCase(API_REGISTER)) {

        } else {
            getVerifyOtpSuccess().postValue(false);
        }
    }

    public void register(String uname, String pass, String email, String mob, String pref) {
        if (!activity.isNetworkConnected()) return;

        getIsShowProgress().postValue(true);
        activity.disableEnableTouch(true);

        HashMap<String, String> map = new HashMap<>();
        map.put("username", uname);
        map.put("password", pass);
        map.put("device_token", activity.getToken());
        map.put("device_type", Constants.ANDROID);
        map.put("sys_id", String.valueOf(SYS_ID));
        map.put("platform", ANDROID);
        map.put("email", email);
        map.put("contactNo", mob);
        map.put("mobile_prefix", pref);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_REGISTER, true, map);

    }

    private void firebaseSignIn(String response, boolean isSignUp) {
        mAuth.signInAnonymously().addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    saveData(response, isSignUp);
                }
            } else {
                activity.toastMessage(activity.getString(R.string.authentication_failed));
            }
        });
    }

    private void saveData(String data, boolean isSignUp) {
        Profile userModel = new Gson().fromJson(data, Profile.class);
        if (userModel != null) {
            Branch.getInstance().setIdentity(userModel.email);
            if (userModel.profileType != null && userModel.profileType.id != activity.getProfileTypeId()) {
                if (userModel.profileType.id == Constants.CLIENT_PROFILE) {
                    activity.failureError(activity.getString(R.string.you_are_trying_to_login_with_client_profile));
                } else {
                    activity.failureError(activity.getString(R.string.you_are_trying_to_login_with_agent_profile));
                }
                return;
            }
            Preferences.writeBoolean(activity, Constants.IS_LOGIN, true);
            Preferences.setProfileData(activity, userModel);

            Intercom.client().setUserHash(generateHMACKey(String.valueOf(userModel.id)));
            Intercom.client().registerIdentifiedUser(Registration.create().withUserId(String.valueOf(userModel.id)));
            activity.connectSocket(activity);

            //Update Device Token to firebase
//            if (!activity.isEmpty(activity.getToken())) {
//                gotoMain(isSignUp);
//            } else {
//                getFirebaseToken(isSignUp);
//            }
        }
    }

    private void getFirebaseToken() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            // Get new FCM registration token
            String token = task.getResult();
            Preferences.writeString(activity, Constants.FCM_TOKEN, token);
            gotoMain();
        });
    }

    private String generateHMACKey(String message) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec("sh_B0K7q5gnhvzj46rrYIZ_abGkTrP1cYjXydg09".getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = (sha256_HMAC.doFinal(message.getBytes()));
            StringBuilder result = new StringBuilder();
            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }
            Log.e("HMAC Key", result.toString());
            return result.toString();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return "";
    }

    private void gotoMain() {
        activity.redirectActivity(MyProfileActivity.class);
        activity.finish();
        getIsShowProgress().postValue(false);
    }
}
