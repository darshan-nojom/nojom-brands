package com.nojom.client.ui.addcard;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ContentAddcardBinding;
import com.nojom.client.ui.BaseActivity;

public class AddCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentAddcardBinding addcardBinding = DataBindingUtil.setContentView(this, R.layout.content_addcard);
        new AddCardActivityVM(Task24Application.getInstance(), addcardBinding, this);
    }
}
