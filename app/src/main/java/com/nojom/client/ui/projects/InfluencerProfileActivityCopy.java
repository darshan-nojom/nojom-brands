package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityInfluencerProfileCopyBinding;
import com.nojom.client.databinding.ActivityProfileStarsBinding;
import com.nojom.client.ui.BaseActivity;

public class InfluencerProfileActivityCopy extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileStarsBinding profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_stars);
        new StarProfileActivityVM(Task24Application.getInstance(), profileBinding, this);
    }

}
