package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityClientProfileBinding;
import com.nojom.client.ui.BaseActivity;

public class ClientProfileActivity extends BaseActivity {
    private ClientProfileActivityVM clientProfileActivityVM;
    private MyProfileActivityVM myProfileActivityVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClientProfileBinding clientProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_profile);
        clientProfileActivityVM = new ClientProfileActivityVM(Task24Application.getInstance(), clientProfileBinding, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        clientProfileActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishToRight();
    }
}
