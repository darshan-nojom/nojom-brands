package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.MIN_PROJECT_AMOUNT;

import android.app.Application;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentEnterRateBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.Locale;
import java.util.Objects;

class HireEnterRateActivityVM extends AndroidViewModel implements View.OnClickListener {
    private final FragmentEnterRateBinding binding;
    private final BaseActivity fragment;
    //private boolean isFixedPrice;
    private boolean isEdit;
    private String moServiceID, moSkilIDs, moSkillNames, payType, lawyerService;
    private String describe, attachLocalFile;
    private AgentProfile agentData;

    HireEnterRateActivityVM(Application application, FragmentEnterRateBinding enterRateBinding, BaseActivity enterRateFragment) {
        super(application);
        binding = enterRateBinding;
        fragment = enterRateFragment;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.btnLastStep.setOnClickListener(this);
        binding.header.setVisibility(View.VISIBLE);
        binding.llProgress.setVisibility(View.VISIBLE);
        binding.relBtn.setVisibility(View.VISIBLE);

        if (fragment.getIntent() != null) {
            describe = fragment.getIntent().getStringExtra(Constants.DESCRIBE);
            attachLocalFile = fragment.getIntent().getStringExtra(Constants.ATTACH_LOCAL_FILE);
            agentData = (AgentProfile) fragment.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        }

        //isFixedPrice = Preferences.readBoolean(fragment, Constants.IS_FIXED_PRICE, false);

        if (fragment.getCurrency().equals("SAR")) {
            binding.txtSign.setText(fragment.getString(R.string.sar));
        } else {
            binding.txtSign.setText(fragment.getString(R.string.dollar));
        }

//        if (isFixedPrice) {
        binding.tvTitle.setText(fragment.getString(R.string.specific_budget_in_mind));
        binding.rateSeekbar.setMinValue(MIN_PROJECT_AMOUNT);
//            binding.rateSeekbar.setMaxValue(1500);
        binding.rateSeekbar.setMaxValue(100000);
        binding.tvMinValue.setText(String.format(Locale.US, fragment.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : fragment.getString(R.string.dollar) + "%d", MIN_PROJECT_AMOUNT));
        binding.tvMaxValue.setText(fragment.getCurrency().equals("SAR") ? "100000 " + fragment.getString(R.string.sar) : "$100000");
        binding.tvHour.setVisibility(View.GONE);
        binding.etRate.setText("9");
        /*} else {
            binding.tvTitle.setText(fragment.getString(R.string.specific_hourly_rate_in_mind));
            binding.rateSeekbar.setMinValue(2);
            binding.rateSeekbar.setMaxValue(50);
            binding.tvMinValue.setText(fragment.getCurrency().equals("SAR") ? "2 "+fragment.getString(R.string.sar) + fragment.getString(R.string.hr) : "$2" + fragment.getString(R.string.hr));
            binding.tvMaxValue.setText(fragment.getCurrency().equals("SAR") ? "50 "+fragment.getString(R.string.sar) + fragment.getString(R.string.hr) : "$50" + fragment.getString(R.string.hr));
            binding.etRate.setText("2");
        }*/

        binding.rateSeekbar.setOnSeekbarChangeListener(minValue -> {
//            if (isFixedPrice) {
            binding.tvMinValue.setText(String.format(Locale.US, fragment.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : fragment.getString(R.string.dollar) + "%s", minValue));
//            } else {
//                binding.tvMinValue.setText(String.format(Locale.US, fragment.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : fragment.getString(R.string.dollar)+"%s" + fragment.getString(R.string.hr), minValue));
//            }
            binding.etRate.setText(String.valueOf(minValue));
        });

//        if (!isEdit) {
//            ((PostJobActivity) fragment).setProgressView(0.5f);
//        } else {
//            ((PostJobActivity) fragment).hideProgressView();
//            String budget = Preferences.readString(fragment, Constants.BUDGET, "");
//            if (!TextUtils.isEmpty(budget)) {
//                binding.etRate.setText(Utils.priceWithout$(budget));
//                binding.rateSeekbar.setMinStartValue(Float.parseFloat(Utils.priceWithout$(budget)));
//                binding.rateSeekbar.apply();
//            }
//        }

        binding.etRate.setOnEditorActionListener((v, actionId, event) -> {
            Utils.hideSoftKeyboard(fragment);
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
                        fragment.validationError(fragment.getString(R.string.you_can_enter_between) + " " + MIN_PROJECT_AMOUNT + "  " + fragment.getString(R.string.to_) + " 9,99,999");
                    }
//                    } else {
//                        if (Integer.parseInt(rate) > MIN_PROJECT_AMOUNT) {
//                            binding.etRate.setText(String.format(Locale.US, "%d", MIN_PROJECT_AMOUNT));
//                            fragment.validationError(fragment.getString(R.string.you_can_enter_between_2_to_50));
//                        }
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void onResumeMethod() {
        setProgressView(0.50f);
    }

    void setProgressView(float progress) {
        binding.llProgress.setVisibility(View.VISIBLE);

        float remainProgress = 1.0f - progress;
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) binding.progressView.getLayoutParams();
        lParams.weight = progress;
        LinearLayout.LayoutParams rParams = (LinearLayout.LayoutParams) binding.blankView.getLayoutParams();
        rParams.weight = remainProgress;

        binding.progressView.setLayoutParams(lParams);
        binding.blankView.setLayoutParams(rParams);
    }

    void hideProgressView() {
        binding.llProgress.setVisibility(View.GONE);
    }


    private String getRate() {
        return Objects.requireNonNull(binding.etRate.getText()).toString().trim();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.finish();
                break;
            case R.id.btn_last_step:
                saveValue();
                break;
        }
    }

