package com.nojom.client.ui;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityMaintainanceBinding;
import com.nojom.client.util.Utils;

import io.intercom.android.sdk.Intercom;

class MaintenanceActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityMaintainanceBinding binding;
    private BaseActivity activity;

    MaintenanceActivityVM(Application application, ActivityMaintainanceBinding activityMainBinding, BaseActivity maintenanceActivity) {
        super(application);
        binding = activityMainBinding;
        activity = maintenanceActivity;

        binding.btnRetry.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                Utils.checkForMaintenanceRetry(activity);
                break;
            case R.id.btn_chat:
//                activity.openWhatsappChat();
                Intercom.client().displayMessageComposer();
                break;
        }
    }
}
