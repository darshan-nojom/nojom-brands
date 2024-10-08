package com.nojom.client.ui.auth;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityUpdatePasswordBinding;
import com.nojom.client.ui.BaseActivity;

public class UpdatePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUpdatePasswordBinding updatePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_password);
        new UpdatePasswordActivityVM(Task24Application.getInstance(), updatePasswordBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
