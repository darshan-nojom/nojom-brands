package com.nojom.client.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityGigExtrasBinding;
import com.nojom.client.ui.BaseActivity;

public class GigExtrasActivity extends BaseActivity {
    GigExtrasActivityVM gigExtrasActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGigExtrasBinding activityGigExtrasBinding = DataBindingUtil.setContentView(this, R.layout.activity_gig_extras);
        gigExtrasActivityVM = new GigExtrasActivityVM(Task24Application.getInstance(), activityGigExtrasBinding, this);

    }
}