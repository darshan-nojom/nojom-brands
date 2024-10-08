package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityUsernameBinding;
import com.nojom.client.ui.BaseActivity;

public class UsernameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUsernameBinding usernameBinding = DataBindingUtil.setContentView(this, R.layout.activity_username);
        new UsernameActivityVM(Task24Application.getInstance(), usernameBinding, this);
    }
}
