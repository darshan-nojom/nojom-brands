package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentEnterRateBinding;
import com.nojom.client.ui.BaseActivity;

public class HireEnterRateActivity extends BaseActivity {
    HireEnterRateActivityVM hireDescribeActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentEnterRateBinding addDescribeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_enter_rate);
        hireDescribeActivityVM = new HireEnterRateActivityVM(Task24Application.getInstance(), addDescribeBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hireDescribeActivityVM.onResumeMethod();
    }
}
