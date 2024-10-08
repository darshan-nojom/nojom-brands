package com.nojom.client.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivitySelectAccountBinding;

public class SelectAccountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin()) {
            redirectActivity(MainActivity.class);
            finish();
            return;
        }

        ActivitySelectAccountBinding selectAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_account);
        new SelectAccountActivityVM(Task24Application.getInstance(), selectAccountBinding, this);
    }
}
