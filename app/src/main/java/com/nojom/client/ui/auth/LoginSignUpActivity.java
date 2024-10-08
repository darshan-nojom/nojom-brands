package com.nojom.client.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityLoginSignUpBinding;
import com.nojom.client.ui.BaseActivity;

public class LoginSignUpActivity extends BaseActivity {

    private LoginSignUpActivityVM loginSignUpActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginSignUpBinding loginSignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_sign_up);
        loginSignUpActivityVM = new LoginSignUpActivityVM(Task24Application.getInstance(), loginSignUpBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginSignUpActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
