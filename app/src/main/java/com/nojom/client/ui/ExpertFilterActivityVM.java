package com.nojom.client.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.adapter.SelectItemAdapter;
import com.nojom.client.adapter.SkillFilterAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityExpertFilterBinding;
import com.nojom.client.model.Language;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GET_LANGUAGE;

class ExpertFilterActivityVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, View.OnClickListener, RequestResponseListener {
    private ActivityExpertFilterBinding binding;
    private BaseActivity activity;
    private boolean isTotalJobCheck = false;
    private boolean isTopRatingCheck = false;
    private boolean isHourlyCheck = false;
    private boolean isPartTimeCheck = false;
    private boolean isFullTimeCheck = false;
    private boolean isOfficeBaseCheck = false;
    private boolean isHomeBaseCheck = false;
    private List<ServicesModel.Data> servicesList;
    private List<ServicesModel.Data> skillList;
    private RecyclerviewAdapter mExpertiseAdapter;
    private SkillFilterAdapter mSkillAdapter;
    private ArrayList<Language.Data> languagesArray;
    private SelectItemAdapter itemAdapter;
    private String sortType = "JOB";
    private String workbase = "", availability = "";
    private int languageId = -1;
    private int serviceCatId = -1;
    private String skillIds, serviceName = "";

    ExpertFilterActivityVM(Application application, ActivityExpertFilterBinding expertFilterBinding, BaseActivity expertFilterActivity) {
        super(application);
        binding = expertFilterBinding;
        activity = expertFilterActivity;
        initData();
    }

    private void initData() {
        binding.rlHourly.setOnClickListener(this);
        binding.rlPartTime.setOnClickListener(this);
        binding.rlFullTime.setOnClickListener(this);
        binding.rlOfficeBase.setOnClickListener(this);
        binding.rlHomeBase.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvApply.setOnClickListener(this);
        binding.tvTotalJobs.setOnClickListener(this);
        binding.tvTopRating.setOnClickListener(this);
        binding.tvLanguage.setOnClickListener(this);
        binding.imgClose.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);

        if (activity.getIntent() != null) {
            sortType = activity.getIntent().getStringExtra(Constants.SORT_BY);
            serviceCatId = activity.getIntent().getIntExtra(Constants.SERVICE_CATEGORY_ID, -1);
            languageId = activity.getIntent().getIntExtra(Constants.LANGUAGE_ID, -1);
            workbase = activity.getIntent().getStringExtra(Constants.WORKBASE);
            availability = activity.getIntent().getStringExtra(Constants.AVAILABILITY);
            skillIds = activity.getIntent().getStringExtra(Constants.SKILL_ID);
        }

        if (serviceCatId == -1 || serviceCatId == 0) {
            serviceCatId = 10; //Service Category id for "Any Expertise" is 10
        }

        servicesList = Preferences.getCategoryV2(activity);
        skillList = new ArrayList<>();
        languagesArray = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
        binding.rvExpertise.setLayoutManager(manager);
        binding.rvExpertise.addItemDecoration(new EqualSpacingItemDecoration((int) activity.getResources().getDimension(R.dimen._10sdp)));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rvSkills.setLayoutManager(linearLayoutManager);

        if (servicesList != null && servicesList.size() > 0) {
            ServicesModel.Data otherData = servicesList.get(servicesList.size() - 1);
            servicesList.remove(servicesList.size() - 1);
            servicesList.add(0, otherData);
        }

        binding.rvExpertise.setFocusable(false);

        setData();

