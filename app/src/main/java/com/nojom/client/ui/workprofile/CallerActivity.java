package com.nojom.client.ui.workprofile;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityCallerBinding;
import com.nojom.client.ui.BaseActivity;

public class CallerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        View decorView = getWindow().getDecorView(); //set status background black
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); //set status text  light
        ActivityCallerBinding bin = DataBindingUtil.setContentView(this, R.layout.activity_caller);
        new CallerActivityVM(Task24Application.getInstance(), this);

        if (getLanguage().equals("ar")) {
            bin.imgLogo.setImageResource(R.drawable.ic_logo_ar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
