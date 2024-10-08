package com.nojom.client.ui.projects;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_GET_CLIENT_RATES;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentSelectRateBinding;
import com.nojom.client.fragment.postjob.PostJobFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ClientRate;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

class HirePriceRateActivityVM extends AndroidViewModel implements View.OnClickListener, RecyclerviewAdapter.OnViewBindListner, RequestResponseListener {
    private FragmentSelectRateBinding binding;
    private BaseActivity fragment;
    private List<ClientRate.Data> priceList;
    private RecyclerviewAdapter mAdapter;
    private boolean isFixedPrice;
    private boolean isEdit;
    private String describe, attachLocalFile;
    private AgentProfile agentData;

    HirePriceRateActivityVM(Application application, FragmentSelectRateBinding selectRateBinding, BaseActivity priceRateFragment) {
        super(application);
        binding = selectRateBinding;
        fragment = priceRateFragment;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.header.setVisibility(View.VISIBLE);
        binding.llProgress.setVisibility(View.VISIBLE);

        if (fragment.getIntent() != null) {
            describe = fragment.getIntent().getStringExtra(Constants.DESCRIBE);
            attachLocalFile = fragment.getIntent().getStringExtra(Constants.ATTACH_LOCAL_FILE);
            agentData = (AgentProfile) fragment.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        }

        binding.txtTitle.setText(Utils.getColorString(fragment, fragment.getString(R.string.what_is_your_budget_for_this_service),
                fragment.getString(R.string.budget_).toLowerCase(), R.color.colorPrimary));
        learnMoreClick();

        binding.tvEnterPrice.setOnClickListener(this);

        isFixedPrice = Preferences.readBoolean(fragment, Constants.IS_FIXED_PRICE, false);

        priceList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment);
        binding.rvRates.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvRates.getContext(),
                linearLayoutManager.getOrientation());
        binding.rvRates.addItemDecoration(dividerItemDecoration);

        ClientRate clientRate = Preferences.getClientRate(fragment);
        if (clientRate != null && clientRate.data != null && clientRate.data.size() > 0) {
            priceList = new ArrayList<>();
            priceList = clientRate.data;
            setAdapter();
        } else {
            getClientRates();
        }

        String budget = Preferences.readString(fragment, Constants.BUDGET, "");
        if (!TextUtils.isEmpty(budget)) {
            binding.tvEnterPrice.setText(budget);
        }
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
            tvRates.setText(String.format(Locale.US, fragment.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar_s_sar_) : "$%s - $%s", item.rangeFrom, item.rangeTo));
        } else {
            tvRates.setText(String.format(Locale.US, fragment.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : fragment.getString(R.string.dollar)+"%s", item.rangeFrom));
        }

        view.setOnClickListener(v -> {
//            if (isEdit) {
//                // imgBack.performClick();
//                Intent intent = new Intent(fragment, PostJobFragment.class);
//                intent.putExtra(Constants.BUDGET, "");
//                intent.putExtra(Constants.CLIENT_RATE_ID, item.id);
//                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null")) {
//                    intent.putExtra(Constants.CLIENT_RATE, "$" + item.rangeFrom + " - $" + item.rangeTo);
//                } else {
//                    intent.putExtra(Constants.CLIENT_RATE, "$" + item.rangeFrom);
//                }
////                Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
//                fragment.getSupportFragmentManager().popBackStack();
//            } else {
////                changeFragment(true, item);
//            }

            Intent intent = new Intent(fragment, HireDeadlineActivity.class);
            intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
            intent.putExtra(Constants.DESCRIBE, describe);
            intent.putExtra(Constants.ATTACH_LOCAL_FILE, attachLocalFile);
            intent.putExtra(Constants.BUDGET, "");
            intent.putExtra(Constants.CLIENT_RATE_ID, item.id);
            if (fragment.getCurrency().equals("SAR")) {
                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null")) {
                    intent.putExtra(Constants.CLIENT_RATE, item.rangeFrom + " "+fragment.getString(R.string.sar)+" - "+fragment.getString(R.string.dollar) + item.rangeTo + " "+fragment.getString(R.string.sar));
                } else {
                    intent.putExtra(Constants.CLIENT_RATE, item.rangeFrom + " "+fragment.getString(R.string.sar));
                }
            } else {
                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null")) {
                    intent.putExtra(Constants.CLIENT_RATE, fragment.getString(R.string.dollar) + item.rangeFrom + " - "+fragment.getString(R.string.dollar) + item.rangeTo);
                } else {
                    intent.putExtra(Constants.CLIENT_RATE, fragment.getString(R.string.dollar) + item.rangeFrom);
                }
            }
            fragment.startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.finish();
                break;
            case R.id.tv_enter_price:
                Intent intent = new Intent(fragment, HireEnterRateActivity.class);
                intent.putExtra(Constants.DESCRIBE, describe);
                intent.putExtra(Constants.ATTACH_LOCAL_FILE, attachLocalFile);
                intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
                fragment.startActivity(intent);
                break;
        }
    }

