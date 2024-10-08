package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityHowItWorksBinding;
import com.nojom.client.ui.BaseActivity;

public class HowItWorksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHowItWorksBinding howItWorksBinding = DataBindingUtil.setContentView(this, R.layout.activity_how_it_works);
        new HowItWorksActivityVM(Task24Application.getInstance(), howItWorksBinding, this);
    }
}
