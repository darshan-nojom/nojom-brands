package com.nojom.client.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityMaintainanceBinding;

public class MaintenanceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMaintainanceBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_maintainance);
        new MaintenanceActivityVM(Task24Application.getInstance(), activityMainBinding, this);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
