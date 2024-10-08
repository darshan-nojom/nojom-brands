package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityJobPostTitleBinding;
import com.nojom.client.ui.BaseActivity;

public class PostJobTitleActivity extends BaseActivity {
    private PostJobTitleActivityVM postJobActivityNewVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityJobPostTitleBinding jobPostTitleBinding = DataBindingUtil.setContentView(this, R.layout.activity_job_post_title);
        postJobActivityNewVM = new PostJobTitleActivityVM(Task24Application.getInstance(), jobPostTitleBinding, this);
        postJobActivityNewVM.initData();

        jobPostTitleBinding.btnPostJob.setOnClickListener(view -> {

            if (isLogin()) {
                if (getIsVerified() == 0) {
                    redirectActivity(MyProfileActivity.class);
                    finish();
                } else {
                    if (postJobActivityNewVM.isValid()) {
                        postJobActivityNewVM.postJobAPI();
                    }
                }
            } else {
                openLoginDialog();
            }
        });

        jobPostTitleBinding.toolbar.imgBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
