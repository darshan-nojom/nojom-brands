package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_ADD_CONTRACT;
import static com.nojom.client.util.Constants.MIN_PROJECT_AMOUNT;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityHireBinding;
import com.nojom.client.model.Proposals;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

class HireActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private final ActivityHireBinding binding;
    private final BaseActivity activity;
    private Proposals.Data proposalData;
    private String filePath;

    HireActivityVM(Application application, ActivityHireBinding hireBinding, BaseActivity hireActivity) {
        super(application);
        binding = hireBinding;
        activity = hireActivity;
        initData();
    }

    private void initData() {
        binding.tvHiw.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvConfirmHire.setOnClickListener(this);
        Utils.trackAppsFlayerEvent(activity, "Hire_Agent_Screen");

        binding.tvCurrency.setText(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar) : activity.getString(R.string.dollar));
        binding.tvCurrencyRec.setText(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar) : activity.getString(R.string.dollar));

        if (activity.getIntent() != null) {
            proposalData = (Proposals.Data) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
            filePath = activity.getIntent().getStringExtra(Constants.USER_IMG_PATH);
        }

        if (proposalData != null) {
            activity.setImage(binding.imgProfile, TextUtils.isEmpty(proposalData.img) ? "" : filePath + proposalData.img, 0, 0);
            if (!TextUtils.isEmpty(proposalData.lastName) && !proposalData.lastName.equals("null")) {
                binding.tvUserName.setText(proposalData.firstName + " " + proposalData.lastName);
            } else {
                binding.tvUserName.setText(proposalData.firstName);
            }

//            if (proposalData.jobPayType.id == 1 || proposalData.jobPayType.id == 5) {
//                binding.tvPriceType.setText(" /Project");
//            } else {
//                binding.tvPriceType.setText(" /hr");
//            }
            double percentage = (proposalData.amount * proposalData.depositCharges) / 100;
            double total = 0;
            total = proposalData.amount + percentage;
            if (activity.getCurrency().equals("SAR")) {
//                binding.tvBidAmountFee.setText(String.format("%s %s %s", activity.getString(R.string._10_service_fee), percentage, activity.getString(R.string.sar)));
                binding.tvBidAmountFee.setText(String.format(activity.getString(R.string.service_fee_s_s_d), "" + percentage, activity.getString(R.string.sar), proposalData.depositCharges));
            } else {
                binding.tvBidAmountFee.setText(String.format("%s %s%s", activity.getString(R.string._10_service_fee), activity.getString(R.string.dollar), percentage));
            }
            binding.etAmount.setText(Utils.decimalFormat(String.valueOf(total)));
            binding.tvBidPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(total)) + " " + activity.getString(R.string.sar) : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(total)));
            binding.etRecAmount.setText(String.format("%s", Utils.get2DecimalPlaces(proposalData.amount)));
        }

        binding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activity.isEmpty(s.toString())) {
                    binding.etRecAmount.setText("");
                } else {
//                    double percentage = (Double.parseDouble(getAmount()) * 10) / 100;
                    double total = 0;
//                    total = Double.parseDouble(getAmount()) - percentage;

                    double commission = (Double.parseDouble(getAmount()) / (100 + 10)) * 10;
                    total = (Double.parseDouble(getAmount()) - commission);

                    if (activity.getCurrency().equals("SAR")) {
//                        binding.tvBidAmountFee.setText(String.format("%s %s %s", activity.getString(R.string._10_service_fee), Utils.get2DecimalPlaces(commission), activity.getString(R.string.sar)));
                        binding.tvBidAmountFee.setText(String.format(activity.getString(R.string.service_fee_s_s_d), Utils.get2DecimalPlaces(commission), activity.getString(R.string.sar), proposalData.depositCharges));
                    } else {
                        binding.tvBidAmountFee.setText(String.format("%s %s%s", activity.getString(R.string._10_service_fee), activity.getString(R.string.dollar), Utils.get2DecimalPlaces(commission)));
                    }

                    binding.etRecAmount.setText(String.format("%s", Utils.get2DecimalPlaces(total)));
                    binding.tvBidPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(getAmount()) + " " + activity.getString(R.string.sar) : activity.getString(R.string.dollar) + Utils.decimalFormat(getAmount()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etAmount.setSelection(binding.etAmount.getText().length());
            }
        });

        binding.tvHiw.setPaintFlags(binding.tvHiw.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private double getCharges() {
        double bidCharges = 0;
        try {
            double bidDollarCharges = proposalData.bidDollarCharges; //getBidDollarCharges();
            double bidPercFee = proposalData.bidPercentCharges;//getBidPercCharges();
            double percentage = (Double.parseDouble(getAmount()) * bidPercFee) / 100;
            bidCharges = Math.max(percentage, bidDollarCharges);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bidCharges;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hiw:
                activity.redirectActivity(HowItWorksActivity.class);
                break;
            case R.id.tv_cancel:
                activity.onBackPressed();
                break;
            case R.id.tv_confirm_hire:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        if (activity.isEmpty(getSendAmount())) {
            activity.validationError(activity.getString(R.string.please_enter_bid_amount));
            return;
        }
        if (proposalData.jobPayType != null && proposalData.jobPayType.id != 5) {
            if (Double.parseDouble(getSendAmount()) < MIN_PROJECT_AMOUNT) {
                if (activity.getCurrency().equals("SAR")) {
                    activity.validationError(activity.getString(R.string.minimum_bid_amount_should_be) + " " + MIN_PROJECT_AMOUNT + " " + activity.getString(R.string.sar));
                } else {
                    activity.validationError(activity.getString(R.string.minimum_bid_amount_should_be) + " " + activity.getString(R.string.dollar) + MIN_PROJECT_AMOUNT);
                }

                return;
            }
        }
        showConfirmation();
    }

    private String getAmount() {
        try {
            if (binding.etAmount.getText().toString().trim().isEmpty()) {
                return "0";
            } else {
                if (activity.getCurrency().equals("SAR")) {
                    return Utils.priceWithoutSAR(activity, binding.etAmount.getText().toString().replaceAll(",", "")
                            .replaceAll(" ", ""));
                } else {
                    return Utils.priceWithout$(binding.etAmount.getText().toString().replaceAll(",", "").replaceAll(" ", ""));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    private String getSendAmount() {
        try {
            if (binding.etRecAmount.getText().toString().trim().isEmpty()) {
                return "0";
            } else {
                if (activity.getCurrency().equals("SAR")) {
                    return Utils.priceWithoutSAR(activity, binding.etRecAmount.getText().toString().replaceAll(",", "")
                            .replaceAll(" ", ""));
                } else {
                    return Utils.priceWithout$(binding.etRecAmount.getText().toString().replaceAll(",", "").replaceAll(" ", ""));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    private void getHire() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;
        binding.tvConfirmHire.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", proposalData.jobPostId + "");
        map.put("fixed_price", getSendAmount());
        map.put("job_post_bid_id", proposalData.id + "");
        map.put("pay_type_id", "1");
        map.put("currency", proposalData.currency);
        map.put("bid_charges", "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_ADD_CONTRACT, true, map);

    }

    private void showConfirmation() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(String.format(Locale.US, activity.getString(R.string.are_you_sure_want_to_hire) + " %s?", proposalData.firstName));

        tvCancel.setText(activity.getString(R.string.no));
        tvChatnow.setText(activity.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            getHire();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        binding.tvConfirmHire.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        activity.gotoMainActivity(Constants.TAB_JOB_LIST);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        binding.tvConfirmHire.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }
}
