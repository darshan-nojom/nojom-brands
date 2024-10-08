package com.nojom.client.ui.balance;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityDepositBinding;
import com.nojom.client.ui.BaseActivity;

public class DepositActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDepositBinding depositBinding = DataBindingUtil.setContentView(this, R.layout.activity_deposit);
        new DepositActivityVM(Task24Application.getInstance(), depositBinding, this);
    }
}
