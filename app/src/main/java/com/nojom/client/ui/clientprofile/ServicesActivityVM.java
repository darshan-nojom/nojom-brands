package com.nojom.client.ui.clientprofile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.HomeItemsAdapter;
import com.nojom.client.databinding.ActivityServicesBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.home.HomePagerModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;

import io.intercom.android.sdk.Intercom;

class ServicesActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityServicesBinding binding;
    private BaseActivity activity;
    private String type;

    ServicesActivityVM(Application application, ActivityServicesBinding servicesBinding, BaseActivity servicesActivity) {
        super(application);
        binding = servicesBinding;
        activity = servicesActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.llBack.setOnClickListener(this);
        binding.toolbar.tvRight.setOnClickListener(this);
        binding.btnPricing.setOnClickListener(this);
        binding.btnFreeTrial.setOnClickListener(this);

        if (activity.getIntent() != null) {
            type = activity.getIntent().getStringExtra(Constants.SERVICE_TYPE);
        }

        binding.toolbar.tvRight.setText(activity.getString(R.string.chat));

        switch (type) {
            case Constants.HIRE:
                binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.our_service).toUpperCase());
                binding.rvServices.setLayoutManager(new GridLayoutManager(activity, 2));
                binding.rvServices.addItemDecoration(new EqualSpacingItemDecoration(20));
                break;
            case Constants.WHY_US:
                binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.why_us).toUpperCase());
                binding.rvServices.setLayoutManager(new GridLayoutManager(activity, 2));
                binding.rvServices.addItemDecoration(new EqualSpacingItemDecoration(20));
                break;
            case Constants.HOW_IT_WORKS:
                binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.how_it_works_).toUpperCase());
                binding.rvServices.setLayoutManager(new LinearLayoutManager(activity));
                binding.rvServices.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
                break;
        }

        ArrayList<HomePagerModel> arrayList = getList();
        HomeItemsAdapter mAdapter = new HomeItemsAdapter(activity, arrayList, type, model -> {
            switch (type) {
                case Constants.HIRE:
                    Preferences.writeString(activity, Constants.SERVICE_NAME, model.title);
                    activity.gotoMainActivity(Constants.TAB_FREE_TRIAL);
                    break;
                case Constants.WHY_US:
                case Constants.HOW_IT_WORKS:
                    break;
            }
        });
        binding.rvServices.setAdapter(mAdapter);
        binding.rvServices.setNestedScrollingEnabled(false);

        binding.scrollView.postDelayed(() -> binding.scrollView.scrollTo(0, 0), 200);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                activity.onBackPressed();
                break;
            case R.id.tv_right:
//                activity.openWhatsappChat();
                Intercom.client().displayMessageComposer();
                break;
            case R.id.btn_pricing:
                Preferences.writeString(activity, Constants.SERVICE_NAME, "");
                activity.gotoMainActivity(Constants.TAB_CALCULATE);
                break;
            case R.id.btn_free_trial:
                Preferences.writeString(activity, Constants.SERVICE_NAME, "");
                activity.gotoMainActivity(Constants.TAB_POST_JOB);
                break;
        }

    }

    @SuppressLint("Recycle")
    public ArrayList<HomePagerModel> getList() {
        ArrayList<HomePagerModel> arrayList = new ArrayList<>();
        TypedArray imgs = null;
        String[] stringArray = new String[0];
        switch (type) {
            case Constants.HIRE:
                imgs = activity.getResources().obtainTypedArray(R.array.hire_images);
                stringArray = activity.getResources().getStringArray(R.array.hire);
                break;
            case Constants.WHY_US:
                imgs = activity.getResources().obtainTypedArray(R.array.why_us_images);
                stringArray = activity.getResources().getStringArray(R.array.why_us);
                break;
            case Constants.HOW_IT_WORKS:
                imgs = activity.getResources().obtainTypedArray(R.array.how_it_works_images);
                stringArray = activity.getResources().getStringArray(R.array.how_it_works);
                break;
        }

        for (int i = 0; i < stringArray.length; i++) {
            HomePagerModel model = new HomePagerModel();
            model.title = stringArray[i];
            model.icon = imgs.getResourceId(i, -1);
            arrayList.add(model);
        }
        return arrayList;
    }
}
