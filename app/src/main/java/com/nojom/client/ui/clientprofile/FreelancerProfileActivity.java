package com.nojom.client.ui.clientprofile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityFreelancerProfileBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.ui.BaseActivity;

public class FreelancerProfileActivity extends BaseActivity {
    private FreelancerProfileActivityVM freelancerProfileActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFreelancerProfileBinding profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_freelancer_profile);
        freelancerProfileActivityVM = new FreelancerProfileActivityVM(Task24Application.getInstance(), profileBinding, this);
    }

    public int agentProfileId() {
        return freelancerProfileActivityVM.getAgentData().id;
    }

    public AgentProfile getAgentData() {
        return freelancerProfileActivityVM.getAgentData();
    }
}
