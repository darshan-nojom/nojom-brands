package com.nojom.client.ui.balance;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityWithdrawMoneyBinding;
import com.nojom.client.ui.BaseActivity;

public class WithdrawMoneyActivity extends BaseActivity {

    private WithdrawMoneyActivityVM withdrawMoneyActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWithdrawMoneyBinding withdrawMoneyBinding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw_money);
        withdrawMoneyActivityVM = new WithdrawMoneyActivityVM(Task24Application.getInstance(), withdrawMoneyBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        withdrawMoneyActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
