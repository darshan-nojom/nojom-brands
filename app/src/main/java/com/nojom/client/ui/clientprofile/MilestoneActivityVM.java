package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityMilestoneBinding;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.PaymentActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

class MilestoneActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ProjectByID projectData;
    private final ActivityMilestoneBinding binding;
    private final BaseActivity activity;

    MilestoneActivityVM(Application application, ActivityMilestoneBinding milestoneBinding, BaseActivity milestoneActivity) {
        super(application);
        binding = milestoneBinding;
        activity = milestoneActivity;
        initData();
    }

    @SuppressLint("StringFormatInvalid")
    private void initData() {
        binding.tvDeposit.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        if (activity.getIntent() != null) {
            projectData = (ProjectByID) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        if (projectData != null) {
            if (projectData.agentDetails != null) {
                activity.setImage(binding.milestone.imgProfile, TextUtils.isEmpty(projectData.agentDetails.photo) ? "" : activity.getUserData().filePath.pathProfilePicAgent + projectData.agentDetails.photo, 0, 0);

                if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                    binding.milestone.tvUserName.setText(activity.getString(R.string.you_ve_hired, projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName));
                } else {
                    binding.milestone.tvUserName.setText(activity.getString(R.string.you_ve_hired, projectData.agentDetails.firstName));
                }

//                binding.milestone.tvBidPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(projectData.fixedPrice)) + " "+activity.getString(R.string.sar)
//                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(projectData.fixedPrice)));
//                binding.tvPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(projectData.fixedPrice)) + " "+activity.getString(R.string.sar)
//                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(projectData.fixedPrice)));
//                if (projectData.jobPayType != null) {
//                    if (projectData.jobPayType.id == 1 || projectData.jobPayType.id == 5) {//fixed
//                        binding.milestone.tvPriceType.setText(" " + activity.getString(R.string.project_1));
//                    } else if (projectData.jobPayType.id == 2) {//hourly
//                        binding.milestone.tvPriceType.setText(" " + activity.getString(R.string.project_1));
//                    }
//                } else {
//                    binding.milestone.tvPriceType.setText(activity.getString(R.string.Free));
//                }
                binding.milestone.tvDepositAmount.setText(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar__0_00) : activity.getString(R.string.dollar__0_00));
                binding.milestone.tvReleaseAmount.setText(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar__0_00) : activity.getString(R.string.dollar__0_00));
                if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                    binding.milestone.tvSatisfied.setText(activity.getString(R.string.releasing_deposit_text, projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName));
                } else {
                    binding.milestone.tvSatisfied.setText(activity.getString(R.string.releasing_deposit_text, projectData.agentDetails.firstName));
                }

                binding.milestone.txtDepAmnt.setText(String.format(activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : "$%s", projectData.fixedPrice));
                setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.milestone.txtTotalAmnt, binding.milestone.txtServAmnt);
                setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvPrice, null);
            }
        }

        binding.milestone.tvTnc.setText(Utils.getColorString(activity, activity.getString(R.string.refund_terms_and_conditions),
                activity.getString(R.string.terms_and_conditions), R.color.colorPrimaryAlpha));

        ClickableSpan reffCodeClick = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                activity.redirectUsingCustomTab(Constants.TERMS_USE);
            }
        };
        binding.milestone.tvTnc.setText(Utils.getColorString(activity, activity.getString(R.string.refund_terms_and_conditions),
                activity.getString(R.string.terms_and_conditions), R.color.colorPrimaryAlpha));
        Utils.makeLinks(binding.milestone.tvTnc, new String[]{activity.getString(R.string.terms_and_conditions)}, new ClickableSpan[]{reffCodeClick});
    }

    private void setTotal(Double amount, Integer charge, TextView txtView, TextView txtService) {
        if (amount == null) {
            amount = 0.0;
        }
        double val = amount * charge / 100;
        if (txtService != null) {
            txtService.setText(String.format(activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : "$%s", Utils.numberFormat(val, 2)));
        }
        double totalAmnt = val + amount;
        txtView.setText(String.format(activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : "$%s", Utils.numberFormat(totalAmnt, 2)));
    }

    private void getPaymentMethod() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        binding.tvDeposit.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_deposit:
                getPaymentMethod();
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        PaymentMethods paymentMethod = PaymentMethods.gePaymentMethodInfo(responseBody);
        binding.tvDeposit.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        Preferences.setPaymentMethod(activity, paymentMethod);
//        Intent i = new Intent(activity, DepositFundsActivity.class);
        Intent i = new Intent(activity, PaymentActivity.class);
        i.putExtra(Constants.IS_FROM_GIG, false);
        i.putExtra(Constants.USER_DATA, projectData);
        activity.startActivity(i);
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
    }
}