        binding.etSkill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mSkillAdapter != null)
                    mSkillAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getLanguageList();
    }

    private void setData() {
        if (sortType.equals("JOB")) {
            binding.tvTotalJobs.performClick();
        } else if (sortType.equals("RATING")) {
            binding.tvTopRating.performClick();
        }

        if (serviceCatId != -1) {
            getServiceByServiceId(serviceCatId);
        }

        mExpertiseAdapter = new RecyclerviewAdapter((ArrayList<?>) servicesList, R.layout.item_skills_edit, this);
        binding.rvExpertise.setAdapter(mExpertiseAdapter);

        if (workbase.contains("0")) {
            isOfficeBaseCheck = true;
            binding.imgOfficeBase.setImageResource(R.drawable.circle_check);
        }

        if (workbase.contains("1")) {
            isHomeBaseCheck = true;
            binding.imgCheckHomeBase.setImageResource(R.drawable.circle_check);
        }

        if (availability.contains("2")) {
            isHourlyCheck = true;
            binding.imgCheckHourly.setImageResource(R.drawable.circle_check);
        }

        if (availability.contains("3")) {
            isPartTimeCheck = true;
            binding.imgCheckPartTime.setImageResource(R.drawable.circle_check);
        }

        if (availability.contains("4")) {
            isFullTimeCheck = true;
            binding.imgCheckFullTime.setImageResource(R.drawable.circle_check);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
            case R.id.tv_cancel:
                activity.onBackPressed();
                break;
            case R.id.tv_clear:
                clearFilter();
                break;
            case R.id.tv_apply:
                String workbase = "";
                String availability = "";

                if (isOfficeBaseCheck && isHomeBaseCheck) {
                    workbase = "2";
                } else if (isOfficeBaseCheck) {
                    workbase = "0";
                } else if (isHomeBaseCheck) {
                    workbase = "1";
                } else {
                    workbase = "";
                }
                if (isHourlyCheck) {
                    availability = "2";
                }

                if (isPartTimeCheck) {
                    if (activity.isEmpty(availability))
                        availability = "3";
                    else
                        availability = availability + ",3";
                }

                if (isFullTimeCheck) {
                    if (activity.isEmpty(availability))
                        availability = "4";
                    else
                        availability = availability + ",4";
                }

                String skillids = "";
                if (mSkillAdapter != null) {
                    List<ServicesModel.Data> serviceData = mSkillAdapter.getData();
                    for (ServicesModel.Data data : serviceData) {
                        if (data.isSelected) {
                            if (activity.isEmpty(skillids)) {
                                skillids = data.id + "";
                            } else {
                                skillids = data.id + "," + skillids;
                            }
                        }
                    }
                }

                Intent i = new Intent();
                i.putExtra(Constants.SORT_BY, sortType);
                i.putExtra(Constants.SERVICE_CATEGORY_ID, serviceCatId == 10 ? 0 : serviceCatId);
                i.putExtra(Constants.SERVICE_NAME, serviceName);
                i.putExtra(Constants.LANGUAGE_ID, languageId);
                i.putExtra(Constants.WORKBASE, workbase);
                i.putExtra(Constants.AVAILABILITY, availability);
                i.putExtra(Constants.SKILL_ID, skillids);
                activity.setResult(RESULT_OK, i);
                activity.finish();
                break;
            case R.id.tv_language:
                showLanguageSelectDialog();
                break;
            case R.id.tv_total_jobs:
                if (isTotalJobCheck) {
                    sortType = "";
                    binding.tvTotalJobs.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
                    binding.tvTotalJobs.setTextColor(ContextCompat.getColor(activity, R.color.black));
                } else {
                    sortType = "JOB";
                    binding.tvTotalJobs.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_button_bg));
                    binding.tvTotalJobs.setTextColor(ContextCompat.getColor(activity, R.color.white));
                }

                isTotalJobCheck = !isTotalJobCheck;

                binding.tvTopRating.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
                binding.tvTopRating.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            case R.id.tv_top_rating:
                if (isTopRatingCheck) {
                    sortType = "";
                    binding.tvTopRating.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
                    binding.tvTopRating.setTextColor(ContextCompat.getColor(activity, R.color.black));
                } else {
                    sortType = "RATING";
                    binding.tvTopRating.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_button_bg));
                    binding.tvTopRating.setTextColor(ContextCompat.getColor(activity, R.color.white));
                }

                isTopRatingCheck = !isTopRatingCheck;

                binding.tvTotalJobs.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
                binding.tvTotalJobs.setTextColor(ContextCompat.getColor(activity, R.color.black));
                break;
            case R.id.rl_hourly:
                if (isHourlyCheck) {
                    binding.imgCheckHourly.setImageResource(R.drawable.circle_uncheck);
                } else {
                    binding.imgCheckHourly.setImageResource(R.drawable.circle_check);
                }
                isHourlyCheck = !isHourlyCheck;
                break;
            case R.id.rl_part_time:
                if (isPartTimeCheck) {
                    binding.imgCheckPartTime.setImageResource(R.drawable.circle_uncheck);
                } else {
                    binding.imgCheckPartTime.setImageResource(R.drawable.circle_check);
                }
                isPartTimeCheck = !isPartTimeCheck;
                break;
            case R.id.rl_full_time:
                if (isFullTimeCheck) {
                    binding.imgCheckFullTime.setImageResource(R.drawable.circle_uncheck);
                } else {
                    binding.imgCheckFullTime.setImageResource(R.drawable.circle_check);
                }
                isFullTimeCheck = !isFullTimeCheck;
                break;
            case R.id.rl_office_base:
                if (isOfficeBaseCheck) {
                    binding.imgOfficeBase.setImageResource(R.drawable.circle_uncheck);
                } else {
                    binding.imgOfficeBase.setImageResource(R.drawable.circle_check);
                }
                isOfficeBaseCheck = !isOfficeBaseCheck;
                break;
            case R.id.rl_home_base:
                if (isHomeBaseCheck) {
                    binding.imgCheckHomeBase.setImageResource(R.drawable.circle_uncheck);
                } else {
                    binding.imgCheckHomeBase.setImageResource(R.drawable.circle_check);
                }
                isHomeBaseCheck = !isHomeBaseCheck;
                break;
        }
    }

    private void clearFilter() {
        sortType = "JOB";
        binding.tvTotalJobs.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_button_bg));
        binding.tvTotalJobs.setTextColor(ContextCompat.getColor(activity, R.color.white));
        binding.tvTopRating.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
        binding.tvTopRating.setTextColor(ContextCompat.getColor(activity, R.color.black));

        serviceCatId = 10;
        mExpertiseAdapter.notifyDataSetChanged();

        mSkillAdapter.clearSelected();

        getServiceByServiceId(serviceCatId);

        languageId = 0;
        workbase = "";
        availability = "";
        skillIds = "";
        binding.tvLanguage.setText(activity.getString(R.string.select_language));

        isHourlyCheck = false;
        binding.imgCheckHourly.setImageResource(R.drawable.circle_uncheck);

        isFullTimeCheck = false;
        binding.imgCheckFullTime.setImageResource(R.drawable.circle_uncheck);

        isPartTimeCheck = false;
        binding.imgCheckPartTime.setImageResource(R.drawable.circle_uncheck);

        isOfficeBaseCheck = false;
        binding.imgOfficeBase.setImageResource(R.drawable.circle_uncheck);

        isHomeBaseCheck = false;
        binding.imgCheckHomeBase.setImageResource(R.drawable.circle_uncheck);
    }

    @Override
    public void bindView(View view, int position) {
        ServicesModel.Data serviceData = servicesList.get(position);

        TextView textView = view.findViewById(R.id.tv_skill);
        if (position == 0) {
            textView.setText(activity.getString(R.string.any_expertise));
        } else {
            textView.setText(serviceData.getServNameByLang(activity.getLanguage()));
        }

        if (serviceData.id == serviceCatId) {
            textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_button_bg));
            textView.setTextColor(Color.WHITE);
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_BOLD);
            textView.setTypeface(tf);
        } else {
            textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.gray_button_bg));
            textView.setTextColor(Color.BLACK);
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_REGULAR);
            textView.setTypeface(tf);
        }

        textView.setOnClickListener(view1 -> {
            serviceCatId = serviceData.id;
            serviceName = serviceData.name;
            mExpertiseAdapter.notifyDataSetChanged();
            getServiceByServiceId(serviceCatId);
        });
    }

    private void getServiceByServiceId(int serviceId) {
        skillList = new ArrayList<>();
        skillList = new ArrayList<>(Preferences.getCategoryV2(activity));

//        for (ServicesModel.Data serviceData : servicesSubList) {
//            if (serviceData.id == serviceId) {
//                skillList.addAll(serviceData.services);
//                break;
//            }
//        }

        if (servicesList.size() > 0) {

            // Sort array by service name and put "Other" service at the end of the list
            if (skillList.size() > 0) {
                ServicesModel.Data otherData = skillList.get(skillList.size() - 1);
                skillList.remove(skillList.size() - 1);
                Collections.sort(skillList, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));
                skillList.add(otherData);
            }

            String[] split = skillIds.split(",");
            for (ServicesModel.Data servCatData : skillList) {
                for (String s : split) {
                    if (s.equals(String.valueOf(servCatData.id))) {
                        servCatData.isSelected = true;
                    }
                }
            }
            setSkillAdapter();
        }

    }

    private void setSkillAdapter() {
        if (skillList != null && skillList.size() > 0) {
            if (mSkillAdapter == null) {
                mSkillAdapter = new SkillFilterAdapter(activity, item -> {
                });
            }

            mSkillAdapter.doRefresh(skillList);

            if (binding.rvSkills.getAdapter() == null) {
                binding.rvSkills.setAdapter(mSkillAdapter);
            }
        } else {
            if (mSkillAdapter != null) {
                mSkillAdapter.doRefresh(skillList);
            }
        }
    }

    private void getLanguageList() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_LANGUAGE, false, null);
    }

    @SuppressLint("StringFormatInvalid")
    private void showLanguageSelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(activity.getString(R.string.search_for), activity.getString(R.string.language).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        if (languagesArray != null && languagesArray.size() > 0) {
            for (Language.Data data : languagesArray) {
                data.isSelected = data.getServNameByLang(activity.getLanguage()).equalsIgnoreCase(binding.tvLanguage.getText().toString());
            }
            itemAdapter = new SelectItemAdapter(activity, languagesArray, true);
            rvTypes.setAdapter(itemAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (itemAdapter != null && itemAdapter.getSelectedItem() != null) {
                languageId = itemAdapter.getSelectedItem().id;
                binding.tvLanguage.setText(itemAdapter.getSelectedItem().getServNameByLang(activity.getLanguage()));
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_one_item));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemAdapter != null)
                    itemAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(activity, etSearch)));
        etSearch.requestFocus();
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_LANGUAGE)) {
            Language language = Language.getLanguages(responseBody);
            if (language != null && language.data != null) {
                languagesArray = (ArrayList<Language.Data>) language.data;

                if (languageId != -1) {
                    for (Language.Data langData : languagesArray) {
                        if (langData.id == languageId) {
                            binding.tvLanguage.setText(langData.getServNameByLang(activity.getLanguage()));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