//    private void changeFragment(boolean isDescribe, ClientRate.Data item) {
//        Fragment fragmentA;
//        Bundle bundle = new Bundle();
//        if (isDescribe) {
//            fragmentA = new DeadlineFragment();
//            if (item != null) {
//                bundle.putString(Constants.BUDGET, "");
//                bundle.putInt(Constants.CLIENT_RATE_ID, item.id);
//                if (!TextUtils.isEmpty(item.rangeTo) && !item.rangeTo.equals("null"))
//                    bundle.putString(Constants.CLIENT_RATE, "$" + item.rangeFrom + " - $" + item.rangeTo);
//                else
//                    bundle.putString(Constants.CLIENT_RATE, "$" + item.rangeFrom);
//            }
//
//        } else {
//            fragmentA = new EnterRateFragment();
//            bundle.putBoolean(Constants.IS_EDIT, isEdit);
//            if (isEdit) {
//                fragmentA.setArguments(bundle);
//                fragment.replaceFragmentWithTarget(fragment, fragmentA, Constants.ENTER_PRICE_FRAGMENT_CODE);
//                return;
//            }
//        }
//        bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
//        bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
//        bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");
//        bundle.putString(Constants.PAY_TYPE, payType);
//        bundle.putString(Constants.PLATFORM_NAME, lawyerService);
//        fragmentA.setArguments(bundle);
//
//        fragment.replaceFragment(fragmentA);
//    }

    private void getClientRates() {
        if (!fragment.isNetworkConnected())
            return;

        binding.shimmerLayout.startShimmer();
        binding.shimmerLayout.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("pay_type_id", /*isFixedPrice ? 1 + "" :*/ 1 + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment, API_GET_CLIENT_RATES, true, map);
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


    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ENTER_PRICE_FRAGMENT_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String budget = data.getStringExtra(Constants.BUDGET);
                String clientRate = data.getStringExtra(Constants.CLIENT_RATE);
                int clientRateId = data.getIntExtra(Constants.CLIENT_RATE_ID, 0);

                Intent intent = new Intent(fragment, PostJobFragment.class);
                intent.putExtra(Constants.BUDGET, budget);
                intent.putExtra(Constants.CLIENT_RATE_ID, clientRateId);
                intent.putExtra(Constants.CLIENT_RATE, clientRate);
//                intent.putExtra(Constants.PAY_TYPE, payType);
//                intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
//                Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
                fragment.getSupportFragmentManager().popBackStack();

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
                Preferences.setClientRate(fragment, model);
            }
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }
//
//    public void goBack() {
//        Intent intent = new Intent();
//        intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
//        intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
//        intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
//        intent.putExtra(Constants.PAY_TYPE, payType);
//        intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
//        fragment.setResult(RESULT_OK, intent);
//        if (isEdit) {
//            fragment.getSupportFragmentManager().popBackStack();
//        } else {
//            ((PostJobActivity) fragment).finish();
//        }
//    }
}
