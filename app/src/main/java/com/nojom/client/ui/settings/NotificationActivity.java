package com.nojom.client.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityNotificationBinding;
import com.nojom.client.ui.BaseActivity;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotificationBinding notificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        new NotificationActivityVM(Task24Application.getInstance(), notificationBinding, this);
    }
}
