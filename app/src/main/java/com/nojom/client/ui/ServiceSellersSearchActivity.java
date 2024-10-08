package com.nojom.client.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityServiceSellersSearchBinding;


public class ServiceSellersSearchActivity extends BaseActivity {
    ServiceSellersSearchActivityVM serviceSellersSearchActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityServiceSellersSearchBinding serviceSellersSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_sellers_search);
        serviceSellersSearchActivityVM = new ServiceSellersSearchActivityVM(Task24Application.getInstance(), serviceSellersSearchBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceSellersSearchActivityVM.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        serviceSellersSearchActivityVM.onPause();
    }
}
