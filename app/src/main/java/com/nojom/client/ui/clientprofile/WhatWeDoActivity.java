package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityWhatWeDoBinding;
import com.nojom.client.ui.BaseActivity;

public class WhatWeDoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWhatWeDoBinding whatWeDoBinding = DataBindingUtil.setContentView(this, R.layout.activity_what_we_do);
        new WhatWeDoActivityVM(Task24Application.getInstance(), whatWeDoBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
