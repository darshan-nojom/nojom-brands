package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityProfileInfoBinding;
import com.nojom.client.ui.BaseActivity;

public class ProfileInfoActivity extends BaseActivity {

    private ProfileInfoActivityVM profileInfoActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileInfoBinding profileInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_info);
        profileInfoActivityVM = new ProfileInfoActivityVM(Task24Application.getInstance(), profileInfoBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileInfoActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        profileInfoActivityVM.onActivityResult(requestCode, resultCode, data);

    }

    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
