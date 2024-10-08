package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityHireBinding;
import com.nojom.client.ui.BaseActivity;

public class HireActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHireBinding hireBinding = DataBindingUtil.setContentView(this, R.layout.activity_hire);
        new HireActivityVM(Task24Application.getInstance(), hireBinding, this);
    }
}
