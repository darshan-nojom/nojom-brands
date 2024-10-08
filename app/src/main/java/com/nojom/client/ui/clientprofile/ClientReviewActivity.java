package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityClientReviewBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

public class ClientReviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClientReviewBinding clientReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_review);
        new ClientReviewActivityVM(Task24Application.getInstance(), clientReviewBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoMainActivity(Constants.TAB_JOB_LIST);
    }
}
