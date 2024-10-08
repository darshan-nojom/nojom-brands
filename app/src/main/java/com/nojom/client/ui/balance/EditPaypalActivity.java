package com.nojom.client.ui.balance;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityEditPaypalBinding;
import com.nojom.client.ui.BaseActivity;

public class EditPaypalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditPaypalBinding paypalBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_paypal);
        new EditPaypalActivityVM(Task24Application.getInstance(), paypalBinding, this);
    }
}
