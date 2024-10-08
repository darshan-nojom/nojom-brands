package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityJobPostTitleBinding;
import com.nojom.client.databinding.ActivityOfferTitleBinding;
import com.nojom.client.ui.BaseActivity;

public class HirePostJobTitleActivity extends BaseActivity {
    private HirePostJobTitleActivityVM postJobActivityNewVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOfferTitleBinding jobPostTitleBinding = DataBindingUtil.setContentView(this, R.layout.activity_offer_title);
        postJobActivityNewVM = new HirePostJobTitleActivityVM(Task24Application.getInstance(), jobPostTitleBinding, this);
        postJobActivityNewVM.initData();

        jobPostTitleBinding.toolbar.imgBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        postJobActivityNewVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
