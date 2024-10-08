package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityInflProfileAllBinding;
import com.nojom.client.databinding.ActivityInfluencerProfileBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class InfluencerProfileAllActivity extends BaseActivity {
    private InfluencerProfileAllActivityVM freelancerProfileActivityVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInflProfileAllBinding profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_infl_profile_all);
        freelancerProfileActivityVM = new InfluencerProfileAllActivityVM(Task24Application.getInstance(), profileBinding, this);
    }

    public int agentProfileId() {
        return freelancerProfileActivityVM.getAgentData().id;
    }

    public AgentProfile getAgentData() {
        return freelancerProfileActivityVM.getAgentData();
    }
//    public void setPlatformAdapter(List<SocialPlatformList.Data> socialPlatformList) {
//        freelancerProfileActivityVM.setPlatformAdapter(socialPlatformList);
//    }

    public List<SocialPlatformList.Data> getSocialServiceList() {
        if (freelancerProfileActivityVM.socialPlatformList == null) {
            freelancerProfileActivityVM.socialPlatformList = new ArrayList<>();
        }
        return freelancerProfileActivityVM.socialPlatformList;
    }
}
