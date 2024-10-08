package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityClientSettingBinding;
import com.nojom.client.ui.BaseActivity;

public class ClientSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClientSettingBinding settingBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_setting);
        new ClientSettingActivityVM(Task24Application.getInstance(), settingBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
