package com.nojom.client.fragment.postjob;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.databinding.FragmentSelectServiceBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.SimpleDividerItemDecoration;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

class SelectServiceFragmentVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner/*, RequestResponseListener*/ {
    private final FragmentSelectServiceBinding binding;
    private final BaseFragment fragment;
    private RecyclerviewAdapter mAdapter;

    SelectServiceFragmentVM(Application application, FragmentSelectServiceBinding selectServiceBinding, BaseFragment selectServiceFragment) {
        super(application);
        binding = selectServiceBinding;
        fragment = selectServiceFragment;
        initData();
    }

    private void initData() {
        binding.txtTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.choose_a_platform),
                fragment.getString(R.string.platform).toLowerCase(), R.color.colorPrimary));
        learnMoreClick();

        Preferences.writeString(fragment.activity, Constants.SKILL_IDS, "");
        Preferences.writeString(fragment.activity, Constants.SKILL_NAMES, "");
        Preferences.writeString(fragment.activity, Constants.CLIENT_RATE, "");
        Preferences.writeInteger(fragment.activity, Constants.CLIENT_RATE_ID, 0);
        Preferences.writeString(fragment.activity, Constants.BUDGET, "");
        Preferences.writeString(fragment.activity, Constants.DESCRIBE, "");
        Preferences.writeString(fragment.activity, Constants.ATTACH_FILE, "");
        Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, "");
        Preferences.writeString(fragment.activity, Constants.ATTACH_FILE_ID, "");

        // Check for serviceId is present or not
        String serviceId = Preferences.readString(fragment.activity, Constants.PLATFORM_ID, "");
        String serviceName = Preferences.readString(fragment.activity, Constants.PLATFORM_NAME, "");
        if (!TextUtils.isEmpty(serviceId)) {// This case may come in case of Rehire Agent, Edit case, Hire etc.
            Preferences.writeString(fragment.activity, Constants.PLATFORM_ID, "");
            Preferences.writeString(fragment.activity, Constants.PLATFORM_NAME, "");
            serviceClickHandle(serviceId, serviceName);

        } else {
            Preferences.writeString(fragment.activity, Constants.PLATFORM_ID, "");
            Preferences.writeString(fragment.activity, Constants.PLATFORM_NAME, "");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvServices.setLayoutManager(linearLayoutManager);
        binding.rvServices.addItemDecoration(new SimpleDividerItemDecoration(fragment.activity));

        if (((PostJobActivity) fragment.activity).platformList != null && ((PostJobActivity) fragment.activity).platformList.size() > 0) {
            setAdapter();
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
        if (mAdapter == null) {
            mAdapter = new RecyclerviewAdapter((ArrayList<?>) ((PostJobActivity) fragment.activity).platformList, R.layout.item_select_service, this);
        }
        mAdapter.doRefresh((ArrayList<?>) ((PostJobActivity) fragment.activity).platformList);
        if (binding.rvServices.getAdapter() == null) {
            binding.rvServices.setAdapter(mAdapter);
        }
    }

    @Override
    public void bindView(View view, int position) {
        final SocialPlatformModel.Data service = ((PostJobActivity) fragment.activity).platformList.get(position);
        TextView tvService = view.findViewById(R.id.tv_service);
        TextView tvSubService = view.findViewById(R.id.tv_subservice);
        ImageView imgService = view.findViewById(R.id.img_service);
        CircularProgressBar progressBar = view.findViewById(R.id.progress_bar);

        tvService.setText(service.getServNameByLang(fragment.activity.getLanguage()));
        tvSubService.setVisibility(View.GONE);

        view.setOnClickListener(view1 -> {
            progressBar.setVisibility(View.VISIBLE);
            imgService.setVisibility(View.GONE);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                imgService.setVisibility(View.VISIBLE);
                serviceClickHandle(String.valueOf(service.id), service.getServNameByLang(fragment.activity.getLanguage()));
            }, 500);
        });
    }

    private void serviceClickHandle(String serviceId, String serviceName) {

        Fragment fragmentA;
        fragmentA = new ChooseDeveloperFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLATFORM_ID, serviceId + "");
        bundle.putString(Constants.PLATFORM_NAME, serviceName);

        fragmentA.setArguments(bundle);

        fragment.activity.replaceFragment(fragmentA);
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideNextView();
        ((PostJobActivity) fragment.activity).hideProgressView();
    }
}
