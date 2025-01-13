package com.nojom.client.ui.auth;

import static com.nojom.client.util.Constants.ANDROID;
import static com.nojom.client.util.Constants.API_CONTACT_UNIQUE;
import static com.nojom.client.util.Constants.API_FORGET_PASS;
import static com.nojom.client.util.Constants.API_LOGIN;
import static com.nojom.client.util.Constants.API_REGISTER;
import static com.nojom.client.util.Constants.API_RESET_PASS;
import static com.nojom.client.util.Constants.API_SEND_CODE;
import static com.nojom.client.util.Constants.LOGIN_TYPE_NORMAL;
import static com.nojom.client.util.Constants.LOGIN_TYPE_SOCIAL;
import static com.nojom.client.util.Constants.SYS_ID;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityLoginSignUpBinding;
import com.nojom.client.model.ContactCheck;
import com.nojom.client.model.Profile;
import com.nojom.client.model.SendCode;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;

public class LoginSignUpActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private static final int LOGIN = 1;
    private static final int SIGNUP = 0;
    private ActivityLoginSignUpBinding binding;
    private BaseActivity activity;
    private boolean isLoginForm = false, isGoogleLogin, isFbLogin;
    private boolean isNeedToFinish = false;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible = false;
    private boolean isLoginPasswordVisible = false;
    private Dialog dialog;
    private boolean isResend;
    private String forgetPasswordEmail;

    LoginSignUpActivityVM(Application application, ActivityLoginSignUpBinding loginSignUpBinding, BaseActivity loginSignUpActivity) {
        super(application);
        binding = loginSignUpBinding;
        activity = loginSignUpActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);
        binding.rlLoginWithFacebook.setOnClickListener(this);
        binding.rlLoginWithGoogle.setOnClickListener(this);
        binding.imgPassword.setOnClickListener(this);
        binding.imgLPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // For Fb Login
        callbackManager = CallbackManager.Factory.create();
        initFacebook();

        // For Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        if (activity.getIntent() != null) {
            isLoginForm = activity.getIntent().getBooleanExtra(Constants.FROM_LOGIN, false);
            isNeedToFinish = activity.getIntent().getBooleanExtra(Constants.LOGIN_FINISH, false);
        }

        if (isLoginForm) {
            loginVisible();
            Utils.trackAppsFlayerEvent(activity, "Login_Screen");
        } else {
            signupVisible();
            Utils.trackAppsFlayerEvent(activity, "Sign_Up_Screen");
        }

        binding.segmentLoginGroup.setOnPositionChangedListener(position -> {
            if (position == LOGIN) {
                isLoginForm = true;
                loginVisible();
            } else if (position == SIGNUP) {
                isLoginForm = false;
                signupVisible();
            }
        });

        binding.segmentLoginGroup.setPosition(isLoginForm ? 1 : 0, true);

        binding.etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClick(binding.btnLogin);
                return true;
            }
            return false;
        });

        binding.etSPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClick(binding.btnSignup);
                return true;
            }
            return false;
        });

        binding.ccp.registerCarrierNumberEditText(binding.etMobile);
        binding.ccp.setOnCountryChangeListener(() -> {
            binding.etMobile.setText("");
            binding.txtPrefix.setText(binding.ccp.getSelectedCountryCodeWithPlus());
            binding.ccp.setTag(binding.ccp.getSelectedCountryCodeWithPlus());
        });
        binding.txtPrefix.setText(binding.ccp.getSelectedCountryCodeWithPlus());
    }

    private void signupVisible() {
        binding.llLogin.setVisibility(View.GONE);
        binding.llSignup.setVisibility(View.VISIBLE);
        binding.txtGoogleTitle.setText(activity.getString(R.string.signup_with_google));
        binding.txtFbTitle.setText(activity.getString(R.string.signup_with_facebook));
    }

    private void loginVisible() {
        binding.llLogin.setVisibility(View.VISIBLE);
        binding.llSignup.setVisibility(View.GONE);
        binding.txtGoogleTitle.setText(activity.getString(R.string.login_with_google));
        binding.txtFbTitle.setText(activity.getString(R.string.login_with_facebook));
    }

    private void initFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getGraphRequest(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                binding.progressBarFb.setVisibility(View.GONE);
                binding.txtFbTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException e) {
                if (e instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                if (!activity.isEmpty(e.getMessage()))
                    binding.progressBarFb.setVisibility(View.GONE);
                binding.txtFbTitle.setVisibility(View.VISIBLE);
            }
        });
    }

    public String getEmail() {
        return binding.etEmail.getText().toString().trim();
    }

    public String getMobile() {
        return binding.etMobile.getText().toString().trim();
    }

    private String getPassword() {
        return isLoginForm ? binding.etPassword.getText().toString().trim() : binding.etSPassword.getText().toString().trim();
    }

    private String getName() {
        return isLoginForm ? binding.etUsername.getText().toString().trim() : binding.etSUsername.getText().toString().trim();
    }

    public void login() {
        if (!activity.isNetworkConnected()) return;

        activity.disableEnableTouch(true);
        binding.progressBarLogin.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.INVISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("username", getName());
        map.put("password", getPassword());
        map.put("device_token", activity.getToken());
        map.put("device_type", Constants.ANDROID);
        map.put("sys_id", String.valueOf(SYS_ID));
        map.put("loginMethod", LOGIN_TYPE_NORMAL);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_LOGIN, true, map);
    }

    private void register() {
        if (!activity.isNetworkConnected()) return;

        activity.disableEnableTouch(true);
        binding.progressBarSignup.setVisibility(View.VISIBLE);
        binding.btnSignup.setVisibility(View.INVISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("username", getName());
        map.put("password", getPassword());
        map.put("device_token", activity.getToken());
        map.put("device_type", Constants.ANDROID);
        map.put("sys_id", String.valueOf(SYS_ID));
        map.put("platform", ANDROID);
        map.put("email", getEmail());
        map.put("contactNo", getMobile());
        map.put("mobile_prefix", binding.ccp.getSelectedCountryCodeWithPlus());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_REGISTER, true, map);

    }

    private void socialLogin(String fbUserId, String googleUserId, String username, String firstName, String lastName, String email) {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("username", "");
        map.put("device_token", activity.getToken());
        map.put("device_type", Constants.ANDROID);
        map.put("sys_id", String.valueOf(SYS_ID));
        map.put("loginMethod", LOGIN_TYPE_SOCIAL);

        if (!TextUtils.isEmpty(email)) {
            map.put("email", email);
        }
        if (!TextUtils.isEmpty(fbUserId)) {
            isFbLogin = true;
            map.put("facebook_id", fbUserId);
        }
        if (!TextUtils.isEmpty(googleUserId)) {
            isGoogleLogin = true;
            map.put("google_id", googleUserId);
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_LOGIN, true, map);
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
            if (!activity.isEmpty(activity.getToken())) {
                gotoMain(isSignUp);
            } else {
                getFirebaseToken(isSignUp);
            }
        }
        if (isSignUp) {
            binding.progressBarSignup.setVisibility(View.GONE);
            binding.btnSignup.setVisibility(View.VISIBLE);
        } else {
            binding.progressBarLogin.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void getFirebaseToken(boolean isSignUp) {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            // Get new FCM registration token
            String token = task.getResult();
            Preferences.writeString(activity, Constants.FCM_TOKEN, token);
            gotoMain(isSignUp);
        });
    }

    private void gotoMain(boolean isSignUp) {
        if (Task24Application.getInstance().isFromPostJobNGig) {
            Task24Application.getInstance().isFromPostJobNGig = false;
            activity.finish();
        } else {
            if (isSignUp) {
                Intent intent = new Intent(activity, OtpActivity.class);
                intent.putExtra("mobile", getMobile());
                intent.putExtra("prefix", binding.ccp.getSelectedCountryCodeWithPlus());
                activity.startActivity(intent);
            } else {
                if (isNeedToFinish) {
                    activity.finish();
                } else {
                    if (activity.getUserData() != null
                            && activity.getUserData().trustRate != null &&
                            activity.getUserData().trustRate.phoneNumber == 0) {//if number is not verified then again open while login case
                        if (activity.getUserData().contactNo != null) {
                            Intent intent = new Intent(activity, OtpActivity.class);
                            String[] split = activity.getUserData().contactNo.split("\\.");
                            if (split.length == 2) {
                                intent.putExtra("mobile", split[1]);
                                intent.putExtra("prefix", split[0]);
                            }
                            activity.startActivity(intent);
                        } else {
                            activity.gotoMainActivity(Constants.TAB_HOME);
                        }

                    } else {
                        activity.gotoMainActivity(Constants.TAB_HOME);
                    }
                }
            }
        }

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

    private boolean validLoginData() {
        if (activity.isEmpty(getName())) {
            activity.validationError(activity.getString(R.string.enter_username));
            return false;
        }

        if (activity.isEmpty(getPassword())) {
            activity.validationError(activity.getString(R.string.enter_password));
            return false;
        }
        return true;
    }

    private boolean validSignUpData() {
        if (!activity.isValidEmail(getEmail())) {
            activity.validationError(activity.getString(R.string.enter_valid_email));
            return false;
        }

//        if (activity.isEmpty(getName())) {
//            activity.validationError(activity.getString(R.string.enter_username));
//            return false;
//        }
//
//        if (activity.isValidUserName(getName())) {
//            activity.validationError(activity.getString(R.string.enter_valid_username));
//            return false;
//        }
//
//        if (activity.isEmpty(getPassword())) {
//            activity.validationError(activity.getString(R.string.enter_username));
//            return false;
//        }

        if (TextUtils.isEmpty(getMobile())) {
            activity.toastMessage(activity.getString(R.string.please_enter_mobile));
            return false;
        }
        if (getMobile().startsWith("0") || getMobile().startsWith("00")) {
            activity.toastMessage(activity.getString(R.string.number_should_not_start_with_0));
            return false;
        }

        if (getPassword().length() < 8) {
            activity.validationError(activity.getString(R.string.password_length_should_be_min_8));
            return false;
        }

        return true;
    }

    private void showForgotPasswordDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
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
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        this.isResend = isResend;
        forgetPasswordEmail = email;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_FORGET_PASS, true, map);
    }

    private void showSecurityCodeDialog(final String email) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
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
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("otp", otp);
        map.put("password", password);

        this.dialog = dialog;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_RESET_PASS, true, map);
    }


    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    String json = new Gson().toJson(account);
                    socialLogin("", account.getId(), account.getDisplayName(), account.getGivenName(), account.getFamilyName(), account.getEmail());
                    Utils.trackAppsFlayerEvent(activity, "Login_Gmail_Screen");
                }
            } catch (ApiException e) {
                binding.progressBarGoogle.setVisibility(View.GONE);
                binding.txtGoogleTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getGraphRequest(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, (object, response) -> {
            try {
                if (object != null) {
                    String id = object.getString("id");
                    String name = "", first_name = "", last_name = "", email = "";
                    if (object.has("name")) {
                        name = object.getString("name");
                    }
                    if (object.has("first_name")) {
                        first_name = object.getString("first_name");
                    }
                    if (object.has("last_name")) {
                        last_name = object.getString("last_name");
                    }
                    if (object.has("email")) {
                        email = object.getString("email");
                    }
                    Log.e("Fb Response", object.toString());

                    socialLogin(id, "", name, first_name, last_name, email);

                    Utils.trackAppsFlayerEvent(activity, "Login_Facebook_Screen");
                }
            } catch (JSONException e) {
                binding.progressBarFb.setVisibility(View.GONE);
                binding.txtFbTitle.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void googleSignOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(activity, task -> {

        });
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(activity);
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_forgot_password:
                showForgotPasswordDialog();
                break;
            case R.id.btn_login:
                if (validLoginData()) {
                    login();
                }
                break;
            case R.id.btn_signup:
                if (validSignUpData()) {
                    //call contact uniqness API, on based success response call send otp api
                    binding.btnSignup.setVisibility(View.INVISIBLE);
                    binding.progressBarSignup.setVisibility(View.VISIBLE);
                    checkContactUniqueness(getEmail(), binding.ccp.getSelectedCountryCodeWithPlus() + "." + getMobile());
                }
                break;
            case R.id.rl_login_with_google:
                binding.progressBarGoogle.setVisibility(View.VISIBLE);
                binding.txtGoogleTitle.setVisibility(View.INVISIBLE);

                mAuth.signOut();
                mGoogleSignInClient.signOut();

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activity.startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.rl_login_with_facebook:
                binding.progressBarFb.setVisibility(View.VISIBLE);
                binding.txtFbTitle.setVisibility(View.INVISIBLE);

                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
                break;
            case R.id.img_password:
                if (!isPasswordVisible) {
                    // show password
                    binding.etSPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.etSPassword.setSelection(getPassword().length());
                    binding.imgPassword.setImageResource(R.drawable.show_password);
                } else {
                    // hide password
                    binding.etSPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.etSPassword.setSelection(getPassword().length());
                    binding.imgPassword.setImageResource(R.drawable.hide_password);
                }
                isPasswordVisible = !isPasswordVisible;
                break;
            case R.id.img_l_password:
                if (!isLoginPasswordVisible) {
                    // show password
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.etPassword.setSelection(getPassword().length());
                    binding.imgLPassword.setImageResource(R.drawable.show_password);
                } else {
                    // hide password
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.etPassword.setSelection(getPassword().length());
                    binding.imgLPassword.setImageResource(R.drawable.hide_password);
                }
                isLoginPasswordVisible = !isLoginPasswordVisible;
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_LOGIN)) {
            Preferences.writeBoolean(activity, Constants.IS_SOCIAL_LOGIN, false);
            firebaseSignIn(responseBody, false);
            Utils.trackAppsFlayerEvent(activity, "User_Login_Success");
        } else if (url.equalsIgnoreCase(API_REGISTER)) {
            Preferences.writeBoolean(activity, Constants.IS_SOCIAL_LOGIN, false);
            Preferences.locationUpdate(activity);
            firebaseSignIn(responseBody, true);

        } else if (isFbLogin || isGoogleLogin) {
            Preferences.writeBoolean(activity, Constants.IS_SOCIAL_LOGIN, true);
            firebaseSignIn(responseBody, false);
            googleSignOut();
            binding.progressBarFb.setVisibility(View.GONE);
            binding.txtFbTitle.setVisibility(View.VISIBLE);
            binding.progressBarGoogle.setVisibility(View.GONE);
            binding.txtGoogleTitle.setVisibility(View.VISIBLE);
        } else if (url.equalsIgnoreCase(API_RESET_PASS)) {
            activity.toastMessage(message);
            if (dialog != null) {
                dialog.dismiss();
            }
        } else if (url.equalsIgnoreCase(API_FORGET_PASS)) {
            activity.toastMessage(message);
            if (!isResend) showSecurityCodeDialog(forgetPasswordEmail);
        } else if (url.equalsIgnoreCase(API_CONTACT_UNIQUE)) {
            //code sent on number API
            sendCode(binding.ccp.getSelectedCountryCodeWithPlus() + getMobile());
        } else if (url.equalsIgnoreCase(API_SEND_CODE)) {//code sent and redirect on next screen
            binding.btnSignup.setVisibility(View.VISIBLE);
            binding.progressBarSignup.setVisibility(View.GONE);
            Intent intent = new Intent(activity, OtpActivity.class);
            intent.putExtra("mobile", getMobile());
            intent.putExtra("prefix", binding.ccp.getSelectedCountryCodeWithPlus());
            intent.putExtra("uname", getName());
            intent.putExtra("pass", getPassword());
            intent.putExtra("email", getEmail());
            activity.startActivity(intent);
        }
        activity.disableEnableTouch(false);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.disableEnableTouch(false);

        if (url.equalsIgnoreCase(API_LOGIN)) {
            binding.progressBarLogin.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            isFbLogin = false;
            isGoogleLogin = false;
            binding.progressBarFb.setVisibility(View.GONE);
            binding.txtFbTitle.setVisibility(View.VISIBLE);
            binding.progressBarGoogle.setVisibility(View.GONE);
            binding.txtGoogleTitle.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_REGISTER)) {
            binding.progressBarSignup.setVisibility(View.GONE);
            binding.btnSignup.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_RESET_PASS)) {
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_CONTACT_UNIQUE)) {
            activity.toastMessage(message);
            binding.btnSignup.setVisibility(View.VISIBLE);
            binding.progressBarSignup.setVisibility(View.GONE);
        } else {
            isFbLogin = false;
            isGoogleLogin = false;
            binding.progressBarFb.setVisibility(View.GONE);
            binding.txtFbTitle.setVisibility(View.VISIBLE);
            binding.progressBarGoogle.setVisibility(View.GONE);
            binding.txtGoogleTitle.setVisibility(View.VISIBLE);
        }
    }

    private void checkContactUniqueness(String email, String phone) {
        if (!activity.isNetworkConnected()) return;

        ContactCheck contactCheck = new ContactCheck(email, phone);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.checkContactUniqueness(this, activity, API_CONTACT_UNIQUE, contactCheck);
    }

    private void sendCode(String phone) {
        if (!activity.isNetworkConnected()) return;

        SendCode sendCode = new SendCode(phone);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.sendCode(this, activity, API_SEND_CODE, sendCode);
    }


}
