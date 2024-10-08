package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityWhatWeDoBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import io.intercom.android.sdk.Intercom;

public class WhatWeDoActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityWhatWeDoBinding binding;
    private BaseActivity activity;

    WhatWeDoActivityVM(Application application, ActivityWhatWeDoBinding whatWeDoBinding, BaseActivity whatWeDoActivity) {
        super(application);
        binding = whatWeDoBinding;
        activity = whatWeDoActivity;
        initData();
    }

    private void initData() {
        binding.rlWhatwecan.setOnClickListener(this);
        binding.rlHowitworks.setOnClickListener(this);
        binding.rlWhyus.setOnClickListener(this);
        binding.toolbar.llBack.setOnClickListener(this);
        binding.toolbar.tvRight.setOnClickListener(this);
        binding.cardLocation.setOnClickListener(this);

        binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.what_we_do).toUpperCase());
        binding.toolbar.tvRight.setText(activity.getString(R.string.chat));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_whatwecan:
                Intent i = new Intent(activity, ServicesActivity.class);
                i.putExtra(Constants.SERVICE_TYPE, Constants.HIRE);
                activity.startActivity(i);
                activity.openToLeft();
                break;
            case R.id.rl_whyus:
                i = new Intent(activity, ServicesActivity.class);
                i.putExtra(Constants.SERVICE_TYPE, Constants.WHY_US);
                activity.startActivity(i);
                activity.openToLeft();
                break;
            case R.id.rl_howitworks:
                i = new Intent(activity, ServicesActivity.class);
                i.putExtra(Constants.SERVICE_TYPE, Constants.HOW_IT_WORKS);
                activity.startActivity(i);
                activity.openToLeft();
                break;
            case R.id.ll_back:
                activity.onBackPressed();
                break;
            case R.id.tv_right:
//                activity.openWhatsappChat();
                Intercom.client().displayMessageComposer();
                break;
            case R.id.card_location:
                activity.redirectUsingCustomTab("https://www.google.com/maps/place/24+Task+-+Virtual+Solutions/@14.0993311,122.953149,17z/data=!4m5!3m4!1s0x3398aef000000003:0x8e637de8f2c91be0!8m2!3d14.099578!4d122.955035?hl=en-US");
                break;
        }
    }
}
