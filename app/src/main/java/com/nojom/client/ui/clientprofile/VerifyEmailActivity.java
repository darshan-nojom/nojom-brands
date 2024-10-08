package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityEmailVerifyBinding;
import com.nojom.client.ui.BaseActivity;

public class VerifyEmailActivity extends BaseActivity {
    private VerifyEmailActivityVM verifyEmailActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmailVerifyBinding emailVerifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_verify);
        verifyEmailActivityVM = new VerifyEmailActivityVM(Task24Application.getInstance(), emailVerifyBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyEmailActivityVM.onResumeMethod();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
