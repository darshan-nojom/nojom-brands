package com.nojom.client.ui.settings;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.ccp.CCPCountry.getLibraryMasterCountriesEnglish;
import static com.nojom.client.util.Constants.API_ADD_SURVEY;
import static com.nojom.client.util.Constants.API_SERVICE_CATEGORIES_V2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
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
import com.nojom.client.adapter.SelectCountryAdapter;
import com.nojom.client.adapter.SelectYearsAdapter;
import com.nojom.client.adapter.SocialAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.ccp.CCPCountry;
import com.nojom.client.databinding.ActivityAddSurveyBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.YearsModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class AddSurveyActivityVM extends AndroidViewModel implements View.OnClickListener,
        RecyclerviewAdapter.OnViewBindListner, RequestResponseListener {
    private final ActivityAddSurveyBinding binding;
    private final BaseActivity activity;
    private List<ServicesModel.Data> servicesList;
    private RecyclerviewAdapter mAdapter;
    private int selectedService = -1, selectedYear = -1;
    private List<YearsModel> yearList;
    private SocialAdapter socialAdapter;
    private String selectedGender = "Male";
    private SelectCountryAdapter countryAdapter;
    private SelectYearsAdapter selectYearsAdapter;

    AddSurveyActivityVM(Application application, ActivityAddSurveyBinding addSurveyBinding, BaseActivity addSurveyActivity) {
        super(application);
        binding = addSurveyBinding;
        activity = addSurveyActivity;
        initData();
    }

    private void initData() {
        if (activity.getCurrency().equals("SAR")) {
            binding.txtDesc.setText(activity.getString(R.string.we_will_review_your_survey_within_few_hours_and_deposit_5_to_your_balance_today_we_will_also_pick_a_winner_for_100_sar));
        }

        binding.imgBack.setOnClickListener(this);
        binding.btnAddSurvey.setOnClickListener(this);
        binding.txtFemale.setOnClickListener(this);
        binding.txtMale.setOnClickListener(this);
        binding.txtOther.setOnClickListener(this);
        binding.tvCountry.setOnClickListener(this);
        binding.tvOld.setOnClickListener(this);

        List<String> socialList = new ArrayList<>();
        socialList.add(activity.getString(R.string.google_search));
        socialList.add(activity.getString(R.string.facebook));
        socialList.add(activity.getString(R.string.instagram));
        socialList.add(activity.getString(R.string.tiktok));
        socialList.add(activity.getString(R.string.app_store_apple));
        socialList.add(activity.getString(R.string.google_play_android));
        socialList.add(activity.getString(R.string.friend));
        socialList.add(activity.getString(R.string.other));

        socialAdapter = new SocialAdapter(activity, socialList);
        binding.rvSocial.setHasFixedSize(true);
        binding.rvSocial.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvSocial.setAdapter(socialAdapter);

        servicesList = new ArrayList<>();
        servicesList.addAll(Preferences.getCategoryV2(activity));

//        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
//        binding.rvServices.setLayoutManager(manager);
//        binding.rvServices.addItemDecoration(new EqualSpacingItemDecoration(16));

        if (servicesList != null && servicesList.size() > 0) {
            setData();
        } else {
            getTopServiceList();
        }

        yearList = new ArrayList<>();
        for (int i = 10; i < 71; i++) {
            yearList.add(new YearsModel(i, false));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.txt_male:
                onClickMale();
                break;
            case R.id.txt_female:
                onClickFeMale();
                break;
            case R.id.txt_other:
                onClickOther();
                break;
            case R.id.tv_country:
                showCountrySelectDialog();
                break;
            case R.id.tv_old:
                showYearSelectDialog();
                break;
            case R.id.btn_add_survey:
                if (TextUtils.isEmpty(socialAdapter.getSelectedItem())) {
                    activity.toastMessage(activity.getString(R.string.please_select_about_us));
                    return;
                }
//                if (selectedService == -1) {
//                    activity.toastMessage(activity.getString(R.string.please_select_service));
//                    return;
//                }
                if (binding.tvCountry.getText().toString().trim().equalsIgnoreCase(activity.getString(R.string.select_country))) {
                    activity.toastMessage(activity.getString(R.string.please_select_country));
                    return;
                }
                if (binding.tvOld.getText().toString().trim().equalsIgnoreCase(activity.getString(R.string.select_year))) {
                    activity.toastMessage(activity.getString(R.string.please_select_years));
                    return;
                }
                if (TextUtils.isEmpty(binding.etImprove.getText().toString().trim())) {
                    activity.toastMessage(activity.getString(R.string.please_enter_improvements));
                    return;
                }
                addSurvey();
                break;
        }
    }

    private void onClickMale() {
        selectedGender = "Male";
        binding.txtMale.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_button_bg));
        binding.txtMale.setTextColor(Color.WHITE);

        setBackgroundView(binding.txtFemale, binding.txtOther);
    }

    private void onClickFeMale() {
        selectedGender = "Female";
        binding.txtFemale.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_button_bg));
        binding.txtFemale.setTextColor(Color.WHITE);

        setBackgroundView(binding.txtMale, binding.txtOther);
    }

    private void onClickOther() {
        selectedGender = "Other";
        binding.txtOther.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_button_bg));
        binding.txtOther.setTextColor(Color.WHITE);

        setBackgroundView(binding.txtMale, binding.txtFemale);
    }

    private void setBackgroundView(View... views) {
        for (View view : views) {
            view.setBackground(ContextCompat.getDrawable(activity, R.drawable.white_button_bg));
            ((TextView) view).setTextColor(Color.BLACK);
        }
    }

    private void getTopServiceList() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SERVICE_CATEGORIES_V2);
    }

    private void setData() {
//        mAdapter = new RecyclerviewAdapter((ArrayList<?>) servicesList, R.layout.item_skills_edit, this);
//        binding.rvServices.setAdapter(mAdapter);
//        binding.rvServices.setFocusable(false);
    }

    @Override
    public void bindView(View view, final int position) {
        final TextView textView = view.findViewById(R.id.tv_skill);
        textView.setText(servicesList.get(position).getServNameByLang(activity.getLanguage()));

        if (selectedService == position) {
            textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_button_bg));
            textView.setTextColor(Color.WHITE);
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_BOLD);
            textView.setTypeface(tf);
        } else {
            textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.white_button_bg));
            textView.setTextColor(Color.BLACK);
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_REGULAR);
            textView.setTypeface(tf);
        }

        textView.setOnClickListener(view1 -> {
            selectedService = position;
            mAdapter.notifyDataSetChanged();
        });
    }

    @SuppressLint("StringFormatInvalid")
    private void showCountrySelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);
        List<CCPCountry> countryList = getLibraryMasterCountriesEnglish(activity);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(activity.getString(R.string.search_for), activity.getString(R.string.country_1).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        if (countryList.size() > 0) {
            for (CCPCountry country : countryList) {
                country.isSelected = country.getName().equalsIgnoreCase(binding.tvCountry.getText().toString());
            }
            countryAdapter = new SelectCountryAdapter(activity, countryList);
            rvTypes.setAdapter(countryAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (countryAdapter != null && countryAdapter.getSelectedItem() != null) {
                binding.tvCountry.setText(countryAdapter.getSelectedItem().getName());
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_country));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (countryAdapter != null)
                    countryAdapter.getFilter().filter(s.toString());
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

    private void showYearSelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);
        etSearch.setVisibility(View.GONE);

        GridLayoutManager manager = new GridLayoutManager(activity, 5, GridLayoutManager.VERTICAL, false);
        rvTypes.setLayoutManager(manager);
        rvTypes.addItemDecoration(new EqualSpacingItemDecoration(16));
        if (yearList.size() > 0) {
            for (YearsModel model : yearList) {
                model.isSelected = model.year == selectedYear;
            }
            selectYearsAdapter = new SelectYearsAdapter(activity, yearList);
            rvTypes.setAdapter(selectYearsAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectYearsAdapter != null && selectYearsAdapter.getSelectedItem() != null) {
                selectedYear = selectYearsAdapter.getSelectedItem().year;
                binding.tvOld.setText(String.format(Locale.US, "%d " + activity.getString(R.string.years_1), selectYearsAdapter.getSelectedItem().year));
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_years));
            }
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void addSurvey() {
        if (!activity.isNetworkConnected())
            return;

        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject objectQue1 = new JSONObject();
            objectQue1.put("survey_question_id", 1);
            objectQue1.put("survey_text", binding.etImprove.getText().toString());
            jsonArray.put(objectQue1);

            JSONObject objectQue2 = new JSONObject();
            objectQue2.put("survey_question_id", 2);
            objectQue2.put("survey_text", selectedYear + " Years Old");
            jsonArray.put(objectQue2);

            JSONObject objectQue3 = new JSONObject();
            objectQue3.put("survey_question_id", 3);
            objectQue3.put("survey_text", binding.tvCountry.getText().toString());
            jsonArray.put(objectQue3);

            JSONObject objectQue4 = new JSONObject();
            objectQue4.put("survey_question_id", 4);
            objectQue4.put("survey_text", selectedGender);
            jsonArray.put(objectQue4);

            JSONObject objectQue5 = new JSONObject();
            objectQue5.put("survey_question_id", 5);
            objectQue5.put("survey_text", socialAdapter.getSelectedItem());
            jsonArray.put(objectQue5);

//            JSONObject objectQue6 = new JSONObject();
//            objectQue6.put("survey_question_id", 6);
//            objectQue6.put("survey_text", servicesList.get(selectedService).name);
//            jsonArray.put(objectQue6);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("survey_answers", jsonArray.toString());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_ADD_SURVEY, true, map);

    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_ADD_SURVEY)) {
            activity.toastMessage(message);
            Intent i = new Intent();
            activity.setResult(RESULT_OK, i);
            activity.onBackPressed();
        } else if (url.equalsIgnoreCase(API_SERVICE_CATEGORIES_V2)) {
            List<ServicesModel.Data> servicesModel = ServicesModel.getServiceDataCat(responseBody);
            if (servicesModel != null) {
                Preferences.saveCategoryV2(activity, servicesModel);
                servicesList = servicesModel;
                setData();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
