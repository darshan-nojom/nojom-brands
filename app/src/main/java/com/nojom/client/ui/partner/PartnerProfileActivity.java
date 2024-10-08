package com.nojom.client.ui.partner;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityPartnerProfileBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.ProfileInfoActivity;
import com.nojom.client.util.Utils;


public class PartnerProfileActivity extends BaseActivity {
    ActivityPartnerProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_partner_profile);

        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
        if (getUserData().percentage != null) {
            if (Math.round(getUserData().percentage.totalPercentage) == 100) {
                binding.relCompleteProfile.setVisibility(View.INVISIBLE);
                binding.txtLaststep.setVisibility(View.INVISIBLE);
            }
            String profilePercentage = Math.round(getUserData().percentage.totalPercentage) + "%";
            binding.tvProfileComplete.setText(Utils.getColorString(PartnerProfileActivity.this,
                    getString(R.string.gig_profile_is, profilePercentage), profilePercentage, R.color.red));
        } else {
            binding.relCompleteProfile.setVisibility(View.INVISIBLE);
            binding.txtLaststep.setVisibility(View.INVISIBLE);
        }

        binding.relCompleteProfile.setOnClickListener(v -> redirectActivity(ProfileInfoActivity.class));
    }

}
