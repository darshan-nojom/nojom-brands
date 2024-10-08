package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityMilestoneBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

public class MilestoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMilestoneBinding milestoneBinding = DataBindingUtil.setContentView(this, R.layout.activity_milestone);
        new MilestoneActivityVM(Task24Application.getInstance(), milestoneBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //gotoMainActivity(Constants.TAB_JOB_LIST);
    }
}
