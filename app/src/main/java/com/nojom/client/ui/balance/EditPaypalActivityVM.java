package com.nojom.client.ui.balance;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityEditPaypalBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.HashMap;
import java.util.Objects;

import static com.nojom.client.util.Constants.API_DELETE_PAYMENT_ACCOUNT;
import static com.nojom.client.util.Constants.API_EDIT_PAYMENT_ACCOUNT;
import static com.nojom.client.util.Constants.API_SEND_VERIFICATION_LINK;

public class EditPaypalActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityEditPaypalBinding binding;
    private BaseActivity activity;
    private BraintreeCard.Data paymentData;
    private boolean isRedirectFromDeposite;
    private int currentPosition = 0;

    EditPaypalActivityVM(Application application, ActivityEditPaypalBinding paypalBinding, BaseActivity editPaypalActivity) {
        super(application);
        binding = paypalBinding;
        activity = editPaypalActivity;
        initData();
    }

    private void initData() {
        try {
            binding.imgBack.setOnClickListener(this);
            binding.tvSendEmail.setOnClickListener(this);
            binding.tvDeleteAccount.setOnClickListener(this);

            if (activity.getIntent() != null) {
                paymentData = (BraintreeCard.Data) activity.getIntent().getSerializableExtra(Constants.ACCOUNT_DATA);
                isRedirectFromDeposite = activity.getIntent().getBooleanExtra("isFromDeposite", false);
            }

            if (paymentData == null) {
                activity.finish();
                return;
            }

            if (paymentData.paypal != null && paymentData.paypal.verified != null) {
                if (paymentData.paypal.verified.equals("0")) {
                    binding.tvStatus.setText(activity.getString(R.string.not_verified));
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_border_5));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.red_dark));
                    binding.llSendEmail.setVisibility(View.VISIBLE);
                } else {
                    binding.tvStatus.setText(activity.getString(R.string.verified));
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_border_5));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.greendark));
                }
            }

            if (paymentData.paypal != null && paymentData.paypal.account != null) {
                binding.tvDeleteAccount.setText(activity.getString(R.string.delete_this_paypal));
                binding.tvPaypalEmail.setText(paymentData.paypal.account);

                if (!TextUtils.isEmpty(paymentData.paypal.isPrimary)) {
                    currentPosition = Integer.parseInt(paymentData.paypal.isPrimary);
                    binding.segmentGroup.setPosition(!TextUtils.isEmpty(paymentData.paypal.isPrimary) ? paymentData.paypal.isPrimary.equals("1") ? 1 : 0 : 0, true);
                }
            }

            binding.segmentGroup.setOnPositionChangedListener(this::editAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_send_email:
                verifyAccount();
                break;
            case R.id.tv_delete_account:
                showDeleteDialog();
                break;
        }
    }


    private void verifyAccount() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("payment_account_id", paymentData.paypal.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEND_VERIFICATION_LINK, true, map);
    }

    private void editAccount(int primary) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("payment_account_id", paymentData.paypal.id + "");
        map.put("is_primary", primary + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_EDIT_PAYMENT_ACCOUNT, true, map);
    }

    private void deleteAccount() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("payment_account_id", paymentData.paypal.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_DELETE_PAYMENT_ACCOUNT, true, map);
    }

    private void showDeleteDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(Utils.fromHtml(activity.getString(R.string.delete_account_text)));

        tvCancel.setText(activity.getString(R.string.no));
        tvChatnow.setText(activity.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            deleteAccount();
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
        if (url.equalsIgnoreCase(API_DELETE_PAYMENT_ACCOUNT)) {
            if (!isRedirectFromDeposite) {
                Intent i = new Intent(activity, BalanceActivity.class);
                i.putExtra(Constants.TAB_BALANCE, Constants.BALANCE_ACCOUNT);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);
            } else {
                activity.finish();
            }
            activity.finishToRight();
        } else if (url.equalsIgnoreCase(API_SEND_VERIFICATION_LINK)) {
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_EDIT_PAYMENT_ACCOUNT)) {
            if (currentPosition != binding.segmentGroup.getPosition()) {
                currentPosition = binding.segmentGroup.getPosition();
                activity.toastMessage(message);
                activity.finish();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
