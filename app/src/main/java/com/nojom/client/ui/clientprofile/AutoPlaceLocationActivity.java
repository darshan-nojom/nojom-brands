package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityAutoPalceLocationBinding;
import com.nojom.client.ui.BaseActivity;

public class AutoPlaceLocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAutoPalceLocationBinding activityAutoPalceLocationBinding = DataBindingUtil.setContentView(this, R.layout.activity_auto_palce_location);
        new AutoPlaceLocationActivityVM(Task24Application.getInstance(), activityAutoPalceLocationBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
