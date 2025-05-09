package com.nojom.client.ui.policy;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityNewPolicyBinding;
import com.nojom.client.ui.BaseActivity;

public class NewPolicyActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewPolicyBinding newPolicyBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_policy);
        new NewPolicyActivityVM(Task24Application.getInstance(), newPolicyBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }

}
