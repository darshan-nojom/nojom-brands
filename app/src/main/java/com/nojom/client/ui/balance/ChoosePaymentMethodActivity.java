package com.nojom.client.ui.balance;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityChoosePaymentMethodBinding;
import com.nojom.client.ui.BaseActivity;

public class ChoosePaymentMethodActivity extends BaseActivity {
    private ChoosePaymentMethodActivityVM choosePaymentMethodActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChoosePaymentMethodBinding paymentMethodBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_payment_method);
        choosePaymentMethodActivityVM = new ChoosePaymentMethodActivityVM(Task24Application.getInstance(), paymentMethodBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePaymentMethodActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
