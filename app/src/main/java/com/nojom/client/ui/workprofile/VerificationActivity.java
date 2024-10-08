package com.nojom.client.ui.workprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityVerificationBinding;
import com.nojom.client.ui.BaseActivity;

public class VerificationActivity extends BaseActivity {

    private VerificationActivityVM verificationActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVerificationBinding verificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_verification);
        verificationActivityVM = new VerificationActivityVM(Task24Application.getInstance(), verificationBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificationActivityVM.onResumeMethod();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verificationActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
