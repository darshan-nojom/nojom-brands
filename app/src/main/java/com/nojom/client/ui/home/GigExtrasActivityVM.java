package com.nojom.client.ui.home;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.GigExtrasAdapter;
import com.nojom.client.databinding.ActivityGigExtrasBinding;
import com.nojom.client.ui.BaseActivity;

public class GigExtrasActivityVM extends AndroidViewModel implements View.OnClickListener {
    private final ActivityGigExtrasBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    private GigExtrasAdapter gigExtrasAdapter;

    public GigExtrasActivityVM(@NonNull Application application, ActivityGigExtrasBinding activityGigExtrasBinding, GigExtrasActivity gigExtrasActivity) {
        super(application);
        this.binding = activityGigExtrasBinding;
        this.activity = gigExtrasActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.loutBottom.setOnClickListener(this);
        LinearLayoutManager linearLayoutManagerDesigner = new LinearLayoutManager(activity);
        binding.rvGigExtras.setLayoutManager(linearLayoutManagerDesigner);
        setDesignerAdapter();
    }

    private void setDesignerAdapter() {
        if (gigExtrasAdapter == null) {
            gigExtrasAdapter = new GigExtrasAdapter(activity);
            binding.rvGigExtras.setAdapter(gigExtrasAdapter);
        }

        binding.rvGigExtras.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.lout_bottom:
                break;
        }
    }
}
