package com.nojom.client.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityExpertFilterBinding;

public class ExpertFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpertFilterBinding expertFilterBinding = DataBindingUtil.setContentView(this, R.layout.activity_expert_filter);
        new ExpertFilterActivityVM(Task24Application.getInstance(), expertFilterBinding, this);
    }
}
