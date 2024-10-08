package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDescribeBinding;
import com.nojom.client.ui.BaseActivity;

public class HireDescribeActivity extends BaseActivity {
    HireDescribeActivityVM hireDescribeActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentDescribeBinding addDescribeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_describe);
        hireDescribeActivityVM = new HireDescribeActivityVM(Task24Application.getInstance(), addDescribeBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hireDescribeActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hireDescribeActivityVM.onResumeMethod();
    }
}
