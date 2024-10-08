package com.nojom.client.ui.workprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityPhoneVerifyBinding;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import static com.nojom.client.ui.workprofile.VerifyPhoneNumberActivityVM.KEY_VERIFY_IN_PROGRESS;

public class VerifyPhoneNumberActivity extends BaseActivity {

    private VerifyPhoneNumberActivityVM verifyPhoneNumberActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPhoneVerifyBinding phoneVerifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_phone_verify);
        verifyPhoneNumberActivityVM = new VerifyPhoneNumberActivityVM(Task24Application.getInstance(), phoneVerifyBinding, this);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        verifyPhoneNumberActivityVM.onStartMethod();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, verifyPhoneNumberActivityVM.mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        verifyPhoneNumberActivityVM.mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }


}