    private void saveValue() {
        if (TextUtils.isEmpty(getRate())) {
            fragment.validationError(fragment.getString(R.string.please_enter_specific_rate));
            return;
        }
        if (getRate().startsWith("0")) {
            fragment.validationError(fragment.getString(R.string.amount_should_not_start_with_0));
            return;
        }
        if (/*isFixedPrice && */Integer.parseInt(getRate()) < MIN_PROJECT_AMOUNT) {
            fragment.validationError(fragment.getString(R.string.you_can_enter_between) + " " + MIN_PROJECT_AMOUNT + fragment.getString(R.string.to_) + "  9,99,999");
            return;
        }
//        if (!isFixedPrice && Integer.parseInt(getRate()) < 2) {
//            fragment.validationError(fragment.getString(R.string.you_can_enter_between_2_to_50));
//            return;
//        }

        Intent intent = new Intent(fragment, HireDeadlineActivity.class);
        intent.putExtra(Constants.DESCRIBE, describe);
        intent.putExtra(Constants.ATTACH_LOCAL_FILE, attachLocalFile);
        intent.putExtra(Constants.BUDGET, getRate());
        intent.putExtra(Constants.CLIENT_RATE_ID, 0);
        intent.putExtra(Constants.CLIENT_RATE, "");
        intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
        fragment.startActivity(intent);
//
//        if (isEdit) {
//            Intent intent = new Intent(fragment, PriceRateFragment.class);
//            intent.putExtra(Constants.BUDGET, getRate());
//            intent.putExtra(Constants.CLIENT_RATE_ID, 0);
//            intent.putExtra(Constants.CLIENT_RATE, "");
//            intent.putExtra(Constants.PAY_TYPE, payType);
////            Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
//            fragment.getSupportFragmentManager().popBackStack();
//
//        } else {
//            Fragment fragmentA;
//            fragmentA = new DeadlineFragment();
//
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
//            bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
//            bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");
//
//            bundle.putString(Constants.BUDGET, getRate());
//            bundle.putInt(Constants.CLIENT_RATE_ID, 0);
//            bundle.putString(Constants.CLIENT_RATE, "");
//            bundle.putString(Constants.PAY_TYPE, payType);
//            bundle.putString(Constants.PLATFORM_NAME, lawyerService);
//            fragmentA.setArguments(bundle);
//            fragment.replaceFragment(fragmentA);
//        }
    }
}
