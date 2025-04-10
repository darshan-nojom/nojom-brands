package com.nojom.client.ui;

import static com.nojom.client.Task24Application.referrerUid;
import static com.nojom.client.util.Constants.KEY_FORCE_UPDATE_REQUIRED;
import static com.nojom.client.util.Constants.REFERRAL_ID_FROM_LINK;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.nojom.client.BuildConfig;
import com.nojom.client.Task24Application;
import com.nojom.client.ui.workprofile.CallerActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class SplashActivityVM extends AndroidViewModel {
    private BaseActivity activity;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String latestPlaystoreVersion = "";

    SplashActivityVM(Application application, BaseActivity splashActivity) {
        super(application);
        activity = splashActivity;
        initData();
    }

    private void initData() {
        Preferences.writeString(activity, "gigID", null);
        Preferences.writeString(activity, "influencerName", null);
        Utils.checkForFirebasePromoCode(activity);

        try {
            VersionChecker versionChecker = new VersionChecker();
            latestPlaystoreVersion = versionChecker.execute().get();

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction() != null) {
                        if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                            checkForNewVersion();
                        }
                    }
                }
            };

            String version = Preferences.readString(activity, Constants.APP_VERSION, "1.0");
            if (!TextUtils.isEmpty(version) && !version.equals(BuildConfig.VERSION_NAME)) {
                Preferences.writeBoolean(activity, Constants.IS_LOGIN, false);
                Preferences.writeString(activity, Constants.APP_VERSION, BuildConfig.VERSION_NAME);
            }

            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(activity.getIntent())
                    .addOnSuccessListener(activity, pendingDynamicLinkData -> {
                        Uri deepLink = null;
                        String profileUrl = "", serviceURL = "";
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            profileUrl = pendingDynamicLinkData.getLink().getPath();
                            serviceURL = pendingDynamicLinkData.getLink().getPath();
                        }

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user == null && deepLink != null && deepLink.getBooleanQueryParameter("code", false)) {
                            referrerUid = deepLink.getQueryParameter("code");
                            Preferences.writeString(activity, REFERRAL_ID_FROM_LINK, referrerUid);
                        } else if (profileUrl != null && profileUrl.contains("/")) {
                            String influencerName = profileUrl.substring(profileUrl.lastIndexOf("/") + 1);
                            Preferences.writeString(activity, "influencerName", influencerName);
                            redirectIntent();
                        } else if (serviceURL != null && serviceURL.contains("services")) {
                            String gigName = serviceURL.substring(serviceURL.lastIndexOf("/") + 1);
                            String gigID = gigName.substring(gigName.lastIndexOf("-") + 1);
                            Preferences.writeString(activity, "gigID", gigID);
                            redirectIntent();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForNewVersion() {
        try {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3)
                    .build();
            remoteConfig.setConfigSettingsAsync(configSettings);
            remoteConfig.fetchAndActivate()
                    .addOnCompleteListener(activity, task -> {
                        if (!task.isSuccessful()) {
                            redirectIntent();
                            return;
                        }

                        String appVersion = BuildConfig.VERSION_NAME;

                        if (remoteConfig.getBoolean(KEY_FORCE_UPDATE_REQUIRED)) {//force update
                            if (!TextUtils.isEmpty(latestPlaystoreVersion) && !TextUtils.equals(latestPlaystoreVersion, appVersion)) {
                                showUpdateDialog(true);
                            } else {
                                redirectIntent();
                            }
                        } else {//check if version not match than open simple dialog
                            if (!TextUtils.isEmpty(latestPlaystoreVersion) && !TextUtils.equals(latestPlaystoreVersion, appVersion)) {//version not equal so open alert dialog
                                showUpdateDialog(false);
                            } else {
                                redirectIntent();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog(boolean isForceUpdate) {
        String title = "A new version available";
        String message = "A new version of nojom is available. Please, update app to new version to continue.";
        String redirectUrl = "https://play.google.com/store/apps/details?id=com.nojom.client&hl=en";
        if (isForceUpdate) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Update",
                            (dialog14, which) -> {
                                redirectStore(redirectUrl);
                                dialog14.dismiss();
                            })
                    .setNegativeButton("Exit",
                            (dialog13, which) -> activity.finish()).create();
            dialog.show();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Update",
                            (dialog1, which) -> {
                                redirectStore(redirectUrl);
                                dialog1.dismiss();
                            })
                    .setNegativeButton("Cancel",
                            (dialog12, which) -> {
                                redirectIntent();
                                dialog12.dismiss();
                            }).create();
            dialog.show();
        }
    }

    private void redirectStore(String updateUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    private void redirectIntent() {
        if (!activity.isNetworkConnected()) {
            Task24Application.getInstance().isFromSplashActivity = true;
            return;
        }
        Intent intent = new Intent(activity, CallerActivity.class);
        if (activity.getIntent().hasExtra("screen_name")) {
            intent.putExtra("s_name", activity.getIntent().getStringExtra("screen_name"));
            intent.putExtra("camp_id", activity.getIntent().getStringExtra("campaign_id"));
        }
        activity.startActivity(intent);
        activity.finish();
    }

    void onResumeMethod() {
        try {
            if (mRegistrationBroadcastReceiver != null) {
                LocalBroadcastManager.getInstance(activity).registerReceiver(mRegistrationBroadcastReceiver,
                        new IntentFilter(Constants.REGISTRATION_COMPLETE));
            }
            if (!activity.isEmpty(activity.getToken())) {
                checkForNewVersion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onPauseMethod() {
        if (mRegistrationBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(activity).unregisterReceiver(mRegistrationBroadcastReceiver);
            mRegistrationBroadcastReceiver = null;
        }
    }

    public class VersionChecker extends AsyncTask<String, String, String> {

        String newVersion;

        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return newVersion;
        }
    }
}
