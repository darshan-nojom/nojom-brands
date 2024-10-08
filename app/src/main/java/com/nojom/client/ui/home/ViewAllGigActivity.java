package com.nojom.client.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityViewAllGigBinding;
import com.nojom.client.ui.BaseActivity;


public class ViewAllGigActivity extends BaseActivity {
    ViewAllGigActivityVM viewAllGigActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewAllGigBinding activityViewAllGigBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_all_gig);
        viewAllGigActivityVM = new ViewAllGigActivityVM(Task24Application.getInstance(), activityViewAllGigBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewAllGigActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}