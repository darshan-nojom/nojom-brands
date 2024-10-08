package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityAddDescribeBinding;
import com.nojom.client.ui.BaseActivity;

public class AddDescribeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddDescribeBinding addDescribeBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_describe);
        new AddDescribeActivityVM(Task24Application.getInstance(), addDescribeBinding, this);
    }
}
