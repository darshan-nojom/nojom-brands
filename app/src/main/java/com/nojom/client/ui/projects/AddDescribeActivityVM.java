package com.nojom.client.ui.projects;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityAddDescribeBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.HashMap;

import static com.nojom.client.util.Constants.API_ADD_EDIT_CLIENT_JOB_DESCRIBE;

public class AddDescribeActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityAddDescribeBinding binding;
    private BaseActivity activity;
    private String describe = "";
    private int contractID = 0;

    AddDescribeActivityVM(Application application, ActivityAddDescribeBinding addDescribeBinding, BaseActivity addDescribeActivity) {
        super(application);
        binding = addDescribeBinding;
        activity = addDescribeActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        if (activity.getIntent().getStringExtra(Constants.ADD_DESCRIBE) != null) {
            describe = activity.getIntent().getStringExtra(Constants.ADD_DESCRIBE);
            binding.etDescribe.setText(describe);
        }
        contractID = activity.getIntent().getIntExtra(Constants.CONTRACT_ID, 0);
    }

    private String getDescribe() {
        return binding.etDescribe.getText().toString().trim();
    }

    private void addDescribe() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarSave.setVisibility(View.VISIBLE);
        activity.isClickableView = true;
        binding.btnSave.setVisibility(View.INVISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("contractID", contractID + "");
        map.put("description", getDescribe());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_ADD_EDIT_CLIENT_JOB_DESCRIBE, true, map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.btn_save:
                if (!activity.isEmpty(getDescribe())) {
                    addDescribe();
                } else {
                    activity.validationError(activity.getString(R.string.please_enter_describe));
                }
                break;
        }

    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        binding.progressBarSave.setVisibility(View.GONE);
        binding.btnSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        Intent intent = new Intent();
        intent.putExtra(Constants.ADD_DESCRIBE, getDescribe());
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.progressBarSave.setVisibility(View.GONE);
        binding.btnSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
    }
}
