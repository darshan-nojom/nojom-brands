package com.nojom.client.ui;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {

    private SplashActivityVM splashActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        ActivitySplashBinding bin = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        splashActivityVM = new SplashActivityVM(Task24Application.getInstance(), this);
        if (getLanguage().equals("ar")) {
            bin.imgText.setImageResource(R.drawable.ic_logo_ar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashActivityVM.onResumeMethod();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashActivityVM.onPauseMethod();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
