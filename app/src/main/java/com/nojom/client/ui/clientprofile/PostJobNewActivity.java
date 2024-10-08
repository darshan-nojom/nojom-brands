package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityPostJobNewBinding;
import com.nojom.client.ui.BaseActivity;

public class PostJobNewActivity extends BaseActivity {
    private PostJobNewActivityVM postJobActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostJobNewBinding postJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_job_new);
        postJobActivityVM = new PostJobNewActivityVM(Task24Application.getInstance(), postJobBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        postJobActivityVM.onActivityResult(requestCode, resultCode, data);
    }
}
