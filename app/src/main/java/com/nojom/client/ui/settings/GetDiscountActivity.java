package com.nojom.client.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityGetDiscountBinding;
import com.nojom.client.ui.BaseActivity;

public class GetDiscountActivity extends BaseActivity {
    private GetDiscountActivityVM getDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGetDiscountBinding getDiscountBinding = DataBindingUtil.setContentView(this, R.layout.activity_get_discount);
        getDiscount = new GetDiscountActivityVM(Task24Application.getInstance(), getDiscountBinding, this);
    }

    public String getmInvitationUrl() {
        return getDiscount.getmInvitationUrl();
    }
}
