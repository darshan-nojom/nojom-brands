package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityEmailVerifyBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_SEND_EMAIL_VERIFICATION;

public class VerifyEmailActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityEmailVerifyBinding binding;
    private BaseActivity activity;
    private boolean isVerifyEmail = false;

    VerifyEmailActivityVM(Application application, ActivityEmailVerifyBinding emailVerifyBinding, BaseActivity verifyEmailActivity) {
        super(application);
        binding = emailVerifyBinding;
        activity = verifyEmailActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);

        Profile profileData = Preferences.getProfileData(activity);
        binding.etEmail.setText(profileData.email);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_submit:
                if (binding.tvSubmit.getText().toString().equals(activity.getString(R.string.send_verification_email))) {
                    if (isValid()) {
                        verifyEmail(binding.etEmail.getText().toString());
                    }
                }
                break;
        }

    }


    public String getEmail() {
        return binding.etEmail.getText().toString().trim();
    }

    void onResumeMethod() {
        if (isVerifyEmail) {
            isVerifyEmail = false;
            activity.setResult(RESULT_OK);
            activity.finish();
        }
    }

    private void verifyEmail(String email) {
        if (!activity.isNetworkConnected())
            return;


        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("platform", "4");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEND_EMAIL_VERIFICATION, true, map);
    }


    private boolean isValid() {
        if (!activity.isValidEmail(getEmail())) {
            activity.validationError(activity.getString(R.string.enter_valid_email));
            return false;
        }
        return true;
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_SEND_EMAIL_VERIFICATION)) {
            activity.toastMessage(message);
            isVerifyEmail = true;
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
