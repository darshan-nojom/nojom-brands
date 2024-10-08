package com.nojom.client.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityGigDetailsBinding;
import com.nojom.client.ui.BaseActivity;

public class GigDetailActivity extends BaseActivity {
    GigDetailActivityVM gigDetailActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGigDetailsBinding activityGigDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_gig_details);
        gigDetailActivityVM = new GigDetailActivityVM(Task24Application.getInstance(),activityGigDetailsBinding,this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}