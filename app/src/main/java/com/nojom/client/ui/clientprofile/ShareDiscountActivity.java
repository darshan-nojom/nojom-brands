package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityShareDiscountBinding;
import com.nojom.client.ui.BaseActivity;

public class ShareDiscountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShareDiscountBinding shareDiscountBinding = DataBindingUtil.setContentView(this, R.layout.activity_share_discount);
        new ShareDiscountActivityVM(Task24Application.getInstance(), shareDiscountBinding, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
