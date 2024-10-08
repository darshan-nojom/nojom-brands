package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDescribeBinding;
import com.nojom.client.databinding.FragmentSelectRateBinding;
import com.nojom.client.ui.BaseActivity;

public class HirePriceRateActivity extends BaseActivity {
    HirePriceRateActivityVM hireDescribeActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentSelectRateBinding addDescribeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_select_rate);
        hireDescribeActivityVM = new HirePriceRateActivityVM(Task24Application.getInstance(), addDescribeBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hireDescribeActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hireDescribeActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
