package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityServicesBinding;
import com.nojom.client.ui.BaseActivity;

public class ServicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityServicesBinding servicesBinding = DataBindingUtil.setContentView(this, R.layout.activity_services);
        new ServicesActivityVM(Task24Application.getInstance(), servicesBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }

}
