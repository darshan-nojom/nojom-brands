package com.nojom.client.ui;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.nojom.client.util.Constants.API_GET_CLIENT_PROFILE;
import static com.nojom.client.util.Constants.API_GET_EXPERT_INFO;
import static com.nojom.client.util.Constants.API_REMOVE_AGENT;
import static com.nojom.client.util.Constants.API_REMOVE_GIG;
import static com.nojom.client.util.Constants.API_SAVE_AGENT;
import static com.nojom.client.util.Constants.API_SAVE_GIG;
import static com.nojom.client.util.Constants.API_SEND_FEEDBACK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.button.CustomButton;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.util.Base64OutputStream;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nojom.client.BuildConfig;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiClient;
import com.nojom.client.api.ApiInterface;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ChatList;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.Profile;
import com.nojom.client.model.SaveRemoveAgent;
import com.nojom.client.model.SaveRemoveGig;
import com.nojom.client.ui.auth.LoginSignUpActivity;
import com.nojom.client.ui.auth.LoginSignUpDialog;
import com.nojom.client.ui.chat.chatInterface.ChatInterface;
import com.nojom.client.ui.chat.chatInterface.NewMessageForList;
import com.nojom.client.ui.chat.chatInterface.OnlineOfflineListener;
import com.nojom.client.ui.workprofile.CallerActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.SaveRemoveClickListener;
import com.nojom.client.util.SaveRemoveGigClickListener;
import com.nojom.client.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BaseActivity extends AppCompatActivity implements RequestResponseListener {

    public boolean isClickableView;
    public boolean doubleBackToExitPressedOnce = false;
    public Socket mSocket;
    public ChatInterface chatInterface;
    public ChatList.Datum moUserStatus;
    public OnlineOfflineListener onlineOfflineListener;
    public NewMessageForList newMessageForList;
    private OnProfileLoadListener onProfileLoadListener;
    private SaveRemoveClickListener saveRemoveClickListener;
    private SaveRemoveGigClickListener saveRemoveGigClickListener;
    private LayoutInflater inflater;
    private Dialog dialogFeedback;
    private CircularProgressBar progressBarFeedback;
    private TextView tvSendFeedback;
    private ExpertInfoListener expertInfoListener;
    private final Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                Log.e("AAAAA", "diconnected");
                if (chatInterface != null)
                    chatInterface.disconnect(true);
            });
        }
    };
    private final Emitter.Listener onlineParticularUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                if (args != null) {
                    moUserStatus = new Gson().fromJson(args[0].toString(), ChatList.Datum.class);
                    if (moUserStatus != null) {
                        if (onlineOfflineListener != null)
                            onlineOfflineListener.onlineUser(moUserStatus);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final Emitter.Listener offlineParticularUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                if (args != null) {
                    moUserStatus = new Gson().fromJson(args[0].toString(), ChatList.Datum.class);
                    if (moUserStatus != null) {
                        if (onlineOfflineListener != null)
                            onlineOfflineListener.offlineUsr(moUserStatus);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                if (chatInterface != null)
                    chatInterface.onError(args);

                if (!mSocket.connected())
                    mSocket.connect();
            });
        }
    };

    private final Emitter.Listener userVerifyError = args -> {
    };
    public String formatValue(double value) {
        // If there's no fraction part, show without decimal
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            // Else, show with decimal
            return String.valueOf(value);
        }
    }
    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                onVerifyUser();
                Log.e("AAAAA", "start connect..");
                if (chatInterface != null)
                    chatInterface.onConnect(true);
            });
        }
    };

    public void openWhatsappChat(String no) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://wa.me/" + no));
        startActivity(intent);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String language = Preferences.readString(this, Constants.SELECTED_LANGUAGE, "en");
        if (language.equals("ar"))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        super.onCreate(savedInstanceState);

        loadAppLanguage();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isLogin()) {
            connectSocket(this);
        }
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public String getLanguage() {
        return Preferences.readString(this, Constants.SELECTED_LANGUAGE, "en");
    }

    public String getCurrency() {
        return Preferences.readString(this, Constants.PREF_SELECTED_CURRENCY, "SAR");
    }

    public boolean isVerificationDone() {
        return Preferences.readBoolean(this, Constants.PREF_IS_VERIFICATION, false);
    }

    public void setVerificationDone() {
        Preferences.writeBoolean(this, Constants.PREF_IS_VERIFICATION, true);
    }

    public void loadAppLanguage() {
        String language = Preferences.readString(this, Constants.SELECTED_LANGUAGE, "");
        if (!TextUtils.isEmpty(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Resources res = getResources();
            DisplayMetrics dm = getBaseContext().getResources().getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(locale); // API 17+ only.
            switch (language) {
                case "ar":
                case "es":
                    conf.setLayoutDirection(locale);
                    break;
                case "en":
                    conf.locale = Locale.ENGLISH;
                    conf.setLayoutDirection(new Locale("en"));
                    break;
            }
            res.updateConfiguration(conf, dm);
        }
    }

    public ApiInterface getService() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

    public void failureError(String message) {
        if (!isEmpty(message))
            toastMessage(message);
    }

    public void validationError(String message) {
        toastMessage(message);
    }

    public void toastMessage(String message) {
        try {
            Toast toast = new Toast(getApplicationContext());
            if (message.contains("account")) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            View view = inflater.inflate(R.layout.custom_toast, null);
            TextView tvToast = view.findViewById(R.id.tvToast);
            tvToast.setText(message);
            toast.setGravity(Gravity.TOP, 20, 20);
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkStoragePermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void addFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .disallowAddToBackStack()
                .commit();
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragmentWithTarget(@NonNull Fragment sourceFragment, @NonNull Fragment targetFragment, int fragmentCode) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        targetFragment.setTargetFragment(sourceFragment, fragmentCode);
        ft.replace(R.id.container, targetFragment, "");
        ft.addToBackStack(sourceFragment.getClass().getName());
        ft.commit();
    }

    public void onBackPressedEvent() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        toastMessage(getString(R.string.click_back_again_to_exit));

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }

    public int getProfileTypeId() {
        return Constants.CLIENT_PROFILE;
    }


    public boolean isLogin() {
        return Preferences.readBoolean(this, Constants.IS_LOGIN, false);
    }

    public String getToken() {
        return Preferences.readString(this, Constants.FCM_TOKEN, "");
    }

    public String getJWT() {
        try {
            return Preferences.readString(this, Constants.JWT, "");
        } catch (Exception e) {
            return null;
        }
    }

    public int getUserID() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.id : 0;
    }

    public Profile getUserData() {
        return Preferences.getProfileData(this);
    }


    public String getReferralCode() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.referralCode : "";
    }

    public String getUserName() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.username : "";
    }

    public String getProfilePic() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.profilePic : "";
    }

    public String getEmail() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.email : "";
    }

    public String getPhone() {
        Profile userData = Preferences.getProfileData(this);
        return userData != null ? userData.contactNo : "";
    }

    public String get2DecimalPlaces(Object o) {
        if (o instanceof Double)
            return Utils.numberFormat((double) o, 2);
        else if (o instanceof Integer)
            return Utils.numberFormat((int) o, 2);
        else if (o instanceof Float)
            return Utils.numberFormat((float) o, 1);

        return Utils.numberFormat((String) o, 2);
    }

    public String get1DecimalPlaces(Object o) {
        if (o instanceof Double)
            return Utils.numberFormat((double) o, 1);
        else if (o instanceof Integer)
            return Utils.numberFormat((int) o, 1);
        else if (o instanceof Float)
            return Utils.numberFormat((float) o, 1);

        return Utils.numberFormat((String) o, 1);
    }

    public String getDecimalPlaces(Object o) {
        return Utils.numberFormat((String) o);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() != null) {
            return true;
        }
        internetDialog();
        return false;
    }

    public boolean isNetworkConnectedDialog() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }

    private void internetDialog() {
        try {
            final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
            dialog.setTitle(null);
            dialog.setContentView(R.layout.dialog_no_internet);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            CustomButton btnRetry = dialog.findViewById(R.id.btn_retry);
            CustomButton btnSettings = dialog.findViewById(R.id.btn_settings);

            btnRetry.setOnClickListener(v -> {
                if (isNetworkConnectedDialog()) {
                    dialog.dismiss();
                    if (Task24Application.getInstance().isFromSplashActivity) {
                        Task24Application.getInstance().isFromSplashActivity = false;
                        redirectActivity(CallerActivity.class);
                        finish();
                    }
                }
            });

            btnSettings.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.CENTER;
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoMainActivity(int screen) {
        if (getParent() != null) {
            ((MainActivity) getParent()).gotoMainActivity(screen);
        } else {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(Constants.SCREEN_NAME, screen);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK /*| Intent.FLAG_ACTIVITY_NEW_TASK*/);
            startActivity(i);
            finish();
            finishToRight();
        }
    }


    public void redirectTab(int tabIndex) {
        if (getParent() != null) {
            ((MainActivity) getParent()).getTabHost().setCurrentTab(tabIndex);
        }
    }

    public void openLoginScreen(boolean isLogin) {
        Intent i = new Intent(this, LoginSignUpActivity.class);
        i.putExtra(Constants.FROM_LOGIN, isLogin);
        startActivity(i);
        openToLeft();
    }

    public void openLoginDialog() {
        new LoginSignUpDialog(this);
    }


    public void redirectActivity(Class<?> activityClass) {
        if (getParent() != null) {
            ((MainActivity) getParent()).redirectActivity(activityClass);
        } else {
            Intent i = new Intent(this, activityClass);
            i.putExtra(Constants.SCREEN_NAME, Constants.TAB_HOME);
            startActivity(i);
            openToLeft();
        }
    }

    public void openToTop() {
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    public void openToLeft() {
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public void finishToRight() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public boolean isValidEmail(String target) {
        if (!BuildConfig.DEBUG && !TextUtils.isEmpty(target) && target.contains("@mailinator")) {
            return false;
        }
        return (!isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isValidMobile(String phone) {
        if (phone.contains("+"))
            phone = phone.replace("+", "");
        return (!isEmpty(phone) && Double.parseDouble(phone) > 0 && Patterns.PHONE.matcher(phone).matches() && phone.length() > 6);
    }

    public boolean isValidUrl(String url) {
        return (!isEmpty(url) && Patterns.WEB_URL.matcher(url.toLowerCase()).matches());
    }

    public boolean isValidUserName(String input) {
        String regularExpression = "^[a-zA-Z0-9._-]*$";
        return !input.matches(regularExpression);
    }

    public boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public void redirectUsingCustomTab(String url) {
        try {
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(this, uri);
        } catch (Exception e) {
            e.printStackTrace();
            failureError(getString(R.string.something_went_wrong));
        }
    }

    public void showFeedbackDialog(Integer screen, String message) {
        dialogFeedback = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialogFeedback.setTitle(null);
        dialogFeedback.setContentView(R.layout.dialog_feedback);
        dialogFeedback.setCancelable(true);
        TextView tvTitle = dialogFeedback.findViewById(R.id.tv_title);
        TextView tvCancel = dialogFeedback.findViewById(R.id.tv_cancel);
        tvSendFeedback = dialogFeedback.findViewById(R.id.tv_send);
        progressBarFeedback = dialogFeedback.findViewById(R.id.progress_bar);

        final EditText etFeedback = dialogFeedback.findViewById(R.id.et_feedback);
        tvCancel.setOnClickListener(v -> dialogFeedback.dismiss());

        tvTitle.setText(message);

        switch (screen) {
            case 1:
                etFeedback.setHint(getString(R.string.did_you_encounter_any_problems_tell_us_about_the_issue_you_are_facing_with_and_help_us_resolve_it));
                break;
            case 2:
                etFeedback.setHint(getString(R.string.specify_the_issue_that_you_are_facing_with_the_app_in_detail));
                break;
            case 3:
                etFeedback.setHint(getString(R.string.help_us_serve_you_better_give_us_your_feedback_and_suggestions));
                break;
            case 4:
                etFeedback.setHint(getString(R.string.do_you_want_to_say_anything_send_your_message_feedback_compliments_ideas_or_suggestions_to_improve_our_service));
                break;
        }

        tvSendFeedback.setOnClickListener(v -> {
            if (!isEmpty(etFeedback.getText().toString().trim())) {
                sendFeedback(etFeedback.getText().toString(), message);
            } else {
                failureError(getString(R.string.enter_message));
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialogFeedback.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialogFeedback.show();
        dialogFeedback.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFeedback.getWindow().setAttributes(lp);
    }

    public void sendFeedback(String message, String title) {
        if (!isNetworkConnected())
            return;

        progressBarFeedback.setVisibility(View.VISIBLE);
        tvSendFeedback.setVisibility(View.INVISIBLE);
        isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("note", message);
        map.put("device_type", Constants.ANDROID);
        map.put("app_version", BuildConfig.VERSION_NAME);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, this, API_SEND_FEEDBACK, true, map);
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share via..."));
    }

    public void shareApp(String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share via..."));
    }

    public void viewFile(File file) {
        try {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = getUriForFile(this, getPackageName() + ".provider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            String mime;
            if (file.getPath().contains(".doc") || file.getPath().contains(".docx")) {
                mime = "application/msword";
            } else if (file.getPath().contains(".txt")) {
                mime = "text/plain";
            } else if (file.getPath().contains(".pdf")) {
                mime = "application/pdf";
            } else if (file.getPath().contains(".ppt") || file.getPath().contains(".pptx")) {
                mime = "application/vnd.ms-powerpoint";
            } else if (file.getPath().contains(".xls") || file.getPath().contains(".xlsx")) {
                mime = "application/vnd.ms-excel";
            } else if (file.getPath().contains(".jpg") || file.getPath().contains(".png") || file.getPath().contains(".jpeg")) {
                mime = "image/*";
            } else if (file.getPath().contains(".mp4") || file.getPath().contains(".avi")) {
                mime = "video/*";
            } else {
                mime = "*/*";
            }
            intent.setDataAndType(uri, mime);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            toastMessage(getString(R.string.no_application_available_to_view_this_type_of_file));
            e.printStackTrace();
        }
    }

    public void viewFile(String fileUrl) {
        try {
            String driveUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
            String finalFileUrl;
            String mime;
            if (fileUrl.contains(".doc") || fileUrl.contains(".docx")) {
                mime = "application/msword";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".txt")) {
                mime = "text/plain";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".pdf")) {
                mime = "application/pdf";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".ppt") || fileUrl.contains(".pptx")) {
                mime = "application/vnd.ms-powerpoint";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".xls") || fileUrl.contains(".xlsx")) {
                mime = "application/vnd.ms-excel";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".jpg") || fileUrl.contains(".png") || fileUrl.contains(".jpeg") || fileUrl.contains(".gif")) {
                mime = "image/*";
                finalFileUrl = fileUrl;
            } else if (fileUrl.contains(".mp4") || fileUrl.contains(".avi")) {
                mime = "video/*";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".zip") || fileUrl.contains(".rar")) {
                // WAV audio file
                mime = "application/x-wav";
                finalFileUrl = driveUrl + fileUrl;
            } else {
                mime = "*/*";
                finalFileUrl = driveUrl + fileUrl;
            }

            if (mime.equalsIgnoreCase("image/*")) {
                intent.setDataAndType(Uri.parse(finalFileUrl), mime);
            } else {
                intent.setData(Uri.parse(finalFileUrl));
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        } catch (Exception e) {
            toastMessage(getString(R.string.no_application_available_to_view_this_type_of_file));
            e.printStackTrace();
        }
    }

    public void showContactUsDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_chat_now);
        dialog.setCancelable(true);
        View call = dialog.findViewById(R.id.rl_call);
        View messanger = dialog.findViewById(R.id.rl_messanger);
        View whatsapp = dialog.findViewById(R.id.rl_whatsapp);
        View email = dialog.findViewById(R.id.rl_email);
        View sms = dialog.findViewById(R.id.rl_sms);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        TextView lblCall = dialog.findViewById(R.id.lbl_call);
        TextView lblMess = dialog.findViewById(R.id.lbl_messenger);
        TextView lblWhats = dialog.findViewById(R.id.lbl_whatsapp);
        TextView lblEmail = dialog.findViewById(R.id.lbl_email);
        TextView lblSms = dialog.findViewById(R.id.lbl_sms);

        lblCall.setText(getString(R.string.call));
        lblMess.setText(getString(R.string.messenger));
        lblWhats.setText(getString(R.string.whatsapp));
        lblEmail.setText(getString(R.string.email));
        lblSms.setText(getString(R.string.sms));

        call.setOnClickListener(v -> makeCall());

        messanger.setOnClickListener(v -> openMessenger());

        whatsapp.setOnClickListener(v -> openWhatsApp());

        email.setOnClickListener(v -> openEmail());

        sms.setOnClickListener(v -> openSMS());

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public void makeCall() {
        Dexter.withActivity(BaseActivity.this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL,
                                    Uri.parse("tel:" + getString(R.string.phone_number)));
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            failureError(getString(R.string.failed_to_invoke_call));
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        failureError(getString(R.string.please_give_permission_to_make_call));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void openWhatsApp() {
        try {
            String toNumber = getString(R.string.phone_number);
            toNumber = toNumber.replace("+", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace("-", "")
                    .replace(" ", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "");
            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            failureError("Please install WhatsApp");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")));
        }
    }

    public void openMessenger() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nojomApps")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nojomApps")));
        }
    }

    public void openEmail() {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", getString(R.string.email_text).toLowerCase(), null));
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (Exception e) {
            failureError(getString(R.string.mail_apps_not_installed));
        }
    }

    public void openSMS() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", getString(R.string.phone_number), null)));
        } catch (Exception e) {
            e.printStackTrace();
            failureError(getString(R.string.inbox_not_found));
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_GET_EXPERT_INFO)) {
            try {
                ExpertDetail expertDetail = ExpertDetail.getExpertInfo(responseBody);
                if (expertDetail != null) {
                    expertInfoListener.onExpertSuccess(expertDetail);
                }
            } catch (Exception e) {
                expertInfoListener.onExpertFail();
                e.printStackTrace();
            }
        } else if (url.equalsIgnoreCase(API_GET_CLIENT_PROFILE)) {
            Profile profile = Profile.getProfileInfo(responseBody);
            if (profile != null) {
                Preferences.setProfileData(BaseActivity.this, profile);
                if (onProfileLoadListener != null) {
                    onProfileLoadListener.onProfileLoad(profile);
                }
            }
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT)) {
            SaveRemoveAgent saveRemoveAgent = SaveRemoveAgent.getSaveRemoveAgent(responseBody);
            if (saveRemoveClickListener != null && saveRemoveAgent != null) {
                saveRemoveClickListener.removeAgentSuccess(saveRemoveAgent.agentProfileId);
            }
        } else if (url.equalsIgnoreCase(API_SAVE_AGENT)) {
            SaveRemoveAgent saveRemoveAgent = SaveRemoveAgent.getSaveRemoveAgent(responseBody);
            if (saveRemoveClickListener != null && saveRemoveAgent != null) {
                saveRemoveClickListener.savedAgentSuccess(saveRemoveAgent.agentProfileId);
            }
        } else if (url.equalsIgnoreCase(API_REMOVE_GIG)) {
            SaveRemoveGig saveRemoveAgent = SaveRemoveGig.getSaveRemoveGig(responseBody);
            if (saveRemoveGigClickListener != null && saveRemoveAgent != null) {
                saveRemoveGigClickListener.removeGigSuccess(saveRemoveAgent.gigID);
            }
        } else if (url.equalsIgnoreCase(API_SAVE_GIG)) {
            SaveRemoveGig saveRemoveAgent = SaveRemoveGig.getSaveRemoveGig(responseBody);
            if (saveRemoveGigClickListener != null && saveRemoveAgent != null) {
                saveRemoveGigClickListener.savedGigSuccess(saveRemoveAgent.gigID);
            }
        } else if (url.equalsIgnoreCase(API_SEND_FEEDBACK)) {
            progressBarFeedback.setVisibility(View.GONE);
            tvSendFeedback.setVisibility(View.VISIBLE);
            dialogFeedback.dismiss();
            toastMessage(message);
        }
        isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_GET_EXPERT_INFO)) {
            expertInfoListener.onExpertFail();
        } else if (url.equalsIgnoreCase(API_SEND_FEEDBACK)) {
            progressBarFeedback.setVisibility(View.GONE);
            tvSendFeedback.setVisibility(View.VISIBLE);
        }
        isClickableView = false;
    }

    public void setOnProfileLoadListener(OnProfileLoadListener onProfileLoadListener) {
        this.onProfileLoadListener = onProfileLoadListener;
    }

    public void getProfile() {
        if (!isNetworkConnectedDialog())
            return;

        if (TextUtils.isEmpty(getJWT())) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, this, API_GET_CLIENT_PROFILE, false, null);
    }

    public String printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        return String.valueOf(elapsedDays);

    }

    public boolean isValidMail(String email) {
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(email);
        while (m.find()) {
            return true;
        }
        return false;
    }

    public boolean isValidMobileWholeContent(String phone) {
        Pattern p = Pattern.compile("(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})");
        Matcher m = p.matcher(phone);

        while (m.find()) {
            return true;
        }
        return false;
    }

    public void setExpertInfoListener(ExpertInfoListener expertInfoListener) {
        this.expertInfoListener = expertInfoListener;
    }

    public void getExpert(int expertId) {
        if (!isNetworkConnected())
            return;

        expertInfoListener.onPreExpert();

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", String.valueOf(expertId));

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, this, API_GET_EXPERT_INFO, true, map);
    }


    public void rateThisAppDialog(RateClickListener listener) {
        try {
            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_rate_app);
            dialog.setCancelable(false);

            // set the custom dialog components - text, image and button
            CustomTextView textCancel = dialog.findViewById(R.id.tv_cancel);
            CustomTextView textRateNow = dialog.findViewById(R.id.tv_rate_now);

            textCancel.setOnClickListener(v -> {
                dialog.dismiss();
                listener.onClickRateDialog(true);
            });

            textRateNow.setOnClickListener(v -> {
                dialog.dismiss();
                listener.onClickRateDialog(false);
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    failureError("Unable to find market app");
                }
            });

            if (!isFinishing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRemoveAgent(int agentId, SaveRemoveClickListener saveRemoveClickListener, boolean isRemove) {
        if (!isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentId + "");

        this.saveRemoveClickListener = saveRemoveClickListener;

        ApiRequest apiRequest = new ApiRequest();

        if (isRemove) {
            apiRequest.apiRequest(this, this, API_REMOVE_AGENT, true, map);
        } else {
            apiRequest.apiRequest(this, this, API_SAVE_AGENT, true, map);
        }
    }

    public void saveRemoveGig(int gigId, SaveRemoveGigClickListener saveRemoveGigClickListener, boolean isRemove) {
        if (!isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("gigID", gigId + "");

        this.saveRemoveGigClickListener = saveRemoveGigClickListener;

        ApiRequest apiRequest = new ApiRequest();

        if (isRemove) {
            apiRequest.apiRequest(this, this, API_REMOVE_GIG, true, map);
        } else {
            apiRequest.apiRequest(this, this, API_SAVE_GIG, true, map);
        }
    }

    public void disableEnableTouch(boolean isLoading) {
        try {
            if (isLoading) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setImage(ImageView view, String imageUrl, int height, int width) {
        Utils.setImage(view, imageUrl, height, width);
    }

    public void setImage(ImageView view, String imageUrl, int height, int width, boolean isPlaceholder) {
        Utils.setImage(view, imageUrl, height, width, isPlaceholder);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isClickableView)
            return super.dispatchTouchEvent(ev);
        return true;
    }

    public void setChatInterface(ChatInterface chatInterface) {
        this.chatInterface = chatInterface;
    }

    public void setNewMessageForList(NewMessageForList newMessage) {
        this.newMessageForList = newMessage;
    }

    public void setOnlineOfflineListener(OnlineOfflineListener onlineOfflineListener) {
        this.onlineOfflineListener = onlineOfflineListener;
    }

    public void onVerifyUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profile_id", getUserID());
            mSocket.emit("verifyUser", jsonObject);
            mSocket.on("userVerifyError", userVerifyError);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void connectSocket(Activity activity) {
        Task24Application app = (Task24Application) activity.getApplicationContext();
        mSocket = app.getSocketMsg();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_ERROR, onError);
        mSocket.on("offlineParticularUser", offlineParticularUser);
        mSocket.on("onlineParticularUser", onlineParticularUser);
        if (!mSocket.connected())
            mSocket.connect();
    }

    public String getStringFile(File f) {
        InputStream inputStream = null;
        ByteArrayOutputStream output = null;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());
            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, android.util.Base64.DEFAULT);
            int i = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
                i++;
            }
            output64.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public void setEnableDisableView(View view) {
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true), 1000);
    }

    public void onLogout(BaseActivity activity) {
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

        Intent i = new Intent(activity, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(i);
    }

    public interface OnProfileLoadListener {
        void onProfileLoad(Profile data);
    }

    public interface ExpertInfoListener {
        void onExpertSuccess(ExpertDetail expertDetail);

        void onExpertFail();

        void onPreExpert();
    }

    public void openDocuments(BaseFragment activity, int numOfFile) {
        Intent intent;
        if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
            intent.putExtra("CONTENT_TYPE", "*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        } else {

            String[] mimeTypes =
                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "application/zip", "application/vnd.android.package-archive"};

            intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        }
        activity.startActivityForResult(intent, 4545);

    }

    public void openDocuments(BaseActivity activity, int numOfFile) {
        Intent intent;
        if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
            intent.putExtra("CONTENT_TYPE", "*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        } else {

            String[] mimeTypes =
                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "application/zip", "application/vnd.android.package-archive"};

            intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        }
        activity.startActivityForResult(intent, 4545);

    }

    public void openDocuments(BaseActivity activity, int numOfFile, int code) {
        Intent intent;
        if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
            intent.putExtra("CONTENT_TYPE", "*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        } else {

            String[] mimeTypes =
                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "application/zip", "application/vnd.android.package-archive"};

            intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        }
        startActivityForResult(intent, code);
    }


    public String formatNumber(long number) {
        if (number > 1_000_000_000) {
            return format(number / 1_000_000_000.0) + " " + getString(R.string._b);
        } else if (number >= 1_000_000) {
            return format(number / 1_000_000.0) + " " + getString(R.string._m);
        } else if (number >= 1_000) {
            return format(number / 1_000.0) + " " + getString(R.string._k);
        } else {
            return String.valueOf(number);
        }
    }

    public String format(double value) {
        // Use DecimalFormat to format the number with one decimal place
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        decimalFormat.setDecimalFormatSymbols(symbols);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);

        return decimalFormat.format(value);
    }

    public Integer getIsVerified() {
        Profile userData = Preferences.getProfileData(this);
        if (userData != null) {
            return userData.is_verified != null ? userData.is_verified : 0;
        }
        return 0;
    }
}
