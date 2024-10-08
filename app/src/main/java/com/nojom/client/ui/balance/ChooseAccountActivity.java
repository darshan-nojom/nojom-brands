package com.nojom.client.ui.balance;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityChooseAccountBinding;
import com.nojom.client.ui.BaseActivity;

public class ChooseAccountActivity extends BaseActivity {

    private ChooseAccountActivityVM chooseAccountActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChooseAccountBinding chooseAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_account);
        chooseAccountActivityVM = new ChooseAccountActivityVM(Task24Application.getInstance(), chooseAccountBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chooseAccountActivityVM.onResumeMethod();
    }
}
