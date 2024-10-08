package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDeadlineBinding;
import com.nojom.client.databinding.FragmentDescribeBinding;
import com.nojom.client.ui.BaseActivity;

public class HireDeadlineActivity extends BaseActivity {
    HireDeadlineActivityVM hireDescribeActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentDeadlineBinding addDescribeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_deadline);
        hireDescribeActivityVM = new HireDeadlineActivityVM(Task24Application.getInstance(), addDescribeBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hireDescribeActivityVM.onResumeMethod();
    }
}
