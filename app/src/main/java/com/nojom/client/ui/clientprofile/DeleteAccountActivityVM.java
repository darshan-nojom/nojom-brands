package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.app.Dialog;
import android.button.CustomButton;
import android.edittext.CustomEditText;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityAccountDeleteBinding;
import com.nojom.client.ui.BaseActivity;

import java.util.HashMap;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.API_GET_DELETE_PROFILE;

class DeleteAccountActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private BaseActivity activity;
    private ActivityAccountDeleteBinding binding;
    private CircularProgressBar progressBar;
    private CustomButton btnSubmit;
    private Dialog dialogDeleteAccount;

    DeleteAccountActivityVM(Application application, ActivityAccountDeleteBinding deleteBinding, BaseActivity clientSettingActivity) {
        super(application);
        binding = deleteBinding;
        activity = clientSettingActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.rlDeleteAccount.setOnClickListener(this);

        binding.toolbar.tvTitle.setText(activity.getString(R.string.data_privacy));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.rl_delete_account:
                deleteAccountDialog();
                break;
        }
    }

    private void deleteAccountDialog() {
        dialogDeleteAccount = new Dialog(activity);
        dialogDeleteAccount.setTitle(null);
        dialogDeleteAccount.setContentView(R.layout.dialog_delete_account);
        dialogDeleteAccount.setCancelable(true);

        btnSubmit = dialogDeleteAccount.findViewById(R.id.btnSubmit);
        CustomButton btnCancel = dialogDeleteAccount.findViewById(R.id.btnCancel);
        CustomEditText edtReason = dialogDeleteAccount.findViewById(R.id.edtReason);
        progressBar = dialogDeleteAccount.findViewById(R.id.progressBar);

        RadioGroup radioGroup = dialogDeleteAccount.findViewById(R.id.radioGroup);
        RadioButton rbBadExperience = dialogDeleteAccount.findViewById(R.id.rbBadExperience);
        RadioButton rbAlternative = dialogDeleteAccount.findViewById(R.id.rbAlternative);
        RadioButton rbNoReason = dialogDeleteAccount.findViewById(R.id.rbNoReason);
        RadioButton rbOther = dialogDeleteAccount.findViewById(R.id.rbOther);
        radioGroup.clearCheck();

        rbBadExperience.setText(activity.getString(R.string.i_have_a_bad_experience_using_the_app));
        rbAlternative.setText(activity.getString(R.string.i_found_a_better_alternative));
        rbNoReason.setText(activity.getString(R.string.i_just_want_to_delete_my_account_for_no_reason_at_all));
        rbOther.setText(activity.getString(R.string.other));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                if (rb.getText().equals("Other")) {
                    edtReason.setVisibility(VISIBLE);
                } else {
                    edtReason.setVisibility(GONE);
                }
            }
        });
        btnSubmit.setOnClickListener(v -> {
            RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedReason = "";
            if (rb != null && !TextUtils.isEmpty(rb.getText())) {
                if (rb.getText().equals(activity.getString(R.string.other))) {
                    selectedReason = edtReason.getText().toString();
                } else {
                    selectedReason = rb.getText().toString();
                }
            }

            if (TextUtils.isEmpty(selectedReason)) {
                activity.toastMessage(activity.getString(R.string.please_select_reason));
                return;
            }

            deleteAccount(selectedReason);
        });

        btnCancel.setOnClickListener(v -> dialogDeleteAccount.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialogDeleteAccount.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        dialogDeleteAccount.show();
        dialogDeleteAccount.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDeleteAccount.getWindow().setAttributes(lp);
    }

    private void deleteAccount(String selectedReason) {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(GONE);

        HashMap<String, String> map = new HashMap<>();
        map.put("reason", selectedReason);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_DELETE_PROFILE, true, map);
    }


    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        progressBar.setVisibility(GONE);
        btnSubmit.setVisibility(VISIBLE);
        activity.toastMessage(message);

        if (dialogDeleteAccount != null) {
            dialogDeleteAccount.dismiss();
            activity.onLogout(activity);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        progressBar.setVisibility(GONE);
        btnSubmit.setVisibility(VISIBLE);
        activity.toastMessage(message);
    }
}
