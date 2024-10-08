package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityVerifyPaymentBinding;
import com.nojom.client.ui.BaseActivity;

public class VerifyPaymentActivity extends BaseActivity {

    private VerifyPaymentActivityVM verifyPaymentActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVerifyPaymentBinding verifyPaymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_payment);
        verifyPaymentActivityVM = new VerifyPaymentActivityVM(Task24Application.getInstance(), verifyPaymentBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyPaymentActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
