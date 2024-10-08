package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivitySelectFreelancerBinding;
import com.nojom.client.ui.BaseActivity;

public class SelectFreelancerActivity extends BaseActivity {

    private SelectFreelancerActivityVM selectFreelancerActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectFreelancerBinding selectFreelancerBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_freelancer);
        selectFreelancerActivityVM = new SelectFreelancerActivityVM(Task24Application.getInstance(), selectFreelancerBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectFreelancerActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectFreelancerActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void unSelectAll() {
        selectFreelancerActivityVM.unSelectAll();
    }
}
