package com.nojom.client.ui;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.databinding.ActivityExpertGigFilterBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.List;

class ExpertGigFilterActivityVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, View.OnClickListener {
    private ActivityExpertGigFilterBinding binding;
    private BaseActivity activity;
    private List<ServicesModel.Data> servicesList;
    private RecyclerviewAdapter mExpertiseAdapter;
    private int serviceCatId = -1;
    private String serviceCatName = "";

    ExpertGigFilterActivityVM(Application application, ActivityExpertGigFilterBinding expertGigFilterBinding, BaseActivity expertFilterActivity) {
        super(application);
        binding = expertGigFilterBinding;
        activity = expertFilterActivity;
        initData();
    }

    private void initData() {
        binding.tvCancel.setOnClickListener(this);
        binding.tvApply.setOnClickListener(this);
        binding.imgClose.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);

        if (activity.getIntent() != null) {
            serviceCatId = activity.getIntent().getIntExtra(Constants.SERVICE_CATEGORY_ID, -1);
        }

        if (serviceCatId == -1 || serviceCatId == 0) {
            serviceCatId = 10; //Service Category id for "Any Expertise" is 10
        }

        servicesList = Preferences.getCategoryV2(activity);

        GridLayoutManager manager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
        binding.rvExpertise.setLayoutManager(manager);
        binding.rvExpertise.addItemDecoration(new EqualSpacingItemDecoration((int) activity.getResources().getDimension(R.dimen._10sdp)));

        if (servicesList != null && servicesList.size() > 0) {
            ServicesModel.Data otherData = servicesList.get(servicesList.size() - 1);
            servicesList.remove(servicesList.size() - 1);
            servicesList.add(0, otherData);
        }

        binding.rvExpertise.setFocusable(false);

        setData();
    }

    private void setData() {
        mExpertiseAdapter = new RecyclerviewAdapter((ArrayList<?>) servicesList, R.layout.item_skills_edit, this);
        binding.rvExpertise.setAdapter(mExpertiseAdapter);
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
                Intent i = new Intent();
                i.putExtra(Constants.SERVICE_CATEGORY_ID, serviceCatId == 10 ? 0 : serviceCatId);
                i.putExtra(Constants.SERVICE_CATEGORY_NAME, serviceCatName);
                activity.setResult(RESULT_OK, i);
                activity.finish();
                break;
        }
    }

    private void clearFilter() {
        serviceCatId = 10;
        mExpertiseAdapter.notifyDataSetChanged();
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
            serviceCatName = serviceData.name;
            mExpertiseAdapter.notifyDataSetChanged();
        });
    }

}
