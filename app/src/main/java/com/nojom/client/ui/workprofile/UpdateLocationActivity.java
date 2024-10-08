package com.nojom.client.ui.workprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityUpdateLocationBinding;
import com.nojom.client.ui.BaseActivity;

public class UpdateLocationActivity extends BaseActivity {

    private UpdateLocationActivityVM updateLocationActivityVM;
    private boolean isLocationUpdateCompulsory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUpdateLocationBinding updateLocationBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_location);
        if (getIntent() != null) {
            isLocationUpdateCompulsory = getIntent().getBooleanExtra("flag", false);
        }
        updateLocationActivityVM = new UpdateLocationActivityVM(Task24Application.getInstance(), updateLocationBinding, this);
        updateLocationActivityVM.setLocationUpdateCompulsory(isLocationUpdateCompulsory);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isLocationUpdateCompulsory) {
            finishAffinity();
        } else {
            finish();
            finishToRight();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateLocationActivityVM.onStopMethod();
    }
}
