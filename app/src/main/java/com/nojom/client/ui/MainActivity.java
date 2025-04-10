package com.nojom.client.ui;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityMainBinding;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Foreground;
import com.nojom.client.util.InAppMessageClick;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

public class MainActivity extends TabActivity {

    private MainActivityVM mainActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String language = Preferences.readString(this, Constants.SELECTED_LANGUAGE, "en");
        if (language.equals("ar"))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        super.onCreate(savedInstanceState);

        if (Foreground.get().isForeground()) {
            Utils.checkForMaintenance(this);
        }
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityVM = new MainActivityVM(Task24Application.getInstance(), mainBinding, this);

        InAppMessageClick inAppMessageClick = new InAppMessageClick();
        FirebaseInAppMessaging.getInstance().addClickListener(inAppMessageClick);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mainActivityVM.checkForIntentData(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainActivityVM.onResumeMethod();
    }


    public void redirectActivity(Class<?> activityClass) {
        mainActivityVM.redirectActivity(activityClass);
    }

    public void gotoMainActivity(int screen) {
        mainActivityVM.gotoMainActivity(screen);
    }

    public void gotoMainActivity(int screen, boolean anim) {
        mainActivityVM.gotoMainActivity(screen, anim);
    }

    public void setHomeTab() {
        mainActivityVM.setHomeTab();
    }

    public void setSettingTab() {
        mainActivityVM.setSettingTab();
    }

}
