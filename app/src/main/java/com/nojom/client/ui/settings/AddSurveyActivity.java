package com.nojom.client.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityAddSurveyBinding;
import com.nojom.client.ui.BaseActivity;

public class AddSurveyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddSurveyBinding addSurveyBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_survey);
        new AddSurveyActivityVM(Task24Application.getInstance(), addSurveyBinding, this);
    }
}
