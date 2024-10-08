package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.DeveloperListAdapter;
import com.nojom.client.databinding.FragmentChooseDeveloperBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

class ChooseDeveloperFragmentVM extends AndroidViewModel implements View.OnClickListener, DeveloperListAdapter.OnItemClickListener {
    private FragmentChooseDeveloperBinding binding;
    private BaseFragment fragment;
    private boolean isEdit;
    private String serviceId = "0";
    private String lawyerService;
    private DeveloperListAdapter mAdapter;

    ChooseDeveloperFragmentVM(Application application, FragmentChooseDeveloperBinding chooseDeveloperBinding, BaseFragment chooseDeveloperFragment) {
        super(application);
        binding = chooseDeveloperBinding;
        fragment = chooseDeveloperFragment;
        initData();
    }

    private void initData() {
        if (fragment.activity.getLanguage().equals("ar")) {
            binding.txtTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.select_a_category),
                    "المجال", R.color.colorPrimary));
        } else {
            binding.txtTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.select_a_category),
                    fragment.getString(R.string.category).toLowerCase(), R.color.colorPrimary));
        }

        learnMoreClick();

        if (fragment.getArguments() != null) {
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
            serviceId = fragment.getArguments().getString(Constants.PLATFORM_ID);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);
        }

        if (TextUtils.isEmpty(serviceId)) {
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvHire.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvHire.getContext(),
                linearLayoutManager.getOrientation());
        binding.rvHire.addItemDecoration(dividerItemDecoration);

        if (((PostJobActivity) fragment.activity).subServiceList != null && ((PostJobActivity) fragment.activity).subServiceList.size() > 0) {
            setAdapter();
        }

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAdapter != null)
                    mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Utils.trackAppsFlayerEvent(fragment.activity, "All_Category_Screen");

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

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideNextView();
        if (!isEdit) {
            ((PostJobActivity) fragment.activity).setProgressView(0.25f);
        } else {
            ((PostJobActivity) fragment.activity).hideProgressView();
        }
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                goBack();
                break;
            case R.id.tv_next:
                if (mAdapter.getSelectedItem() != null) {
                    itemClickHandle(mAdapter.getSelectedItem());
                } else {
                    fragment.activity.validationError(fragment.activity.getString(R.string.please_select_skill));
                }
                break;
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new DeveloperListAdapter(fragment.activity, ((PostJobActivity) fragment.activity).subServiceList, this::itemClickHandle);
        }

        if (binding.rvHire.getAdapter() == null) {
            binding.rvHire.setAdapter(mAdapter);
        }
    }

    private void itemClickHandle(ServicesModel.Data item) {
        if (isEdit) {
            // goBack();
            Intent intent = new Intent(fragment.activity, PostJobFragment.class);
            intent.putExtra(Constants.SKILL_IDS, item.id + "");
            intent.putExtra(Constants.SKILL_NAMES, item.getServNameByLang(fragment.activity.getLanguage()) == null ? item.name :
                    item.getServNameByLang(fragment.activity.getLanguage()));
//            intent.putExtra(Constants.SKILL_NAMES, item.getServNameByLang(fragment.activity.getLanguage()));
            intent.putExtra(Constants.PLATFORM_NAME, lawyerService + "");
            Objects.requireNonNull(fragment.getTargetFragment()).onActivityResult(fragment.getTargetRequestCode(), RESULT_OK, intent);
            fragment.activity.getSupportFragmentManager().popBackStack();
        } else {
            Fragment fragmentA;
            Preferences.writeBoolean(fragment.activity, Constants.IS_FIXED_PRICE, true);
            fragmentA = new PriceRateFragment();

            Bundle bundle = new Bundle();
            bundle.putString(Constants.PLATFORM_ID, serviceId + "");
            bundle.putString(Constants.SKILL_IDS, item.id + "");
            bundle.putString(Constants.SKILL_NAMES, item.getServNameByLang(fragment.activity.getLanguage()) == null ? item.name :
                    item.getServNameByLang(fragment.activity.getLanguage()));
            bundle.putString(Constants.PLATFORM_NAME, lawyerService + "");

            String deadl = null;
            if (fragment.getArguments() != null) {
                deadl = fragment.getArguments().getString("deadline");
                bundle.putString("deadline", deadl);
                String desc = null;
                if (fragment.getArguments() != null) {
                    desc = fragment.getArguments().getString(Constants.DESCRIBE);
                }
                bundle.putString(Constants.DESCRIBE, desc);
            }

            fragmentA.setArguments(bundle);
            fragment.activity.replaceFragment(fragmentA);
        }
    }

    @Override
    public void onItemClick(ServicesModel.Data item) {
        itemClickHandle(item);
    }

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLATFORM_ID, serviceId + "");
        intent.putExtra(Constants.PLATFORM_NAME, lawyerService + "");
        fragment.activity.setResult(RESULT_OK, intent);
        if (isEdit) {
            fragment.activity.getSupportFragmentManager().popBackStack();
        } else {
            ((PostJobActivity) fragment.activity).finish();
        }
    }
}
