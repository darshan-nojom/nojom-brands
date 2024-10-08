package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityOfferSummaryBinding;
import com.nojom.client.databinding.ActivityOfferTitleBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.MyProfileActivity;

public class HireOfferSummaryActivity extends BaseActivity {
    private HireOfferSummaryActivityVM postJobActivityNewVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOfferSummaryBinding jobPostTitleBinding = DataBindingUtil.setContentView(this, R.layout.activity_offer_summary);
        postJobActivityNewVM = new HireOfferSummaryActivityVM(Task24Application.getInstance(), jobPostTitleBinding, this);

        jobPostTitleBinding.btnLastStep.setOnClickListener(view -> {

            if (isLogin()) {
                if (getIsVerified() == 0) {
                    redirectActivity(MyProfileActivity.class);
                    finish();
                } else {
                    postJobActivityNewVM.postJobAPI();
                }
            } else {
                openLoginDialog();
            }
        });

        jobPostTitleBinding.header.imgBack.setOnClickListener(view -> finish());
    }
}
