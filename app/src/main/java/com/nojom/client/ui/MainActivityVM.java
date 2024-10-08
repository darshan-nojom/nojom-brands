package com.nojom.client.ui;

import static com.nojom.client.util.Constants.TAB_HOME;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityMainBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.chat.ChatActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.clientprofile.ClientMoreActivity;
import com.nojom.client.ui.clientprofile.MyProfileActivity;
import com.nojom.client.ui.clientprofile.PostJobNewActivity;
import com.nojom.client.ui.home.LawyerHomeActivity;
import com.nojom.client.ui.projects.MyProjectsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.io.File;
import java.util.HashMap;

import io.intercom.android.sdk.Intercom;

class MainActivityVM extends AndroidViewModel implements TabHost.OnTabChangeListener {
    private final ActivityMainBinding binding;
    private final MainActivity activity;
    final private Class lawyerHomeActivity;

    MainActivityVM(Application application, ActivityMainBinding mainBinding, MainActivity mainActivity) {
        super(application);
        binding = mainBinding;
        activity = mainActivity;
        lawyerHomeActivity = LawyerHomeActivity.class;
        initData();
    }

    private void initData() {
        setTab("home", R.drawable.tab_home, lawyerHomeActivity, null);
        setTab("chat", R.drawable.tab_chat, ChatActivity.class, null);
        setTab("plus", R.drawable.tab_plus, PostJobNewActivity.class/*PostJobActivity.class*/, null);
        setTab("search", R.drawable.tab_project, MyProjectsActivity.class, null);
        setTab("profile", R.drawable.tab_profile, ClientMoreActivity.class, null);

        binding.tabhost.setOnTabChangedListener(this);

        try {
            if (activity.getIntent() != null) {
                if (activity.getIntent().hasExtra(Constants.SCREEN_NAME)) {
                    int screen = activity.getIntent().getIntExtra(Constants.SCREEN_NAME, 0);
                    binding.tabhost.setCurrentTab(screen);
                }
                checkForIntentData(activity.getIntent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.tabs.getChildAt(0).setOnClickListener(v -> {
//            LawyerHomeActivity.scrollHandle();
            setHomeTab();
        });
    }


    public void setHomeTab() {
        binding.tabhost.setCurrentTab(TAB_HOME);
    }
    public void setSettingTab() {
        binding.tabhost.setCurrentTab(4);
    }

    void checkForIntentData(Intent intent) {
        try {
            if (intent.hasExtra(Constants.CHAT_ID)) {
                binding.tabhost.setCurrentTab(Constants.TAB_CHAT);
                HashMap<String, String> chatMap = new HashMap<>();
                if (intent.hasExtra("id")) {
                    chatMap.put(Constants.RECEIVER_ID, "" + intent.getIntExtra("id", 0));
                }

                if (intent.hasExtra("username")) {
                    chatMap.put(Constants.RECEIVER_NAME, "" + intent.getStringExtra("username"));
                }

                if (intent.hasExtra("profile_pic") && !TextUtils.isEmpty(intent.getStringExtra("profile_pic"))) {
                    chatMap.put(Constants.RECEIVER_PIC, intent.getStringExtra("path") + File.separator + intent.getStringExtra("profile_pic"));
                } else {
                    chatMap.put(Constants.RECEIVER_PIC, "");
                }

                chatMap.put(Constants.SENDER_ID, getUserID() + "");
                chatMap.put(Constants.SENDER_NAME, getUserName().username);
                chatMap.put(Constants.SENDER_PIC, getUserName().filePath.pathProfilePicClient + getUserName().profilePic);
                chatMap.put("isProject", "1");//1 mean updated record

                Intent i = new Intent(activity, ChatMessagesActivity.class);
                i.putExtra(Constants.CHAT_ID, getUserID() + "-" + intent.getIntExtra("id", 0));  // ClientId - AgentId
                i.putExtra(Constants.CHAT_DATA, chatMap);
                if (getIsVerified() == 1) {
                    activity.startActivity(i);
                } else {
                    Toast.makeText(activity, activity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them), Toast.LENGTH_SHORT).show();
                }
            } else if (intent.hasExtra(Constants.M_TYPE)) {
                String mType = intent.getStringExtra(Constants.M_TYPE);
                String projectId = intent.getStringExtra(Constants.M_PROJECTID);
                if (projectId != null)
                    Preferences.writeString(activity, Constants.PROJECT_ID, projectId);

                if (mType != null) {
                    switch (mType) {
                        case "New Bids":
                        case "Offer Accept":
                        case "Offer Rejected":
                        case "File Submitted":
                            binding.tabhost.setCurrentTab(Constants.TAB_JOB_LIST);
                            break;
                        default:
                            binding.tabhost.setCurrentTab(Constants.TAB_HOME);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTab(String tag, int drawable, Class<?> activityClass, Intent intent) {
        TabHost.TabSpec tabSpec = binding.tabhost.newTabSpec(tag)
                .setIndicator("", ContextCompat.getDrawable(activity, drawable))
                .setContent(intent != null ? intent : new Intent(activity, activityClass));
        binding.tabhost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("home")) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.black));
            View decorView = activity.getWindow().getDecorView(); //set status background black
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); //set status text  light
            }
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.white));// set status background white
        }
    }

    public void gotoMainActivity(int screen) {
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(Constants.SCREEN_NAME, screen);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK /*| Intent.FLAG_ACTIVITY_NEW_TASK*/);
        activity.startActivity(i);
        activity.finish();
        activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void gotoMainActivity(int screen, boolean noAnim) {
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(Constants.SCREEN_NAME, screen);
        activity.startActivity(i);
        activity.finish();
        if (noAnim) {
            activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

    public void redirectActivity(Class<?> activityClass) {
        activity.startActivity(new Intent(activity, activityClass));
        activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }

    public String getUserID() {
        Profile userData = Preferences.getProfileData(activity);
        return String.valueOf(userData != null ? userData.id : 0);
    }

    public Profile getUserName() {
        Profile userData = Preferences.getProfileData(activity);
        return userData;
    }

    public Integer getIsVerified() {
        Profile userData = Preferences.getProfileData(activity);
        return userData.is_verified;
    }

    void onResumeMethod() {
        Intercom.client().handlePushMessage();
//        if (getIsVerified() != null && (getIsVerified() == 0 || getIsVerified() == 2)) {
//            activity.redirectActivity(MyProfileActivity.class);
//        }
    }

}
