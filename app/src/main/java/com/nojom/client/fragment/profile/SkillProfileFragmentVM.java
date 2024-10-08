package com.nojom.client.fragment.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.adapter.ExpertiseAdapter;
import com.nojom.client.adapter.SkillsAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentSkillProfileBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ProfileSkills;
import com.nojom.client.model.Skill;
import com.nojom.client.ui.clientprofile.FreelancerProfileActivity;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.nojom.client.util.Constants.API_GET_AGENT_PROFILE_SKILLS;

class SkillProfileFragmentVM extends AndroidViewModel implements RequestResponseListener {
    private FragmentSkillProfileBinding binding;
    private BaseFragment fragment;
    private ArrayList<Skill> skillList;

    SkillProfileFragmentVM(Application application, FragmentSkillProfileBinding skillProfileBinding, BaseFragment skillProfileFragment) {
        super(application);
        binding = skillProfileBinding;
        fragment = skillProfileFragment;
        initData();
    }

    private void initData() {
        binding.rvExpertise.setLayoutManager(new LinearLayoutManager(fragment.activity));
        binding.rvExpertise.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
        binding.rvExpertise.setNestedScrollingEnabled(false);
        getExpertiseData();
    }


    public void agentSkillAPI() {
        if (skillList != null && skillList.size() > 0) {
            setAdapter();
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("agent_profile_id", ((FreelancerProfileActivity) fragment.activity).agentProfileId() + "");

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, fragment.activity, API_GET_AGENT_PROFILE_SKILLS, true, map);
        }
    }

    private void getExpertiseData() {
        try {
            AgentProfile profileData = ((FreelancerProfileActivity) fragment.activity).getAgentData();
            ArrayList<Skill> expertiseList = new ArrayList<>();
            if (profileData != null && profileData.expertise != null) {
                expertiseList.add(new Skill(profileData.expertise.nameApp,
                        Utils.getExperienceLevel(profileData.expertise.length)));
            }

            if (expertiseList.size() > 0) {
                ExpertiseAdapter mExpertiseAdapter = new ExpertiseAdapter(fragment.activity, expertiseList);
                binding.rvExpertise.setAdapter(mExpertiseAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_GET_AGENT_PROFILE_SKILLS)) {
            try {
                skillList = new ArrayList<>();
                ProfileSkills profileSkills = ProfileSkills.getProfileSkills(responseBody);
                if (profileSkills != null && profileSkills.data != null && profileSkills.data.size() > 0) {
                    for (ProfileSkills.Skill data : profileSkills.data) {
                        skillList.add(new Skill(data.name,
                                Utils.getRatingLevel(data.rating)));
                    }
                }
                setAdapter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setAdapter() {
        if (skillList != null && skillList.size() > 0) {
            binding.rvSkills.setLayoutManager(new LinearLayoutManager(fragment.activity));
            binding.rvSkills.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));
            binding.rvSkills.setNestedScrollingEnabled(false);
            SkillsAdapter mSkillAdapter = new SkillsAdapter(fragment.activity, skillList);
            binding.rvSkills.setAdapter(mSkillAdapter);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }
}
