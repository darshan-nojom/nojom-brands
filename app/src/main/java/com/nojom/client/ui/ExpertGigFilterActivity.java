package com.nojom.client.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityExpertGigFilterBinding;

public class ExpertGigFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpertGigFilterBinding expertGigFilterBinding = DataBindingUtil.setContentView(this, R.layout.activity_expert_gig_filter);
        new ExpertGigFilterActivityVM(Task24Application.getInstance(), expertGigFilterBinding, this);
    }

}
