package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityUsernameBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import java.util.HashMap;

import static com.nojom.client.util.Constants.API_UPDATE_USERNAME;

class UsernameActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityUsernameBinding binding;
    private BaseActivity activity;
    private Profile profileData;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (binding.toolbar.tvSave.getVisibility() == View.GONE) {
                binding.toolbar.tvSave.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    UsernameActivityVM(Application application, ActivityUsernameBinding usernameBinding, BaseActivity usernameActivity) {
        super(application);
        binding = usernameBinding;
        activity = usernameActivity;
        initData();
    }

    private void initData() {
        profileData = Preferences.getProfileData(activity);
        binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.username));
        binding.toolbar.tvSave.setOnClickListener(this);
        binding.tvUsername.setText(profileData.username);
        binding.toolbar.imgBack.setOnClickListener(v -> activity.onBackPressed());

        addTextChangeEvent(binding.tvUsername);
    }

    private void addTextChangeEvent(EditText... editTexts) {
        for (EditText edittext : editTexts) {
            edittext.addTextChangedListener(textWatcher);
        }
    }

    private void updateUserName() {
        if (!activity.isNetworkConnected())
            return;

        if (TextUtils.isEmpty(getUserName())) {
            activity.validationError(activity.getString(R.string.enter_username));
            return;
        }

        if (activity.isValidUserName(getUserName())) {
            activity.validationError(activity.getString(R.string.enter_valid_username));
            return;
        }

        binding.toolbar.progressBar.setVisibility(View.VISIBLE);
        binding.toolbar.tvSave.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("username", getUserName());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_USERNAME, true, map);
    }

    public String getUserName() {
        return binding.tvUsername.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                updateUserName();
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        binding.toolbar.progressBar.setVisibility(View.GONE);
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        activity.toastMessage(message);
        profileData.username = getUserName();
        Preferences.setProfileData(activity, profileData);
        activity.onBackPressed();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.toolbar.progressBar.setVisibility(View.GONE);
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
    }
}
