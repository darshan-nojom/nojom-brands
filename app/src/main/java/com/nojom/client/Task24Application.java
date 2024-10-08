package com.nojom.client;

import static com.nojom.client.util.Constants.STRIPE_KEY_PRODUCTION;
import static com.nojom.client.util.Constants.STRIPE_KEY_TEST;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.util.Base64;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.stripe.android.PaymentConfiguration;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Foreground;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.Objects;

import io.branch.referral.Branch;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;
import io.socket.client.IO;
import io.socket.client.Socket;

public class Task24Application extends MultiDexApplication {
    public static String referrerUid = null;
    private static Task24Application instance;
    public String promoCode = "";
    public boolean isGigShow = true, isFromPostJobNGig = false, isFromSplashActivity = false;
    public String paymentCardType = "", paymentPayPalType = "", paymentGoogleType = "", paymentVenmoType = "";
    private BaseActivity activity;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Socket mSocketMsg;

    {
        try {
            mSocketMsg = IO.socket(Constants.BASE_URL_CHAT_MSG);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Task24Application getInstance() {
        return instance;
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activity = new BaseActivity();
        Foreground.init(this);
        Branch.getAutoInstance(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        PaymentConfiguration.init(this, BuildConfig.DEBUG ? STRIPE_KEY_TEST : STRIPE_KEY_PRODUCTION);

        // Intercom initialize
        Intercom.initialize(this, "android_sdk-1c34857d121a5aca7c6b046b3ffe9ccc6f8605fa", "cmde9nds");
        Registration registration = Registration.create();
        if (Preferences.getProfileData(this) != null) {
            registration.withEmail(Objects.requireNonNull(Preferences.getProfileData(this)).email);
            registration.withUserId(String.valueOf(Objects.requireNonNull(Preferences.getProfileData(this)).id));
            Intercom.client().registerIdentifiedUser(registration);
        } else {
            Intercom.client().registerUnidentifiedUser();
        }

        Preferences.writeString(this, Constants.SERVICE_NAME, "");
        Utils.checkForGigShow(activity);

        // Hash key for facebook login
        printHashKey();

        if (!AppCenter.isConfigured()) {
            AppCenter.start(this, "5e1dc7c4-0ae5-41c9-b8f2-70fa50089b63", Analytics.class, Crashes.class);
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash Key: ", hashKey);
            }
        } catch (Exception e) {
            Log.e("Hash Key: ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public Socket getSocketMsg() {
        return mSocketMsg;
    }

}
