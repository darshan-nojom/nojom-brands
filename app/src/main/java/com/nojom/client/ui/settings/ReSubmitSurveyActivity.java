package com.nojom.client.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityAddSurveySubmitBinding;
import com.nojom.client.ui.BaseActivity;

public class ReSubmitSurveyActivity extends BaseActivity {

    private ReSubmitSurveyActivityVM reSubmitSurveyActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddSurveySubmitBinding addSurveySubmitBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_survey_submit);
        reSubmitSurveyActivityVM = new ReSubmitSurveyActivityVM(Task24Application.getInstance(), addSurveySubmitBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reSubmitSurveyActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (reSubmitSurveyActivityVM.isFileDeleted) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();

    }
}
