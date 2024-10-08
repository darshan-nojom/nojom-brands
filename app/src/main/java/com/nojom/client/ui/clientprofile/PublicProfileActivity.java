package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityPublicProfileBinding;
import com.nojom.client.ui.BaseActivity;

public class PublicProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPublicProfileBinding publicProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_public_profile);
        new PublicProfileActivityVM(Task24Application.getInstance(), publicProfileBinding, this);
    }

}
