package com.nojom.client.ui.balance;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityDepositBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.Locale;

public class DepositActivityVM extends AndroidViewModel implements View.OnClickListener, Constants {
    private double availableBalance;
    private final ActivityDepositBinding binding;
    private final BaseActivity activity;

    DepositActivityVM(Application application, ActivityDepositBinding depositBinding, BaseActivity depositActivity) {
        super(application);
        binding = depositBinding;
        activity = depositActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

        if (activity.getIntent() != null) {
            availableBalance = activity.getIntent().getDoubleExtra(AVAILABLE_BALANCE, 0);
        }

        try {
            binding.tvBalance.setText(activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(activity,Utils.currencyFormat(availableBalance)) : Utils.priceWith$(Utils.currencyFormat(availableBalance),activity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAmount() {
        return binding.etBalance.getText().toString();
    }

    private double getFormatAmount() {
        if (!TextUtils.isEmpty(getAmount()))
            return Double.parseDouble(getAmount());
        else
            return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.btn_next:
                if (getFormatAmount() < 5) {
                    binding.tvValidation.setText(activity.getCurrency().equals("SAR") ? activity.getString(R.string.minimum_amount_dol_five_sar) : activity.getString(R.string.minimum_amount_dol_five));
                } else if (getFormatAmount() > 10000) {
                    binding.tvValidation.setText(String.format(Locale.US, activity.getString(R.string.maximum_amount) + " %s", Utils.currencyFormat(10000)));
                } else {
                    binding.tvValidation.setText("");
                    Intent i = new Intent(activity, WithdrawMoneyActivity.class);
                    i.putExtra(AVAILABLE_BALANCE, availableBalance);
                    i.putExtra(WITHDRAW_AMOUNT, getFormatAmount());
                    activity.startActivity(i);
                }
                break;
        }
    }
}
