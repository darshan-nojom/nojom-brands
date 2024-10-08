package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GET_CLIENT_RATES;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentSelectRateBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ClientRate;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

class PriceRateFragmentVM extends AndroidViewModel implements View.OnClickListener, RecyclerviewAdapter.OnViewBindListner, RequestResponseListener {
    private FragmentSelectRateBinding binding;
    private BaseFragment fragment;
    private List<ClientRate.Data> priceList;
    private RecyclerviewAdapter mAdapter;
    private boolean isFixedPrice;
    private boolean isEdit;
    private String moServiceID, moSkilIDs, moSkillNames, payType, lawyerService;

    PriceRateFragmentVM(Application application, FragmentSelectRateBinding selectRateBinding, BaseFragment priceRateFragment) {
        super(application);
        binding = selectRateBinding;
        fragment = priceRateFragment;
        initData();
    }

    private void initData() {
        binding.txtTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.what_is_your_budget_for_this_service), fragment.getString(R.string.budget_).toLowerCase(), R.color.colorPrimary));
        learnMoreClick();

        binding.tvEnterPrice.setOnClickListener(this);

        if (fragment.getArguments() != null) {
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            moSkilIDs = fragment.getArguments().getString(Constants.SKILL_IDS);
            moSkillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);
            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);
        }
        isFixedPrice = Preferences.readBoolean(fragment.activity, Constants.IS_FIXED_PRICE, false);

        priceList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvRates.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvRates.getContext(), linearLayoutManager.getOrientation());
        binding.rvRates.addItemDecoration(dividerItemDecoration);

        ClientRate clientRate = Preferences.getClientRate(fragment.activity);
        if (clientRate != null && clientRate.data != null && clientRate.data.size() > 0) {
            priceList = new ArrayList<>();
            priceList = clientRate.data;
            setAdapter();
        } else {
            getClientRates();
        }

        String budget = Preferences.readString(fragment.activity, Constants.BUDGET, "");
        if (!TextUtils.isEmpty(budget)) {
            binding.tvEnterPrice.setText(budget);
        }
        Utils.trackAppsFlayerEvent(fragment.activity, "Budget_Screen");

        ((PostJobActivity) fragment.activity).getBackView().setOnClickListener(view -> {
            goBack();
        });
    }

    private void learnMoreClick() {
        AtomicBoolean isExpand = new AtomicBoolean(true);
        binding.txtLearnMore.setOnClickListener(view -> {
            if (isExpand.get()) {
                isExpand.set(false);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(360);
                binding.txtLearnMoreDesc.setVisibility(View.VISIBLE);
            } else {
                isExpand.set(true);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(180);
                binding.txtLearnMoreDesc.setVisibility(View.GONE);
            }
        });
    }

    private void setAdapter() {
        if (priceList != null && priceList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new RecyclerviewAdapter((ArrayList<?>) priceList, R.layout.item_select_rate, this);
            }
            mAdapter.doRefresh((ArrayList<?>) priceList);
            if (binding.rvRates.getAdapter() == null) {
                binding.rvRates.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public void bindView(View view, final int position) {
        final ClientRate.Data item = priceList.get(position);
        TextView tvRates = view.findViewById(R.id.tv_rates);
        if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null")) {
            tvRates.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar_s_sar_) : "$%s - $%s", item.rangeFrom, item.rangeTo));
        } else {
            tvRates.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", item.rangeFrom));
        }

        view.setOnClickListener(v -> {
            if (isEdit) {
                // imgBack.performClick();
                Intent intent = new Intent(fragment.activity, PostJobFragment.class);
                intent.putExtra(Constants.BUDGET, "");
                intent.putExtra(Constants.CLIENT_RATE_ID, item.id);
                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null")) {
                    if (fragment.activity.getCurrency().equals("SAR")) {
                        intent.putExtra(Constants.CLIENT_RATE, item.rangeFrom + " - " + item.rangeTo + " " + fragment.getString(R.string.sar));
                    } else {
                        intent.putExtra(Constants.CLIENT_RATE, "$" + item.rangeFrom + " - $" + item.rangeTo);
                    }
                } else {
                    intent.putExtra(Constants.CLIENT_RATE, fragment.activity.getCurrency().equals("SAR") ? item.rangeFrom + " " + fragment.getString(R.string.sar) : "$" + item.rangeFrom);
                }
                Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
                fragment.activity.getSupportFragmentManager().popBackStack();
            } else {
                changeFragment(true, item);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_enter_price:
                changeFragment(false, null);
                break;
        }
    }

    private void changeFragment(boolean isDescribe, ClientRate.Data item) {
        Fragment fragmentA;
        Bundle bundle = new Bundle();
        if (isDescribe) {
            fragmentA = new DeadlineFragment();
            if (item != null) {
                bundle.putString(Constants.BUDGET, "");
                bundle.putInt(Constants.CLIENT_RATE_ID, item.id);
                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null"))
                    bundle.putString(Constants.CLIENT_RATE, fragment.activity.getCurrency().equals("SAR") ? item.rangeFrom + " - " + item.rangeTo + " " + fragment.getString(R.string.sar)
                            : "$" + item.rangeFrom + " - $" + item.rangeTo);
                else
                    bundle.putString(Constants.CLIENT_RATE, fragment.activity.getCurrency().equals("SAR") ? item.rangeFrom + " " + fragment.getString(R.string.sar) : "$" + item.rangeFrom);
            }

        } else {
            fragmentA = new EnterRateFragment();
            bundle.putBoolean(Constants.IS_EDIT, isEdit);
            if (isEdit) {
                fragmentA.setArguments(bundle);
                fragment.activity.replaceFragmentWithTarget(fragment, fragmentA, Constants.ENTER_PRICE_FRAGMENT_CODE);
                return;
            }
        }
        bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
        bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
        bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");
        bundle.putString(Constants.PAY_TYPE, payType);
        bundle.putString(Constants.PLATFORM_NAME, lawyerService);
        String deadl = fragment.getArguments().getString("deadline");
        bundle.putString("deadline", deadl);
        String desc = null;
        if (fragment.getArguments() != null) {
            desc = fragment.getArguments().getString(Constants.DESCRIBE);
        }
        bundle.putString(Constants.DESCRIBE, desc);

        fragmentA.setArguments(bundle);

        fragment.activity.replaceFragment(fragmentA);
    }

    private void getClientRates() {
        if (!fragment.activity.isNetworkConnected()) return;

        binding.shimmerLayout.startShimmer();
        binding.shimmerLayout.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("pay_type_id", /*isFixedPrice ? 1 + "" :*/ 1 + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_CLIENT_RATES, true, map);
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideNextView();
        if (!isEdit) {
            ((PostJobActivity) fragment.activity).setProgressView(0.5f);
        } else {
            ((PostJobActivity) fragment.activity).hideProgressView();
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ENTER_PRICE_FRAGMENT_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String budget = data.getStringExtra(Constants.BUDGET);
                String clientRate = data.getStringExtra(Constants.CLIENT_RATE);
                int clientRateId = data.getIntExtra(Constants.CLIENT_RATE_ID, 0);

                Intent intent = new Intent(fragment.activity, PostJobFragment.class);
                intent.putExtra(Constants.BUDGET, budget);
                intent.putExtra(Constants.CLIENT_RATE_ID, clientRateId);
                intent.putExtra(Constants.CLIENT_RATE, clientRate);
                intent.putExtra(Constants.PAY_TYPE, payType);
                intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
                Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
                fragment.activity.getSupportFragmentManager().popBackStack();

            }
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_CLIENT_RATES)) {
            ClientRate model = ClientRate.getClientRates(responseBody);
            if (model != null && model.data != null && model.data.size() > 0) {
                priceList = new ArrayList<>();
                priceList = model.data;
                setAdapter();
                Preferences.setClientRate(fragment.activity, model);
            }
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
        intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
        intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
        intent.putExtra(Constants.PAY_TYPE, payType);
        intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
        fragment.activity.setResult(RESULT_OK, intent);
        if (isEdit) {
            fragment.activity.getSupportFragmentManager().popBackStack();
        } else {
            ((PostJobActivity) fragment.activity).finish();
        }
    }
}
