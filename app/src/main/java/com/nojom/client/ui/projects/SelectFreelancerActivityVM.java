package com.nojom.client.ui.projects;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.ExpertsAdapter;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivitySelectFreelancerBinding;
import com.nojom.client.model.Expert;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.ExpertFilterActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

class SelectFreelancerActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private static final int REQ_FILTER = 101;
    private List<Expert.Data> expertList;
    private ExpertsAdapter mAdapter;
    private int pageNo = 1;
    private String sortType = "JOB";
    private int serviceCatId = -1;
    private int languageId = -1;
    private String workBase = "", availability = "";
    private String skillIds = "";
    private boolean  isSearchClick;
    private ActivitySelectFreelancerBinding binding;
    private BaseActivity activity;
    private boolean isCallAPIForSearch;

    SelectFreelancerActivityVM(Application application, ActivitySelectFreelancerBinding selectFreelancerBinding, BaseActivity selectFreelancerActivity) {
        super(application);
        binding = selectFreelancerBinding;
        activity = selectFreelancerActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.rlCheckAll.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.rlFilter.setOnClickListener(this);
        binding.imgSearch.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);

        expertList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rvPrvFreelancer.setLayoutManager(linearLayoutManager);

        List<ExpertLawyers.Data> expertUsers = Preferences.getExpertUsers(activity);
        if (expertUsers == null || expertUsers.isEmpty()) {
            binding.rlCheckAll.performClick();
        }

        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                resetFilter();
                expertList = new ArrayList<>();
                if (!isCallAPIForSearch) {
                    isCallAPIForSearch = true;
                }
                return true;
            }
            return false;
        });
    }

    private void resetFilter() {
        pageNo = 1;
        expertList = new ArrayList<>();
        sortType = "JOB";
        languageId = -1;
        serviceCatId = -1;
        workBase = "";
        availability = "";
        skillIds = "";
        mAdapter = null;
        binding.tvFilterCount.setVisibility(View.GONE);
    }

    private void setAdapter(boolean isClickOnKeyboardSearch, String path) {
        if (expertList != null && expertList.size() > 0) {
            if (isClickOnKeyboardSearch) {
                mAdapter = new ExpertsAdapter(activity, true, path);
                binding.rvPrvFreelancer.setAdapter(mAdapter);
                mAdapter.initList(expertList);
                isCallAPIForSearch = false;
            } else {
                if (mAdapter == null) {
                    mAdapter = new ExpertsAdapter(activity, true, path);
                    binding.rvPrvFreelancer.setAdapter(mAdapter);
                }
                if (pageNo == 1) {
                    mAdapter.initList(expertList);
                } else {
                    mAdapter.doRefresh(expertList);
                }
            }
        } else {
            if (mAdapter == null) {
                mAdapter = new ExpertsAdapter(activity, true, path);
                binding.rvPrvFreelancer.setAdapter(mAdapter);
            }
            if (mAdapter != null)
                mAdapter.initList(expertList);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
            case R.id.tv_done:
                activity.onBackPressed();
                break;
            case R.id.rl_check_all:
                binding.imgCheckAll.setImageResource(R.drawable.circle_check);
                binding.rlCheckAll.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_rounded_corner_5));
                if (mAdapter != null)
                    mAdapter.unSelectAll();
                break;
            case R.id.rl_filter:
                Intent i = new Intent(activity, ExpertFilterActivity.class);
                i.putExtra(Constants.SORT_BY, sortType);
                i.putExtra(Constants.SERVICE_CATEGORY_ID, serviceCatId);
                i.putExtra(Constants.LANGUAGE_ID, languageId);
                i.putExtra(Constants.WORKBASE, workBase);
                i.putExtra(Constants.AVAILABILITY, availability);
                i.putExtra(Constants.SKILL_ID, skillIds);
                activity.startActivityForResult(i, REQ_FILTER);
                break;
            case R.id.tv_cancel:
                Utils.hideSoftKeyboard(activity);
                binding.etSearch.setText("");
                resetFilter();
                binding.etSearch.setVisibility(View.INVISIBLE);
                binding.imgSearch.setVisibility(View.VISIBLE);
                binding.tvCancel.setVisibility(View.INVISIBLE);

                break;
            case R.id.img_search:
                binding.etSearch.setVisibility(View.VISIBLE);
                binding.imgSearch.setVisibility(View.INVISIBLE);
                binding.tvCancel.setVisibility(View.VISIBLE);
                binding.etSearch.setFocusable(true);
                binding.etSearch.requestFocus();
                binding.etSearch.requestFocusFromTouch();
                break;
        }
    }

    void unSelectAll() {
        binding.imgCheckAll.setImageResource(R.drawable.circle_uncheck);
        binding.rlCheckAll.setBackground(ContextCompat.getDrawable(activity, R.drawable.lightgray_border_5));
        Preferences.setExpertUsers(activity, null);
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_FILTER) {
            if (data != null) {
                resetFilter();
                binding.etSearch.setText("");

                sortType = data.getStringExtra(Constants.SORT_BY);
                serviceCatId = data.getIntExtra(Constants.SERVICE_CATEGORY_ID, 0);
                languageId = data.getIntExtra(Constants.LANGUAGE_ID, 0);
                workBase = data.getStringExtra(Constants.WORKBASE);
                availability = data.getStringExtra(Constants.AVAILABILITY);
                skillIds = data.getStringExtra(Constants.SKILL_ID);

                expertList = new ArrayList<>();
            }
        }
    }

    void onResumeMethod() {
        int filterCount = 0;
        if (!activity.isEmpty(sortType)) {
            filterCount = filterCount + 1;
        }

        if (serviceCatId != -1) {
            filterCount = filterCount + 1;
        }

        if (languageId != -1) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(workBase)) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(availability)) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(skillIds)) {
            filterCount = filterCount + 1;
        }

        if (filterCount > 0) {
            binding.tvFilterCount.setVisibility(View.VISIBLE);
            binding.tvFilterCount.setText(String.format(Locale.US,"%d", filterCount));
        } else {
            binding.tvFilterCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        Expert expert = Expert.getExperts(responseBody);
        if (expert != null && expert.data != null) {
            if (expert.data.size() > 0) {
                expertList.addAll(expert.data);
                pageNo++;
            }
            setAdapter(isSearchClick, expert.path);
        } else {
            isCallAPIForSearch = false;
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        isCallAPIForSearch = false;
        isSearchClick = false;
    }
}
