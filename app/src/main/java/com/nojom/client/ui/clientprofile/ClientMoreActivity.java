package com.nojom.client.ui.clientprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityClientMoreBinding;
import com.nojom.client.databinding.ActivityClientSettingsBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.partner.PartnerActivity;

public class ClientMoreActivity extends BaseActivity {

    private ClientMoreActivityVM clientMoreActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClientSettingsBinding clientMoreBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_settings);
        clientMoreActivityVM = new ClientMoreActivityVM(Task24Application.getInstance(), clientMoreBinding, this);

        clientMoreActivityVM.getListMutableLiveData().observe(this, data -> {
            Intent intent = new Intent(ClientMoreActivity.this, PartnerActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });

//        clientMoreActivityVM.getIsShow().observe(this, isShow -> {
//            disableEnableTouch(isShow);
//            if (isShow) {
//                clientMoreBinding.imgPartner.setVisibility(View.INVISIBLE);
//                clientMoreBinding.progressPartner.setVisibility(View.VISIBLE);
//            } else {
//                clientMoreBinding.imgPartner.setVisibility(View.VISIBLE);
//                clientMoreBinding.progressPartner.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        clientMoreActivityVM.onResumeMethod();
    }

    @Override
    public void onBackPressed() {
        onBackPressedEvent();
    }


}
