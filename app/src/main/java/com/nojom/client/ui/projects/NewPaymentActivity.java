package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityPaymentNewBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.ui.BaseActivity;

public class NewPaymentActivity extends BaseActivity {

    private AgentProfile agentData;
    ActivityPaymentNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_new);
        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            agentData = (AgentProfile) getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        }

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        if (agentData != null) {

//            binding.toolbarTitle.setText(stringBuilder.toString());

        }
    }
}
