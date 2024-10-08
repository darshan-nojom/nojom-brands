package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityHowItWorksBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

class HowItWorksActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityHowItWorksBinding binding;
    private BaseActivity activity;

    HowItWorksActivityVM(Application application, ActivityHowItWorksBinding howItWorksBinding, BaseActivity howItWorksActivity) {
        super(application);
        binding = howItWorksBinding;
        activity = howItWorksActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvPostJob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
            case R.id.tv_cancel:
                activity.onBackPressed();
                break;
            case R.id.tv_post_job:
                activity.gotoMainActivity(Constants.TAB_POST_JOB);
                break;
        }
    }
}
