package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.MIN_PROJECT_AMOUNT;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentEnterRateBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.Locale;
import java.util.Objects;

class EnterRateFragmentVM extends AndroidViewModel implements View.OnClickListener {
    private final FragmentEnterRateBinding binding;
    private final BaseFragment fragment;
//    private boolean isFixedPrice;
    private boolean isEdit;
    private String moServiceID, moSkilIDs, moSkillNames, payType, lawyerService;

    EnterRateFragmentVM(Application application, FragmentEnterRateBinding enterRateBinding, BaseFragment enterRateFragment) {
        super(application);
        binding = enterRateBinding;
        fragment = enterRateFragment;
        initData();
    }

    private void initData() {
        if (fragment.getArguments() != null) {
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            moSkilIDs = fragment.getArguments().getString(Constants.SKILL_IDS);
            moSkillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);

            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);
        }
//        isFixedPrice = Preferences.readBoolean(fragment.activity, Constants.IS_FIXED_PRICE, false);

        if (fragment.activity.getCurrency().equals("SAR")) {
            binding.txtSign.setText(fragment.getString(R.string.sar));
        } else {
            binding.txtSign.setText("$");
        }

//        if (isFixedPrice) {
            binding.tvTitle.setText(fragment.activity.getString(R.string.specific_budget_in_mind));
            binding.rateSeekbar.setMinValue(MIN_PROJECT_AMOUNT);
//            binding.rateSeekbar.setMaxValue(1500);
            binding.rateSeekbar.setMaxValue(100000);
            binding.tvMinValue.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? "%d "+fragment.getString(R.string.sar) : "$%d", MIN_PROJECT_AMOUNT));
            binding.tvMaxValue.setText(fragment.activity.getCurrency().equals("SAR") ? "100000 "+fragment.getString(R.string.sar) : "$100000");
            binding.tvHour.setVisibility(View.GONE);
            binding.etRate.setText("9");
/*        } else {
            binding.tvTitle.setText(fragment.activity.getString(R.string.specific_hourly_rate_in_mind));
            binding.rateSeekbar.setMinValue(2);
            binding.rateSeekbar.setMaxValue(50);
            binding.tvMinValue.setText(fragment.activity.getCurrency().equals("SAR") ? "2 "+fragment.getString(R.string.sar) + fragment.activity.getString(R.string.hr) : "$2" + fragment.activity.getString(R.string.hr));
            binding.tvMaxValue.setText(fragment.activity.getCurrency().equals("SAR") ? "50 "+fragment.getString(R.string.sar) + fragment.activity.getString(R.string.hr) : "$50" + fragment.activity.getString(R.string.hr));
            binding.etRate.setText("2");
        }*/

        binding.rateSeekbar.setOnSeekbarChangeListener(minValue -> {
//            if (isFixedPrice) {
                binding.tvMinValue.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? "%s "+fragment.getString(R.string.sar) : "$%s", minValue));
//            } else {
//                binding.tvMinValue.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? "%s "+fragment.getString(R.string.sar) + fragment.activity.getString(R.string.hr) : "$%s" + fragment.activity.getString(R.string.hr), minValue));
//            }
            binding.etRate.setText(String.valueOf(minValue));
        });

        if (!isEdit) {
            ((PostJobActivity) fragment.activity).setProgressView(0.5f);
        } else {
            ((PostJobActivity) fragment.activity).hideProgressView();
            String budget = Preferences.readString(fragment.activity, Constants.BUDGET, "");
            if (!TextUtils.isEmpty(budget)) {
                binding.etRate.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(fragment.activity, budget) : Utils.priceWithout$(budget));
                binding.rateSeekbar.setMinStartValue(Float.parseFloat(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(fragment.activity, budget) : Utils.priceWithout$(budget)));
                binding.rateSeekbar.apply();
            }
        }

        binding.etRate.setOnEditorActionListener((v, actionId, event) -> {
            Utils.hideSoftKeyboard(fragment.activity);
            return false;
        });

        binding.etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rate = getRate();
                if (!TextUtils.isEmpty(rate)) {
//                    if (isFixedPrice) {
                        if (Integer.parseInt(rate) > 999999) {
                            binding.etRate.setText("999999");
                            fragment.activity.validationError(fragment.activity.getString(R.string.you_can_enter_between) + " " + MIN_PROJECT_AMOUNT + "  " + fragment.activity.getString(R.string.to_) + " 9,99,999");
                        }
//                    } else {
//                        if (Integer.parseInt(rate) > MIN_PROJECT_AMOUNT) {
//                            binding.etRate.setText(String.format(Locale.US, "%d", MIN_PROJECT_AMOUNT));
//                            fragment.activity.validationError(fragment.activity.getString(R.string.you_can_enter_between_2_to_50));
//                        }
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((PostJobActivity) fragment.activity).getNextView().setOnClickListener(view -> {
            saveValue();
        });

    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).showNextView();
        if (!isEdit) {
            ((PostJobActivity) fragment.activity).setProgressView(0.5f);
        } else {
            ((PostJobActivity) fragment.activity).hideProgressView();
        }
    }

    private String getRate() {
        return Objects.requireNonNull(binding.etRate.getText()).toString().trim();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_next:
                saveValue();
                break;
        }
    }

    private void saveValue() {
        if (TextUtils.isEmpty(getRate())) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_enter_specific_rate));
            return;
        }
        if (getRate().startsWith("0")) {
            fragment.activity.validationError(fragment.activity.getString(R.string.amount_should_not_start_with_0));
            return;
        }
        if (/*isFixedPrice && */Integer.parseInt(getRate()) < MIN_PROJECT_AMOUNT) {
            fragment.activity.validationError(fragment.activity.getString(R.string.you_can_enter_between) + " " + MIN_PROJECT_AMOUNT + fragment.activity.getString(R.string.to_) + "  9,99,999");
            return;
        }
        /*if (!isFixedPrice && Integer.parseInt(getRate()) < 2) {
            fragment.activity.validationError(fragment.activity.getString(R.string.you_can_enter_between_2_to_50));
            return;
        }*/

        if (isEdit) {
            Intent intent = new Intent(fragment.activity, PriceRateFragment.class);
            intent.putExtra(Constants.BUDGET, getRate());
            intent.putExtra(Constants.CLIENT_RATE_ID, 0);
            intent.putExtra(Constants.CLIENT_RATE, "");
            intent.putExtra(Constants.PAY_TYPE, payType);
            Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
            fragment.activity.getSupportFragmentManager().popBackStack();

        } else {
            Fragment fragmentA;
            fragmentA = new DeadlineFragment();

            Bundle bundle = new Bundle();
            bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
            bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
            bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");

            bundle.putString(Constants.BUDGET, getRate());
            bundle.putInt(Constants.CLIENT_RATE_ID, 0);
            bundle.putString(Constants.CLIENT_RATE, "");
            bundle.putString(Constants.PAY_TYPE, payType);
            bundle.putString(Constants.PLATFORM_NAME, lawyerService);
            fragmentA.setArguments(bundle);
            fragment.activity.replaceFragment(fragmentA);
        }
    }
}
